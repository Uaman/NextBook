package com.nextbook.services.impl;

import com.itextpdf.text.pdf.*;
import com.microsoft.azure.storage.*;
import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.upload.Constants;
import com.microsoft.azure.storage.blob.*;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.IBookStorageProvider;
import com.nextbook.utils.FilesUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;


/**
 * Created by KutsykV on 06.06.2015.
 */
public class BookStorageProvider implements IBookStorageProvider {

    @Autowired
    private IBookProvider bookProvider;

    @Override
    public boolean uploadBookToLocalStorage(int id, MultipartFile file) {
        if(!isPdf(file.getOriginalFilename()))
            return false;
        String path = BOOK_FOLDER + id;
        File bookDir = new File(rootDir + '/' + path);
        if (!bookDir.exists())
            bookDir.mkdirs();
        boolean saved = saveBookWithPath(bookDir, file);
        if(saved)
            saveBookForPreview(bookDir);
        return saved;
    }

    private boolean saveBookWithPath(File bookDir, MultipartFile file){
        try {
            File originalFolder = new File(bookDir + "/" + ORIGINAL_BOOK_FOLDER);
            if(!originalFolder.exists())
                originalFolder.mkdir();
            File fileWithoutPass = new File(originalFolder + "/" + ORIGINAL + PDF_EXTENSION);
            if(!fileWithoutPass.exists())
                fileWithoutPass.createNewFile();
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(fileWithoutPass));
            File fileWithPass = new File(bookDir + "/" + BOOK + PDF_EXTENSION);
            setPasswordToPdfFile(fileWithoutPass, fileWithPass);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveBookForPreview(File bookDir){
        try {
            String pathToOriginalBook = bookDir + "/" + ORIGINAL_BOOK_FOLDER + "/" + ORIGINAL + PDF_EXTENSION;
            PdfReader reader = new PdfReader(pathToOriginalBook);
            int number = reader.getNumberOfPages();
            int endRange = number/10;
            if(endRange < 10)
                endRange = 10;
            reader.selectPages("1-"+endRange);
            File previewFile = new File(bookDir + "/" + PREVIEW_FOLDER);
            if(!previewFile.exists())
                previewFile.mkdir();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(previewFile + "/" + PREVIEW + PDF_EXTENSION));
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
        String path = BOOK_FOLDER + id + '/' + COVERS_FOLDER;
        File coverDir = new File(rootDir + "/" + path);
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
            File resultFile = new File(coverDir + "/" + fileName);
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(resultFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void deleteLocalFolder(int bookId) {
        File folder = new File(rootDir + "/" + BOOK_FOLDER + bookId);
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
    public void getGalleryPhoto(OutputStream outputStream, int bookId, int photoNumber) {
        try{
            CloudBlobContainer container = initContainer(BOOK_FOLDER + bookId);
            CloudBlobDirectory gallery = container.getDirectoryReference(GALLERY_FOLDER);

            for (ListBlobItem blobItem : gallery.listBlobs()) {
                if (blobItem instanceof CloudBlob) {
                    CloudBlob blob = (CloudBlob) blobItem;
                    if(photoNumber-- == 0) {
                        blob.download(outputStream);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadBookToStorage(int id) {
        File bookDir = new File(rootDir + "/" + BOOK_FOLDER + id);
        if (!bookDir.exists()) {
            return null;
        }
        String containerName = BOOK_FOLDER + id;
        String uri = null;
        for (File file : bookDir.listFiles()) {
            if (!file.isFile())
                continue;
            uri = uploadBookToStorage(containerName, file);
            break;
        }
        if(uri != null) {
            uploadPreviewBookToStorage(containerName, id);
            uploadCoversToStorage(containerName, id);
            //uploadGalleryToStorage(containerName, id);
            deleteLocalFolder(id);
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
        String pathToPreviewFile = BOOK_FOLDER + bookId + '/' + PREVIEW_FOLDER + '/' + PREVIEW + PDF_EXTENSION;
        File previewFile = new File(rootDir + "/" + pathToPreviewFile);
        if(!previewFile.exists())
            return;
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(PREVIEW_FOLDER + '/' + PREVIEW + PDF_EXTENSION);
            if (blob.exists())
                blob.delete();
            blob.upload(new FileInputStream(previewFile), previewFile.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadCoversToStorage(String containerName, int id) {
        File bookDir = new File(rootDir + '/' + BOOK_FOLDER + id + '/' + COVERS_FOLDER);
        if (!bookDir.exists()) {
            return;
        }
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlob blob;
            for (File file : bookDir.listFiles()) {
                try {
                    blob = container.getBlockBlobReference(COVERS_FOLDER + '/' + file.getName());
                    if (blob.exists())
                        blob.delete();
                    blob.upload(new FileInputStream(file), file.length());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean uploadGalleryPhoto(int bookId, MultipartFile file){
        boolean saved = false;
        File galleryFolder = new File(rootDir + "/" + BOOK_FOLDER + bookId + "/" + GALLERY_FOLDER);
        if(!galleryFolder.exists())
            galleryFolder.mkdir();
        if(file == null
                || file.getName() == null
                || "".equals(file.getName())
                || file.getSize() == 0){
            return false;
        }
        int numberOfPhotos = newPhotoNumber(BOOK_FOLDER + bookId);
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = file.getBytes();
            if (bytes.length == 0)
                return saved;

            String photoName = numberOfPhotos + "." + FilesUtils.getFIleExtensions(file.getOriginalFilename());
            File fileToStore = new File(galleryFolder.getPath() + "/" + photoName);
            stream = new BufferedOutputStream(new FileOutputStream(fileToStore));
            stream.write(bytes);
            stream.close();
            CloudBlobContainer container = initContainer(BOOK_FOLDER + bookId);
            CloudBlob blob = container.getBlockBlobReference(GALLERY_FOLDER + '/' + photoName);
            if (blob.exists())
                blob.delete();
            blob.upload(new FileInputStream(fileToStore), fileToStore.length());
            saved = true;
            fileToStore.delete();
        } catch (Exception e) {
            e.printStackTrace();
            saved = false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return saved;
    }

    private int newPhotoNumber(String containerName){
        int photoNumber = 0;
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlobDirectory gallery = container.getDirectoryReference(GALLERY_FOLDER);
            String uri = null;
            if (gallery != null) {
                for (ListBlobItem blobItem : gallery.listBlobs()) {
                    if (blobItem instanceof CloudBlob) {
                        uri = ((CloudBlob)blobItem).getUri().toString();
                    }
                }
            }
            if(uri != null){
                int galleryIndex = uri.indexOf(GALLERY_FOLDER) + GALLERY_FOLDER.length() + 1;
                int dotIndex = uri.lastIndexOf('.');
                if(dotIndex < 0)
                    dotIndex = uri.length();
                photoNumber = Integer.valueOf(uri.substring(galleryIndex, dotIndex)) + 1;
            }
        } catch (Exception e){
            e.printStackTrace();
            photoNumber = -1;
        }
        return photoNumber;
    }

    @Override
    public boolean deleteGalleryPhoto(int bookId, int photoNumber) {
        boolean success = false;
        try {
            CloudBlobContainer container = initContainer(BOOK_FOLDER + bookId);
            CloudBlobDirectory gallery = container.getDirectoryReference(GALLERY_FOLDER);
            if(gallery != null) {
                for (ListBlobItem blobItem : gallery.listBlobs()) {
                    if (blobItem instanceof CloudBlob) {
                        if(photoNumber-- == 0){
                            ((CloudBlob)blobItem).delete();
                            success = true;
                            break;
                        }
                    }
                }
            }
            if(photoNumber > 0)
                success = false;
        } catch(Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private void uploadGalleryToStorage(String containerName, int id) {
        File bookDir = new File(rootDir + '/' + BOOK_FOLDER + id + '/' + GALLERY_FOLDER);
        if (!bookDir.exists()) {
            return;
        }
        try {
            CloudBlobContainer container = initContainer(containerName);
            CloudBlockBlob blob;
            for (File file : bookDir.listFiles()) {
                try {
                    blob = container.getBlockBlobReference(GALLERY_FOLDER + '/' + file.getName());
                    if (blob.exists())
                        blob.delete();
                    blob.upload(new FileInputStream(file), file.length());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfPhotosInGallery(int bookId) {
        try {
            CloudBlobContainer container = initContainer(BOOK_FOLDER + bookId);
            CloudBlobDirectory gallery = container.getDirectoryReference(GALLERY_FOLDER);
            int count = 0;
            if(gallery != null) {
                for (ListBlobItem blobItem : gallery.listBlobs()) {
                    if (blobItem instanceof CloudBlob) {
                        ++count;
                    }
                }
            }
            /*
            File galleryFolder = new File(rootDir + '/' + BOOK_FOLDER + bookId + '/' + GALLERY_FOLDER);
            if(galleryFolder.exists())
                count += galleryFolder.listFiles().length;
*/
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void deleteFile(File f) {
        try {
            f.delete();
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

    private final String rootDir = System.getProperty("catalina.home") + '/' + "pdfFiles";
    private final String STORAGE_CONNECTING_STRING =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=nextbookpdfstorage;" +
                    "AccountKey=mOiuuhUrSiKRkPJAbBhXcujcxdkcf2qM36j22hjUnq3Zu88sH9yRW0OMClPB1jnIV0nn3+E+obCIV3pxLK/Mzw==";

    private final String ACCEPTED_IMAGE_EXTENSIONS = "jpg|jpeg|png|gif";
    private final String BOOK_FOLDER = "book-";
    private final String COVERS_FOLDER = "cover";
    private final String PREVIEW_FOLDER = "preview";
    private final String ORIGINAL_BOOK_FOLDER = "original";
    private final String GALLERY_FOLDER = "gallery";

    private final String ORIGINAL = "original";
    private final String PDF_EXTENSION = ".pdf";
    private final String PREVIEW = "preview";
    private final String BOOK = "book";

    private final String STORAGE_URL = "http://nextbookpdfstorage.blob.core.windows.net";
}
