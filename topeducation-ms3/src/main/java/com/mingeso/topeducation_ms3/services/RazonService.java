package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.razon.RazonDTO;
import com.mingeso.topeducation_ms3.dtos.razon.RazonResponse;
import com.mingeso.topeducation_ms3.dtos.razon.RazonesResponse;
import com.mingeso.topeducation_ms3.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class RazonService {
    WebClient razonWebClient;

    public RazonService(WebClient.Builder webClientBuilder){
        this.razonWebClient = webClientBuilder.baseUrl("http://localhost:8082/razones").build();
    }

    public List<RazonDTO> obtenerRazones(){
        RazonesResponse response = this.razonWebClient
                .get()
                .retrieve()
                .bodyToMono(RazonesResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Razones no encontradas.");
                    } else {
                        throw new ApiErrorException("Error al obtener las razones.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener las razones.");
        return response.getRazones();
    }

    public List<RazonDTO> obtenerCuotasPendientesYAtrasadasPorIdsEstudiantes(Integer[] idsEstudiantes){
        RazonesResponse response = this.razonWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("estados", 2, 3)
                        .queryParam("tipos",2)
                        .queryParam("estudiantes", idsEstudiantes)
                        .build())
                .retrieve()
                .bodyToMono(RazonesResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Razones no encontradas.");
                    } else {
                        throw new ApiErrorException("Error al obtener las razones.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener las razones.");
        System.out.println(response.getRazones());
        return response.getRazones();
    }

    public List<RazonDTO> actualizarCuotas(List<RazonDTO> razones){
        RazonesResponse response = this.razonWebClient
                .put()
                .bodyValue(razones)
                .retrieve()
                .bodyToMono(RazonesResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Razones no encontradas.");
                    } else {
                        throw new ApiErrorException("Error al obtener las razones.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener las razones.");
        System.out.println(response.getRazones());
        return response.getRazones();
    }

    public RazonDTO actualizarEstadoRazon(Integer idRazon, Integer idEstadoRazon) {
        RazonResponse response = this.razonWebClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + idRazon + "/estado/" + idEstadoRazon)
                        .build())
                .retrieve()
                .bodyToMono(RazonResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Razón no encontrada.");
                    } else {
                        throw new ApiErrorException("Error al actualizar el estado de la razón.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al actualizar el estado de la razón.");
        return response.getRazon();
    }
}
