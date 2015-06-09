package com.kutsyk.pdf.uploader.service;

import com.microsoft.azure.storage.blob.CloudBlob;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by KutsykV on 06.06.2015.
 */
public interface PdfService {
    void downloadFile(MultipartHttpServletRequest request);
    List<CloudBlob> getAllFiles();

    void setPasswordToPdfFile(String result);
    void loadFileToStorage(String result);
    void changeFileMetaData() throws IOException;
    void sendFileToStorage(String fileName);
}
