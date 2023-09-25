package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Examen;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.ExamenRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ExamenService {
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    ExamenRepository examenRepository;
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
}
