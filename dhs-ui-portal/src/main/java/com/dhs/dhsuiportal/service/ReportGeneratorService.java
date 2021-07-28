package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.FilteredRecord;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ReportGeneratorService {

    public void generateReport(Map<String, List<FilteredRecord>> map, HttpServletResponse response) {
        map.entrySet().stream().forEach(s -> {
            try {
                List<FilteredRecord> data = s.getValue();
                if (data != null && !data.isEmpty()) {
                    Workbook workbook = new XSSFWorkbook();

                    Sheet sheet = workbook.createSheet("daily-cases");

                    createHeader(sheet, workbook);

                    writeRow(data, workbook, sheet);

                    try (OutputStream outputStream = response.getOutputStream()) {
                        workbook.write(outputStream);
                    }
                }
            } catch (Exception e) {
                log.error(" Failed to generate report " + e.getStackTrace());
            }
        });
    }

    private void writeRow(List<FilteredRecord> records, Workbook workbook, Sheet sheet) {

        CellStyle style = workbook.createCellStyle();
        int rowCount = 1;
        for (int i = 0; i < records.size(); i++) {
            FilteredRecord record = records.get(i);
            Row row = sheet.createRow(rowCount);

            Cell cell = row.createCell(0);
            cell.setCellValue(record.getIcmrId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(record.getLabName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(record.getLabId());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(record.getPatientId());
            cell.setCellStyle(style);


            cell = row.createCell(4);
            cell.setCellValue(record.getPatientName());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(record.getAge());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(record.getGender());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(record.getDistrict());
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(record.getPhc());
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue(record.getAddress());
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue(record.getVillage());
            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue(record.getContactNo());
            cell.setCellStyle(style);

            cell = row.createCell(12);
            cell.setCellValue(record.getConfirmationDate());
            cell.setCellStyle(style);

            rowCount++;
        }

        log.info(" ======== Finished writing data ================== ");
    }

    private void createHeader(Sheet sheet, Workbook workbook) {
        log.info(" ====== Writing header information ==============");
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Icmr ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Laboratory Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Laboratory Code");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Patient ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Patient Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Gender");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("District of Residence");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("PHC");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Address");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Village/Town");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(11);
        headerCell.setCellValue("Contact Number");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(12);
        headerCell.setCellValue("Confirmation Date");
        headerCell.setCellStyle(headerStyle);

        log.info(" =========== header information completed =============== ");
    }
}
