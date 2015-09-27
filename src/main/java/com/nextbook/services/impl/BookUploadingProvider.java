package com.nextbook.services.impl;

import com.itextpdf.text.pdf.*;
import com.microsoft.azure.storage.*;
import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.upload.Constants;
import com.microsoft.azure.storage.blob.*;
import com.nextbook.services.IBookUploadingProvider;
import com.nextbook.utils.FilesUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;


/**
 * Created by KutsykV on 06.06.2015.
 */
public class BookUploadingProvider implements IBookUploadingProvider {

    /*
    private List<CloudBlob> getAllFiles(String containerName) {
        List<CloudBlob> result = new LinkedList<CloudBlob>();
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(STORAGE_CONNECTING_STRING);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference(containerName);
            for (ListBlobItem blobItem : container.listBlobs()) {
                if (blobItem instanceof CloudBlockBlob) {
                    CloudBlob retrievedBlob = (CloudBlob) blobItem;
                    result.add(retrievedBlob);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    */

    @Override
    public boolean uploadBookToLocalStorage(int id, MultipartFile file) {
        if(!isPdf(file.getOriginalFilename()))
            return false;
        String path = BOOK_FOLDER + id;
        File bookDir = new File(rootDir + File.separator + path);
        if (!bookDir.exists())
            bookDir.mkdirs();
        boolean saved = saveBookWithPath(bookDir, file);
        if(saved)
            saveBookForPreview(bookDir);
        return saved;
    }

