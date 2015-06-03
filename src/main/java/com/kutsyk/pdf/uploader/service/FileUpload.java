package com.kutsyk.pdf.uploader.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Kutsyk on 03.06.2015.
 */
public class FileUpload{

    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}