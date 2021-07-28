package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.FilteredRecord;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class ReportProcessService {

    private final Map<String, List<FilteredRecord>> dataMapper = new ConcurrentHashMap<>();

    public Map<String, List<FilteredRecord>> extract(String fileName, Map<String, List<String>> mapper) throws IOException {
        try {
            if (fileName.endsWith(".xlsx")) {
                processXlsx(fileName, mapper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMapper;
    }

    private void processXlsx(String path, Map<String, List<String>> mapper) throws IOException {
        log.info(" Started extracting data from " + path);
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        Stream<Row> stream = StreamSupport.stream(((Iterable<Row>) () -> iterator).spliterator(), false);
        dataMapper.put(file.getName(), process(stream, mapper));
    }

    private List<FilteredRecord> process(Stream<Row> stream, Map<String, List<String>> mapper) throws IOException {
        List<FilteredRecord> records = new ArrayList<>();
        stream.skip(2).forEach(row -> {
            log.info(" Processing row " + row.getRowNum());
            if (row.getRowNum() > 1) {
                FilteredRecord record = new FilteredRecord();
                for (Cell c : row) {
                    XSSFCell cell = (XSSFCell) c;
                    if (cell.getColumnIndex() == 0) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            record.setIcmrId(NumberToTextConverter.toText(cell.getNumericCellValue()));
                        }
                    }

                    if (cell.getColumnIndex() == 1) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setLabName(cell.getStringCellValue());
                        }
                    }

//                    if (cell.getColumnIndex() == 2) {
//                        if (!isCellEmpty(cell)) {
//                            cell.setCellType(Cell.CELL_TYPE_STRING);
//                            record.setLabId(cell.getStringCellValue());
//                        }
//                    }

                    if (cell.getColumnIndex() == 2) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setPatientId(cell.getStringCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 3) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setPatientName(cell.getStringCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 4) {
                        if (!isCellEmpty(cell)) {
                            record.setAge((int) cell.getNumericCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 5) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setGender(cell.getStringCellValue());
                        }
                    }


                    if (cell.getColumnIndex() == 6) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setDistrict(cell.getStringCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 7) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setStateOfResidence(cell.getStringCellValue());
                        }
                    }


                    String searchKey = cell.getColumnIndex() == 8 ? cell.getStringCellValue() : null;
                    if (searchKey != null) {
                        mapper.entrySet().stream().forEach(k -> {
                            List<String> addresses = k.getValue();
                            String[] keys = searchKey.split(" ");
                            List<String> matchers = Arrays.stream(keys).filter(item -> !item.isEmpty()).collect(Collectors.toList());
                            AtomicBoolean found = new AtomicBoolean(false);
                            if (matchers.size() == 1) {
                                for (int j = 0; j < addresses.size(); j++) {
                                    if (matchers.get(0).trim().equalsIgnoreCase(addresses.get(j).trim())) {
                                        found.set(true);
                                        break;
                                    }
                                }
                                if (found.get()) {
                                    record.setPhc(k.getKey());
                                }
                            } else {
                                for (int i = matchers.size() - 1; i >= 0; --i) {
                                    for (int j = 0; j < addresses.size(); j++) {
                                        if (matchers.get(i).trim().equalsIgnoreCase(addresses.get(j).trim())) {
                                            found.set(true);
                                            break;
                                        }
                                    }
                                    if (found.get()) {
                                        record.setPhc(k.getKey());
                                        break;
                                    }
                                }
                            }
                        });
                    }

                    if (cell.getColumnIndex() == 8) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setAddress(cell.getStringCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 9) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setVillage(cell.getStringCellValue());

                        }
                    }

                    if (cell.getColumnIndex() == 10) {
                        if (!isCellEmpty(cell)) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            record.setContactNo(cell.getStringCellValue());
                        }
                    }

                    if (cell.getColumnIndex() == 11) {
                        if (!isCellEmpty(cell)) {
                            //if (DateUtil.isCellDateFormatted(cell)) {
                                //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                //Date date = new Date(dateFormat.format(cell.getDateCellValue()));
                                record.setConfirmationDate(cell.getStringCellValue());
                            //}
                        }
                    }

                }

                //set the record
                log.info(" Completed Icmr id " + record.getIcmrId());
                records.add(record);
            }
            ;
        });

        log.info(" Total Processed " + records.size());
        return records;
    }

    public static boolean isCellEmpty(final XSSFCell cell) {
        if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty()) {
            return true;
        }

        return false;
    }
}
