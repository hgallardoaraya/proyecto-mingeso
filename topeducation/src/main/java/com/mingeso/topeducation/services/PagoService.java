package com.mingeso.topeducation.services;

import com.mingeso.topeducation.repositories.PagoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    public PagoService(PagoRepository pagoRepository){
        this.pagoRepository = pagoRepository;
    }
}
