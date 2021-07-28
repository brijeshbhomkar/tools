package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.DailyRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Log4j2
@Service
public class DownloadService {

    private final RestTemplate restTemplate;

    @Autowired
    private TokenCacheService cacheService;

    @Autowired
    public DownloadService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<DailyRecord> download(final LocalDateTime startDate, final LocalDateTime endDate, String option) throws IOException, ParseException {
        String url = "https://${server-name}/data-api/fetch_records";

        Map<String, String> map = new HashMap<>();
        map.put("start_date", convertDate(startDate));
        map.put("end_date", convertDate(endDate));
        map.put("option", option);
        List<DailyRecord> patientRecords = Collections.emptyList();

        try {
            UncheckedObjectMapper uncheckedObjectMapper = new UncheckedObjectMapper();

            ObjectMapper objectMapper = new ObjectMapper();

            String requestBody = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);

            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + cacheService.get().getToken())
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<List<DailyRecord>> completableFuture = HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(uncheckedObjectMapper::readValue);

            patientRecords = completableFuture.get();

            if (patientRecords != null && !patientRecords.isEmpty()) {
                System.out.println(" patient records " + patientRecords.size());
            } else {
                System.out.println(" No records found !");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return patientRecords;
    }

    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        List<DailyRecord> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }

    }


    private String convertDate(LocalDateTime localDateTime) throws ParseException {

        String date = localDateTime.toString();
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date input = (Date) inputPattern.parse(date);

        SimpleDateFormat outputPattern = new SimpleDateFormat("yyy-MM-dd HH:MM:SS");
        return outputPattern.format(input);
    }

}
