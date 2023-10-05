package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.DescuentoPuntajePrueba;
import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Examen;
import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.repositories.DescuentoPuntajePruebaRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.ExamenRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
                         DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
                         RazonRepository razonRepository){
        this.estudianteRepository = estudianteRepository;
        this.examenRepository = examenRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.razonRepository = razonRepository;
    }

    @Transactional
    public void importarExamen(MultipartFile archivo) {
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(archivo.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                XSSFRow row = worksheet.getRow(i);
                String rut = row.getCell(0).getStringCellValue();
                Estudiante estudiante = estudianteRepository.findByRut(rut);
                Date fecha = row.getCell(1).getDateCellValue();
                Double puntaje = row.getCell(2).getNumericCellValue();
                examenRepository.save(new Examen(fecha, puntaje, estudiante));
            }
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public void aplicarDescuento(String rut, Date fech){
        Estudiante estudiante = estudianteRepository.findByRut(rut);
        System.out.println(estudiante.getNombre1());
        for(Examen examen : estudiante.getExamenes()){
            System.out.println(examen.getId());
        }

        for(Razon razon : estudiante.getRazones()){
            System.out.println(razon.getId());
        }
    }
}
