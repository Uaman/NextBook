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
import java.nio.CharBuffer;
import java.util.*;

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
                File dir = new File(rootPath + File.separator + "pdffiles");
                if (!dir.exists())
                    dir.mkdirs();
                String resultFile = rootPath + File.separator + "pdffiles" + File.separator + mpf.getOriginalFilename();
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(rootPath + File.separator + "pdffiles" + File.separator + "modified.pdf"));
                setPasswordToPdfFile();
                changeFileMetaData(resultFile);
                loadFileToStorage(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("time: " + (System.currentTimeMillis() - time));
        }
    }

    @Override
    public void loadFileToStorage(String result) {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("pdffiles");
            container.createIfNotExists();
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
            container.uploadPermissions(containerPermissions);

            final String fileNameOnBlob = result.substring(result.lastIndexOf("\\") + 1);
            CloudBlockBlob blob = container.getBlockBlobReference(fileNameOnBlob);
            if (!blob.exists()) {
                File source = new File(result);
                blob.upload(new FileInputStream(source), source.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPasswordToPdfFile() {
        try {
            PdfReader reader = new PdfReader(dir + File.separator + "modified.pdf");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dir + File.separator + "temp.pdf"));
            stamper.setEncryption("user".getBytes(), "owner".getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public StringBuffer getPDFText(File pdfFile) throws IOException {
        PDFTextStream stream = new PDFTextStream(pdfFile);
        StringBuffer sb = new StringBuffer(1024);
        // get OutputTarget configured to pipe text to the provided StringBuffer
        OutputTarget tgt = OutputTarget.forBuffer(sb);
        stream.pipe(tgt);
        stream.close();
        return sb;
    }
*/
    StringBuilder lines = new StringBuilder();
    String line = null;

    @Override
    public void changeFileMetaData(String outputFile) throws IOException {
        String strToFind = "%PDF";
        String message = "%KDF";
        String source = dir + File.separator + "temp.pdf";
        File file = new File(source);
        Scanner input = new Scanner(new FileReader(file));
        while (input.hasNextLine()) {
            final String checkline = input.nextLine();
            if (checkline.contains("%PDF-")) {
                checkline.replace(strToFind, message);
            }
            lines.append(checkline);
        }
        input.close();

        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter out = new BufferedWriter(fw);
        out.write(lines.toString());
        out.flush();
        out.close();
/*
        input = new Scanner(new FileReader(file));
        while (input.hasNextLine()) {
            final String checkline = input.nextLine();
            System.out.println(checkline);
        }
*/
        input.close();
    }

    @Override
    public void sendFileToStorage(String fileName) {

    }
}