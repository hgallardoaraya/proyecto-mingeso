package com.mingeso.topeducation_ms2.services;

import com.mingeso.topeducation_ms2.dtos.estudiantes.EstudianteDTO;
import com.mingeso.topeducation_ms2.dtos.estudiantes.EstudianteResponse;
import com.mingeso.topeducation_ms2.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms2.exceptions.RegistroNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class EstudianteService {
    WebClient webClient;

    public EstudianteService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/estudiantes").build();
    }

    public EstudianteDTO obtenerEstudiantePorRut(String rut){
        EstudianteResponse response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("rut", rut)// Ruta adicional
                        .build())
                .retrieve()
                .bodyToMono(EstudianteResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Estudiante no encontrado");
                    } else {
                        throw new ApiErrorException("Error al obtener el estudiante.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener el estudiante.");
        return response.getEstudiante();
    }

    public EstudianteDTO actualizarCuotasPactadas(Integer idEstudiante, Integer cuotasPactadas) {
        EstudianteResponse response = webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/cuotasPactadas")
                        .queryParam("idEstudiante", idEstudiante)// Ruta adicional
                        .queryParam("cuotasPactadas", cuotasPactadas)// Ruta adicional
                        .build())
                .retrieve()
                .bodyToMono(EstudianteResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Estudiante no encontrado");
                    } else {
                        throw new ApiErrorException("Error al obtener el estudiante.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener el estudiante.");
        return response.getEstudiante();
    }
}
