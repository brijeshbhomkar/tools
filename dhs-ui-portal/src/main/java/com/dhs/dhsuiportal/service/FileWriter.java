package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.DailyRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileWriter {

    public void writer(List<DailyRecord> records, HttpServletResponse response) throws Exception;
}
