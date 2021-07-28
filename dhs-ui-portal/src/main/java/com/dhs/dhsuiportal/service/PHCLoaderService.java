package com.dhs.dhsuiportal.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PHCLoaderService {

    private Map<String, List<String>> map = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext ctx;

//    @Value("/resources/phc/*.csv")
//    private Resource[] inputResources;

    public Map<String, List<String>> loadFiles() throws IOException {
        if (map.isEmpty()) {
            Resource[] inputResources = ctx.getResources("classpath:phc/*.csv");
            Arrays.stream(inputResources).forEach(s -> {
                try {
                    csvReader(s.getURI().getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return map;
    }

    private List<String> csvReader(final String name) {
        File file = new File(name);
        String phcName = file.getName().replace(".csv", "");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String[] phc = phcName.split("-");
                map.put(phc[0].toUpperCase() + " " + phc[1],
                        Arrays.stream(values).map(String::trim).collect(Collectors.toList()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }
}
