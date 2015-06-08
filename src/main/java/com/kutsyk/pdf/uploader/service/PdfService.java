package com.kutsyk.pdf.uploader.service;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by KutsykV on 06.06.2015.
 */
public interface PdfService {
    String downloadPdf(MultipartFile file);
    List<String> getAllFiles();

    void setPasswordToPdfFile(String result);
    void loadFileToStorage(String result);
    void changeFileMetaData() throws IOException;
    void sendFileToStorage(String fileName);
}
