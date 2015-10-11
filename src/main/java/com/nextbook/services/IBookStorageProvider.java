package com.nextbook.services;

import com.nextbook.domain.enums.Cover;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * Created by KutsykV on 06.06.2015.
 */
public interface IBookStorageProvider {

    /**
     *
     * @param id - if of book that should be uploaded
     * @return url to file on storage or null if was error while uploading
     */
    String uploadBookToStorage(int id);

    /**
     *
     * @param id - id of book
     * @param file - file that must be saved
     * @return success
     */
    boolean uploadBookToLocalStorage(int id, MultipartFile file);

    /**
     *
     * @param id - id of book
     * @param file - file that must be saved
     * @param cover - first or last page(enum)
     * @return success
     */
    boolean uploadCoversToLocalStorage(int id, MultipartFile file, Cover cover);

    /**
     *
     * @param bookId - id of book
     * @param file - file that must be saved
     * @return success
     */
    boolean uploadGalleryPhoto(int bookId, MultipartFile file);

    /**
     *
     * @param bookId - id of book
     * @param photoNumber - number of photo on storage to delete
     * @return success
     */
    boolean deleteGalleryPhoto(int bookId, int photoNumber);

    /**
     *
     * @param bookId - id of book
     * @return url to preview copy of book
     */
    String getUrlForPreviewBook(int bookId);

    /**
     *
     * @param outputStream - stream where file will be written
     * @param bookId - id of book
     * @param cover - first or last page(enum)
     */
    void getCover(OutputStream outputStream, int bookId, Cover cover);

    void getGalleryPhoto(OutputStream outputStream, int bookId, int photoNumber);

    /**
     *
     * @param bookId - id of book
     * @return number of photos local + storage
     */
    int getNumberOfPhotosInGallery(int bookId);
}
