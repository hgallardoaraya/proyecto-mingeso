package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.pago.PagoDTO;
import com.mingeso.topeducation_ms3.dtos.pago.PagosResponse;
import com.mingeso.topeducation_ms3.dtos.razon.RazonDTO;
import com.mingeso.topeducation_ms3.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class PagoService {
    WebClient pagoWebClient;

    public PagoService(WebClient.Builder webClientBuilder){
        this.pagoWebClient = webClientBuilder.baseUrl("http://localhost:8082/pagos").build();
    }

    public List<PagoDTO> obtenerPagos(){
        PagosResponse response = this.pagoWebClient
                .get()
                .retrieve()
                .bodyToMono(PagosResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Pagos no encontrados.");
                    } else {
                        throw new ApiErrorException("Error al obtener los pagos.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener los pagos.");
        return response.getPagos();
    }

}
