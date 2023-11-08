package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.razones.RazonDTO;
import com.mingeso.topeducation_ms3.dtos.razones.RazonesResponse;
import com.mingeso.topeducation_ms3.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class RazonService {
    WebClient webClient;

    public RazonService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
    }

    public List<RazonDTO> obtenerCuotasPendientesYAtrasadasPorIdsEstudiantes(Integer[] idsEstudiantes){
        RazonesResponse response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/razones")
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
        RazonesResponse response = webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/razones")
                        .build())
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
}
