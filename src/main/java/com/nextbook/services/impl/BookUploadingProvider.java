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
import java.net.URISyntaxException;
import java.util.*;


/**
 * Created by KutsykV on 06.06.2015.
 */
public class BookUploadingProvider implements IBookUploadingProvider {

    @Override
    public List<CloudBlob> getAllFiles(String containerName) {
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

    @Override
    public boolean uploadFileToLocalStorage(int id, MultipartFile file) {
        String path = bookFolderName + id;
        File bookDir = new File(rootDir + File.separator + path);
        if (!bookDir.exists())
            bookDir.mkdirs();
        try {
            File resultFile = new File(bookDir + File.separator + file.getOriginalFilename());
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(resultFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean uploadCoverToLocalStorage(int id, MultipartFile file, Cover cover) {
        String path = bookFolderName + id + File.separator + coverFolderName;
        File coverDir = new File(rootDir + File.separator + path);
        if (!coverDir.exists())
            coverDir.mkdirs();
        try {
            String fileName = cover.toString()+"_"+file.getOriginalFilename();
            File resultFile = new File(coverDir + File.separator + fileName);
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(resultFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String uploadBookToStorage(int id) {
        File bookDir = new File(rootDir + File.separator + bookFolderName + id);
        if (!bookDir.exists()) {
            return "";
        }
        for (File file : bookDir.listFiles()) {
            if(!file.isFile())
                continue;
            uploadFileToStorage(bookFolderName+id+"/", file);
            deleteFile(file);
        }
        for(CloudBlob blob: getAllFiles(bookFolderName+id))
            try {
                if(blob.getName().endsWith(".pdf"))
                    return blob.getUri().toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        return "";
    }

    @Override
    public String uploadCoversToStorage(int id) {
        File bookDir = new File(rootDir + File.separator + bookFolderName + id + File.separator + "cover");
        if (!bookDir.exists()) {
            return "";
        }
        for (File file : bookDir.listFiles()) {
            if(!file.isFile())
                continue;
            uploadFileToStorage(bookFolderName+id+"/", file);
            deleteFile(file);
        }
        return "";
    }

    private void deleteFile(File f) {
        try {
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFileToStorage(String prefix, File result) {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(STORAGE_CONNECTING_STRING);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference(prefix);
            container.createIfNotExists();
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
            container.uploadPermissions(containerPermissions);

            String absolutePath = result.getAbsolutePath();
            final String fileNameOnBlob = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1);
            CloudBlockBlob blob = container.getBlockBlobReference(fileNameOnBlob);
            if (!blob.exists()) {
                blob.upload(new FileInputStream(result), result.length());
            }
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

    private void changeFileMetaData(File source, File result) throws IOException {
        FileInputStream input = new FileInputStream(source);
        byte[] array = IOUtils.toByteArray(input);
        array[1] = 0;
        FileCopyUtils.copy(array, new FileOutputStream(result));
        input.close();
    }

    private static final String rootDir = System.getProperty("catalina.home") + File.separator + "pdfFiles";
    private static final String STORAGE_CONNECTING_STRING =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=nextbookpdfstorage;" +
                    "AccountKey=mOiuuhUrSiKRkPJAbBhXcujcxdkcf2qM36j22hjUnq3Zu88sH9yRW0OMClPB1jnIV0nn3+E+obCIV3pxLK/Mzw==";

    private static final String acceptedCoverExtensions = "jpg|jpeg|png|gif";
    private static final String bookFolderName = "book-";
    private static final String coverFolderName = "cover";
}
