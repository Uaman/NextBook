package com.nextbook.services;

import com.microsoft.azure.storage.blob.CloudBlob;
import com.nextbook.domain.enums.Cover;
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
    String uploadBookToStorage(int id);
    void uploadCoversToStorage(int id);
    boolean uploadBookToLocalStorage(int id, MultipartFile file);
    boolean uploadCoversToLocalStorage(int id, MultipartFile file, Cover cover);

    void deleteLocalFolder(int bookId);
    String getUrlForPreviewBook(int bookId);
}
