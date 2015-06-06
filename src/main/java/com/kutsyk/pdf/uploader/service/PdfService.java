package com.kutsyk.pdf.uploader.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by KutsykV on 06.06.2015.
 */
public interface PdfService {
    String downloadPdf(MultipartFile file);
    void setPasswordToPdfFile(String fileName);
    void sendFileToStorage(String fileName);
}
