package com.dhs.dhsuiportal.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileOption {
    private String name;
    private MultipartFile file;
}
