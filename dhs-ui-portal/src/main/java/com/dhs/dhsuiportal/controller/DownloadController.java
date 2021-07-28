package com.dhs.dhsuiportal.controller;

import com.dhs.dhsuiportal.model.FilterOption;
import com.dhs.dhsuiportal.model.DailyRecord;
import com.dhs.dhsuiportal.service.DownloadService;
import com.dhs.dhsuiportal.service.XlsFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private XlsFileWriter xlsFileWriter;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView showDownload() {
        return new ModelAndView("download", "filteroption", new FilterOption());
    }


    @RequestMapping(value = "/download/filter", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("filteroption") FilterOption filteroption, BindingResult result, ModelMap model, HttpServletResponse response) throws Exception {
        if (result.hasErrors()) {
            return "error";
        }
        List<DailyRecord> records =
                downloadService.download(filteroption.getStartDate(), filteroption.getEndDate(), filteroption.getSelectedOption());
        model.addAttribute("records", records);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=covid-data.xlsx");

        xlsFileWriter.writer(records, response);
        return "redirect:/export";
    }

    @ModelAttribute("optionList")
    public List<String> optionList() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Positive");
        optionList.add("All Entries");
        optionList.add("All Updated");
        return optionList;
    }

    public List<DailyRecord> dummyRecords() {
        List<DailyRecord> records = new ArrayList<>();
        DailyRecord record = new DailyRecord();
        record.setId(1);
        record.setPatientId("1");
        record.setPatientName("Dummy");
        records.add(record);
        return records;
    }
}
