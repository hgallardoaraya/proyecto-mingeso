package com.mingeso.topeducationms1.utils;

import com.mingeso.topeducationms1.dtos.EstudianteDTO;
import com.mingeso.topeducationms1.dtos.IngresarEstudianteDTO;
import com.mingeso.topeducationms1.entities.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<EstudianteDTO> estudiantesToEstudiantesDTOS(List<Estudiante> estudiantes){
        List<EstudianteDTO> estudiantesDTOS = new ArrayList<>();
        for(Estudiante estudiante : estudiantes){
            estudiantesDTOS.add(estudianteToEstudianteDTO(estudiante));
        }
        return estudiantesDTOS;
    }

    public static Estudiante ingresarEstudianteDTOToEstudianteEntity(IngresarEstudianteDTO ingresarEstudianteDTO) {
        Estudiante estudiante = new Estudiante();
        estudiante.setRut(ingresarEstudianteDTO.getRut());
        estudiante.setNombre1(ingresarEstudianteDTO.getNombre1());
        estudiante.setNombre2(ingresarEstudianteDTO.getNombre2());
        estudiante.setApellido1(ingresarEstudianteDTO.getApellido1());
        estudiante.setApellido2(ingresarEstudianteDTO.getApellido2());
        estudiante.setFechaNacimiento(ingresarEstudianteDTO.getFechaNacimiento());
        estudiante.setAnioEgreso(ingresarEstudianteDTO.getAnioEgreso());
        estudiante.setNombreColegio(ingresarEstudianteDTO.getNombreColegio());
        return estudiante;
    }

    public static EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante) {
        return EstudianteDTO.builder()
                .id(estudiante.getId())
                .rut(estudiante.getRut())
                .nombre1(estudiante.getNombre1())
                .nombre2(estudiante.getNombre2())
                .apellido1(estudiante.getApellido1())
                .apellido2(estudiante.getApellido2())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .anioEgreso(estudiante.getAnioEgreso())
                .nombreColegio(estudiante.getNombreColegio())
                .cuotasPactadas(estudiante.getCuotasPactadas())
                .tipoColegio(estudiante.getTipoColegio())
                .tipoPagoArancel(estudiante.getTipoPagoArancel())
                .build();
    }
}
