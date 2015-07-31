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
public interface IBookUploadingProvider {
    String uploadBookToStorage(String bookDirName);
    void uploadFileToLocalStorage(String prefix, MultipartFile file);

    List<CloudBlob> getAllFiles(String containerName);
    void uploadFileToStorage(String prefix, File result);
}
