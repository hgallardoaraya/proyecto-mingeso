package com.mingeso.topeducation_ms3.services;


import com.mingeso.topeducation_ms3.dtos.estudiantes.EstudianteDTO;
import com.mingeso.topeducation_ms3.entities.Examen;
import com.mingeso.topeducation_ms3.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms3.exceptions.RegistrosFaltantesException;
import com.mingeso.topeducation_ms3.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation_ms3.repositories.ExamenRepository;
import com.mingeso.topeducation_ms3.utils.Util;
import jakarta.transaction.Transactional;
import lombok.Generated;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ExamenService {
    private final EstudianteService estudianteService;
    private final ExamenRepository examenRepository;

    @Autowired
    public ExamenService(EstudianteService estudianteService,
                         ExamenRepository examenRepository){
        this.estudianteService = estudianteService;
        this.examenRepository = examenRepository;
    }

    @Generated
    @Transactional
    public void importarExamen(MultipartFile archivo, LocalDate fechaActual) {
        LocalDate fechaInicioProceso = Util.obtenerFechaInicioProceso(fechaActual);
        LocalDate fechaExamenesProceso = Util.obtenerFechaExamenesProceso(fechaInicioProceso);

//        if(fechaActual.isBefore(fechaExamenesProceso) || fechaActual.isAfter(fechaInicioProceso)){
//            throw new FechaNoPermitidaException("La importación de exámenes del proceso correspondiente debe ser realizada" +
//                    " entre el último viernes del més " + fechaExamenesProceso + " y el inicio de pago de arancel del proceso "
//                    + fechaInicioProceso + ".");
//        }

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(archivo.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir el archivo '.xlsx'.");
        }
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numeroFilasDocumento =  worksheet.getPhysicalNumberOfRows();
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantes();

        if(estudiantes.size() > 0 && estudiantes.size() != (numeroFilasDocumento - 1)) {
            throw new RegistrosFaltantesException("Faltan o sobran estudiantes en el archivo excel.");
        }
        Map<String, Boolean> estudiantesImportados = new HashMap<>();

        List<Examen> examenes = new ArrayList<>();
        for(int i = 1; i < numeroFilasDocumento; i++) {
            XSSFRow row = worksheet.getRow(i);

            String rut = row.getCell(0).getStringCellValue();
            if(estudiantesImportados.containsKey(rut)){
                throw new ValorFueraDeRangoException("El estudiante con rut " + rut + " está repetido en el archivo excel.");
            }
            Optional<Integer> idEstudiante = estudiantes.stream()
                    .filter(estudiante -> estudiante.getRut().equals(rut))
                    .map(EstudianteDTO::getId)
                    .findFirst();

            if(idEstudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante con rut " + rut + " no existe.");

            Date fecha = row.getCell(1).getDateCellValue();
            LocalDate fechaLocalDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int puntaje = (int) row.getCell(2).getNumericCellValue();

            if(puntaje < 0 || puntaje > 1000) throw new ValorFueraDeRangoException("El puntaje " + puntaje + " del " +
                    "estudiante con rut " + rut + " está fuera del rango permitido (0-1000).");
            estudiantesImportados.put(rut, true);

            examenes.add(new Examen(fechaLocalDate, puntaje, idEstudiante.get(), 0));
        }
        examenRepository.saveAll(examenes);
    }

}
