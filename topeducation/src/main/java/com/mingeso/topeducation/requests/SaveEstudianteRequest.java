package com.mingeso.topeducation.requests;

import com.mingeso.topeducation.entities.Estudiante;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveEstudianteRequest {
    Estudiante estudiante;
    int idTipoColegio;
    int idTipoPagoArancel;
    int idInteresMesesAtraso;
}
