package com.dhs.dhsuiportal.controller;

import com.dhs.dhsuiportal.model.FilteredRecord;
import com.dhs.dhsuiportal.service.PHCLoaderService;
import com.dhs.dhsuiportal.service.ReportGeneratorService;
import com.dhs.dhsuiportal.service.ReportProcessService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
public class ReportController {

    private String fileLocation;

    @Autowired
    private PHCLoaderService phcLoaderService;

    @Autowired
    private ReportProcessService reportProcessService;

    @Autowired
    private ReportGeneratorService reportGeneratorService;

    @RequestMapping(value = "/daily-report", method = RequestMethod.GET)
    public String showDownload() {
        return "daily-report";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) throws IOException {

        try {
            if ( file != null && file.getResource().getFilename().isEmpty())
                throw new RuntimeException(" File is empty !");

            InputStream in = file.getInputStream();
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
            FileOutputStream f = new FileOutputStream(fileLocation);
            int ch = 0;
            while ((ch = in.read()) != -1) {
                f.write(ch);
            }
            f.flush();
            f.close();

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("message", "File: " + file.getOriginalFilename()
                + " has been uploaded successfully!");

        model.addAttribute("process", "true");

        return "daily-report";
    }

    @RequestMapping(value = "/readFile", method = RequestMethod.POST)
    public String readFile(Model model, HttpServletResponse response) throws IOException {
        if (fileLocation != null) {
            if (fileLocation.endsWith(".xlsx") || fileLocation.endsWith(".xls")) {
                //load phcs
                Map<String, List<String>> mapper = phcLoaderService.loadFiles();

                Map<String, List<FilteredRecord>> filteredMapRecords = reportProcessService.extract(fileLocation, mapper);

                //save and download
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=covid-positive-data.xlsx");

                reportGeneratorService.generateReport(filteredMapRecords, response);

            } else {
                model.addAttribute("message", "Not a valid excel file!");
            }
        } else {
            model.addAttribute("message", "File missing! Please upload an excel file.");
        }
        return "daily-report";
    }
}
