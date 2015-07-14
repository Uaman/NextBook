package com.kutsyk.pdf.uploader.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.kutsyk.pdf.uploader.domain.FileMeta;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFOperator;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.FileDataSource;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
//import java.nio.file.*;
import java.util.*;

import com.kutsyk.pdf.uploader.domain.Constants;

/**
 * Created by KutsykV on 06.06.2015.
 */
public class PdfServiceImpl implements PdfService {

    private String rootPath;
    private String dir;
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=nextbookpdfstorage;" +
                    "AccountKey=mOiuuhUrSiKRkPJAbBhXcujcxdkcf2qM36j22hjUnq3Zu88sH9yRW0OMClPB1jnIV0nn3+E+obCIV3pxLK/Mzw==";
    private long time;



    public PdfServiceImpl() {
        this.rootPath = System.getProperty("catalina.home");
        this.dir = this.rootPath + File.separator + "pdfFiles";
    }

    @Override
    public List<CloudBlob> getAllFiles() {
        List<CloudBlob> result = new LinkedList<CloudBlob>();
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("pdffiles");

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
    public void uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        while (itr.hasNext()) {
            time = System.currentTimeMillis();
            mpf = request.getFile(itr.next());
            try {
                File folder = new File(dir);
                if (!folder.exists())
                    folder.mkdirs();

                File resultFile = new File(dir + File.separator + mpf.getOriginalFilename());
                File tempFile = new File(dir + File.separator + "temp.pdf");
                File encodedFile = new File(dir + File.separator + "encoded.pdf");

                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(tempFile));
                setPasswordToPdfFile(tempFile, encodedFile);
                changeFileMetaData(encodedFile, resultFile);
                loadFileToStorage(resultFile);

                deleteFile(resultFile);
                deleteFile(tempFile);
                deleteFile(encodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("time: " + (System.currentTimeMillis() - time));
        }
    }

    private void deleteFile(File f){
        try{
            f.delete();
        } catch(Exception e){}
    }

    public void loadFileToStorage(File result) {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("pdffiles");
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

    public void setPasswordToPdfFile(File source, File result) {
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

    @Override
    public void changeFileMetaData(File source, File result) throws IOException {
        FileInputStream input = new FileInputStream(source);
        byte[] array = IOUtils.toByteArray(input);
        array[1] = 0;
        FileCopyUtils.copy(array, new FileOutputStream(result));
        input.close();
    }
}