    private boolean saveBookWithPath(File bookDir, MultipartFile file){
        try {
            File originalFolder = new File(bookDir + File.separator + ORIGINAL_BOOK_FOLDER);
            if(!originalFolder.exists())
                originalFolder.mkdir();
            File fileWithoutPass = new File(originalFolder + File.separator + ORIGINAL + PDF_EXTENSION);
            if(!fileWithoutPass.exists())
                fileWithoutPass.createNewFile();
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(fileWithoutPass));
            File fileWithPass = new File(bookDir + File.separator + BOOK + PDF_EXTENSION);
            setPasswordToPdfFile(fileWithoutPass, fileWithPass);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveBookForPreview(File bookDir){
        try {
            String pathToOriginalBook = bookDir + File.separator + ORIGINAL_BOOK_FOLDER + File.separator + ORIGINAL + PDF_EXTENSION;
            PdfReader reader = new PdfReader(pathToOriginalBook);
            int number = reader.getNumberOfPages();
            int endRange = number/10;
            if(endRange < 10)
                endRange = 10;
            reader.selectPages("1-"+endRange);
            File previewFile = new File(bookDir + File.separator + PREVIEW_FOLDER);
            if(!previewFile.exists())
                previewFile.mkdir();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(previewFile + File.separator + PREVIEW + PDF_EXTENSION));
            stamper.close();
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean uploadCoversToLocalStorage(int id, MultipartFile file, Cover cover) {
        if(!isImage(file.getOriginalFilename()))
            return false;
        String path = BOOK_FOLDER + id + File.separator + COVERS_FOLDER;
        File coverDir = new File(rootDir + File.separator + path);
        if (!coverDir.exists())
            coverDir.mkdirs();
        try {
            for(File oldCoverFile : coverDir.listFiles()){
                if(oldCoverFile.isFile() && oldCoverFile.getName().startsWith(cover.toString())){
                    deleteFile(oldCoverFile);
                    break;
                }
            }
            String fileName = cover.toString() + '.' + FilesUtils.getFIleExtensions(file.getOriginalFilename());
            File resultFile = new File(coverDir + File.separator + fileName);
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(resultFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteLocalFolder(int bookId) {
        File folder = new File(rootDir + File.separator + BOOK_FOLDER + bookId);
        deleteFolder(folder);
    }

    @Override
    public String getUrlForPreviewBook(int bookId) {
        char sep = '/';
        return STORAGE_URL + sep + BOOK_FOLDER + bookId + sep + PREVIEW_FOLDER + sep + PREVIEW + PDF_EXTENSION;
    }

    @Override
    public void getCover(OutputStream outputStream, int bookId, Cover cover) {
        try{
            CloudBlobContainer container = initContainer(BOOK_FOLDER + bookId);
            CloudBlobDirectory covers = container.getDirectoryReference(COVERS_FOLDER);

            for (ListBlobItem blobItem : covers.listBlobs()) {
                if (blobItem instanceof CloudBlob) {
                    CloudBlob blob = (CloudBlob) blobItem;
                    if(blob.getName().contains(cover.toString())) {
                        blob.download(outputStream);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadBookToStorage(int id) {
        File bookDir = new File(rootDir + File.separator + BOOK_FOLDER + id);
        if (!bookDir.exists()) {
            return null;
        }
        String uri = null;
        for (File file : bookDir.listFiles()) {
            if (!file.isFile())
                continue;
            uri = uploadBookToStorage(BOOK_FOLDER + id + "/", file);
            uploadPreviewBookToStorage(BOOK_FOLDER + id + "/", id);
            break;
        }
        return uri;
    }

    private String uploadBookToStorage(String containerName, File file){
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(file.getName());
            if (blob.exists())
                blob.delete();
            blob.upload(new FileInputStream(file), file.length());
            return blob.getUri().toURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void uploadPreviewBookToStorage(String containerName, int bookId){
        String pathToPreviewFile = BOOK_FOLDER + bookId + File.separator + PREVIEW_FOLDER + File.separator + PREVIEW + PDF_EXTENSION;
        File previewFile = new File(rootDir + File.separator + pathToPreviewFile);
        if(!previewFile.exists())
            return;
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(PREVIEW_FOLDER + File.separator + PREVIEW + PDF_EXTENSION);
            if (!blob.exists()) {
                blob.upload(new FileInputStream(previewFile), previewFile.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadCoversToStorage(int id) {
        File bookDir = new File(rootDir + File.separator + BOOK_FOLDER + id + File.separator + COVERS_FOLDER);
        if (!bookDir.exists()) {
            return;
        }
        for (File file : bookDir.listFiles()) {
            uploadCoverToStorage(BOOK_FOLDER + id + "/", file);
        }
    }

    private void deleteFile(File f) {
        try {
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadCoverToStorage(String containerName, File file){
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(COVERS_FOLDER + File.separator + file.getName());
            if(blob.exists())
                blob.delete();
            blob.upload(new FileInputStream(file), file.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setPasswordToPdfFile(File source, File result) {
        try {
            PdfReader reader = new PdfReader(source.getAbsolutePath());
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(result));
            stamper.setEncryption(Constants.USER_PASSWORD.getBytes(), Constants.OWNER_PASSWORD.getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CloudBlobContainer initContainer(String containerName) throws URISyntaxException, InvalidKeyException, StorageException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(STORAGE_CONNECTING_STRING);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference(containerName);
        container.createIfNotExists();
        BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
        containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
        container.uploadPermissions(containerPermissions);
        return container;
    }

    private String changeBookName(String oldName){
        return oldName;
    }

    private boolean isPdf(String fileName){
        return fileName.endsWith(PDF_EXTENSION);
    }

    private boolean isImage(String fileName){
        return FilesUtils.getFIleExtensions(fileName).matches(ACCEPTED_IMAGE_EXTENSIONS);
    }

    private void deleteFolder(File folder){
        for (File file : folder.listFiles()) {
            if(file.isDirectory())    deleteFolder(file);
            else                        deleteFile(file);
        }
        folder.delete();
    }

    private void changeFileMetaData(File source, File result) throws IOException {
        FileInputStream input = new FileInputStream(source);
        byte[] array = IOUtils.toByteArray(input);
        array[1] = 0;
        FileCopyUtils.copy(array, new FileOutputStream(result));
        input.close();
    }

    private final String rootDir = System.getProperty("catalina.home") + File.separator + "pdfFiles";
    private final String STORAGE_CONNECTING_STRING =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=nextbookpdfstorage;" +
                    "AccountKey=mOiuuhUrSiKRkPJAbBhXcujcxdkcf2qM36j22hjUnq3Zu88sH9yRW0OMClPB1jnIV0nn3+E+obCIV3pxLK/Mzw==";

    private final String ACCEPTED_IMAGE_EXTENSIONS = "jpg|jpeg|png|gif";
    private final String BOOK_FOLDER = "book-";
    private final String COVERS_FOLDER = "cover";
    private final String PREVIEW_FOLDER = "preview";
    private final String ORIGINAL_BOOK_FOLDER = "original";

    private final String ORIGINAL = "original";
    private final String PDF_EXTENSION = ".pdf";
    private final String PREVIEW = "preview";
    private final String BOOK = "book";

    private final String STORAGE_URL = "http://nextbookpdfstorage.blob.core.windows.net";
}
