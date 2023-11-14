package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.estudiante.EstudianteDTO;
import com.mingeso.topeducation_ms3.dtos.estudiante.EstudiantesResponse;
import com.mingeso.topeducation_ms3.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class EstudianteService {
    WebClient webClient;

    public EstudianteService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://gateway-server-service:8080").build();
    }

    public List<EstudianteDTO> obtenerEstudiantes(){
        EstudiantesResponse response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/estudiantes")
                        .build())
                .retrieve()
                .bodyToMono(EstudiantesResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Estudiantes no encontrados");
                    } else {
                        throw new ApiErrorException("Error al obtener a los estudiantes.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener a los estudiantes.");
        System.out.println("Respuesta ms1 " + response.getEstudiantes());
        return response.getEstudiantes();
    }
}
