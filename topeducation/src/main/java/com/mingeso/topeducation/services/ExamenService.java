package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.DescuentoPuntajePrueba;
import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Examen;
import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation.repositories.DescuentoPuntajePruebaRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.ExamenRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamenService {
    EstudianteRepository estudianteRepository;
    ExamenRepository examenRepository;
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    RazonRepository razonRepository;

    @Autowired
    public ExamenService(EstudianteRepository estudianteRepository,
                         ExamenRepository examenRepository,
                         DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository){
        this.estudianteRepository = estudianteRepository;
        this.examenRepository = examenRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
    }

    @Transactional
    public void importarExamen(MultipartFile archivo) {
        int diaActual = LocalDate.now().getDayOfMonth();

//        if(diaActual >= 5 && diaActual <= 10){
//            throw new FechaNoPermitidaException("No se pueden importar exámenes entre el 5 y el 10 ya que es el proceso " +
//                    "de pago de arancel.");
//        }

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(archivo.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir el archivo '.xlsx'.");
        }
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);

            String rut = row.getCell(0).getStringCellValue();
            Optional<Estudiante> estudiante = estudianteRepository.findByRut(rut);
            if(estudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante con rut " + rut + " no existe.");

            Date fecha = row.getCell(1).getDateCellValue();

            Integer puntaje = (int) row.getCell(2).getNumericCellValue();

            if(puntaje < 0 || puntaje > 1000) throw new ValorFueraDeRangoException("El puntaje " + puntaje + " del " +
                    "estudiante con rut " + rut + " está fuera del rango permitido (0-1000).");

            examenRepository.save(new Examen(fecha, puntaje, estudiante.get(), false));
        }
    }
}
