package com.nextbook.services;

import com.microsoft.azure.storage.blob.CloudBlob;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by KutsykV on 06.06.2015.
 */
public interface PdfService {
    void uploadFile(MultipartHttpServletRequest request);
    List<CloudBlob> getAllFiles();

    void loadFileToStorage(File result);
    void changeFileMetaData(File source, File result) throws IOException;
    void setPasswordToPdfFile(File source, File result);
}
