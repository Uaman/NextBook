package com.kutsyk.pdf.uploader.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
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
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileDataSource;
import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by KutsykV on 06.06.2015.
 */
public class PdfServiceImpl implements PdfService {

    String rootPath = System.getProperty("catalina.home");
    File dir;
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=nextbookpdfstorage;" +
                    "AccountKey=mOiuuhUrSiKRkPJAbBhXcujcxdkcf2qM36j22hjUnq3Zu88sH9yRW0OMClPB1jnIV0nn3+E+obCIV3pxLK/Mzw==";

    @Override
    public List<String> getAllFiles() {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("pdffiles");
            for (ListBlobItem blobItem : container.listBlobs()) {
                System.out.println(blobItem.getUri().toURL());
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String downloadPdf(MultipartFile file) {
        String name = "none";
        if (!file.isEmpty()) {
            try {
                name = file.getOriginalFilename();

                if (!name.toLowerCase().endsWith("pdf"))
                    return "You failed to upload " + name
                            + " because the file was empty.";
                byte[] bytes = file.getBytes();
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                dir = new File(rootPath + File.separator + "pdfFiles");
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + "temp.pdf");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                return dir.getAbsolutePath()
                        + File.separator + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

    @Override
    public void loadFileToStorage(String result) {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("pdffiles");
            container.createIfNotExists();
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
    public void setPasswordToPdfFile(String result) {
        try {
            PdfReader reader = new PdfReader(dir.getAbsolutePath()
                    + File.separator + "temp.pdf");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(result));
            stamper.setEncryption("user".getBytes(), "owner".getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuffer getPDFText(File pdfFile) throws IOException {
        PDFTextStream stream = new PDFTextStream(pdfFile);
        StringBuffer sb = new StringBuffer(1024);
        // get OutputTarget configured to pipe text to the provided StringBuffer
        OutputTarget tgt = OutputTarget.forBuffer(sb);
        stream.pipe(tgt);
        stream.close();
        return sb;
    }

    List<String> lines = new ArrayList<String>();
    String line = null;

    @Override
    public void changeFileMetaData() throws IOException {
        String strToFind = "%PDF";
        String message = "%KDF";
        String result = dir.getAbsolutePath()
                + File.separator + "temp.pdf";
        String outputFile = dir.getAbsolutePath()
                + File.separator + "modyfied.pdf";
        File file = new File(result);
        Scanner input = new Scanner(new FileReader(file));
        while (input.hasNextLine()) {
            final String checkline = input.nextLine();
            if (checkline.contains("%PDF-")) {
                checkline.replace(strToFind, message);
            }
            lines.add(checkline);
        }
        input.close();

        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter out = new BufferedWriter(fw);
        for (String s : lines)
            out.write(s);
        out.flush();
        out.close();

        input = new Scanner(new FileReader(file));
        while (input.hasNextLine()) {
            final String checkline = input.nextLine();
            System.out.println(checkline);
        }
        input.close();
    }

    @Override
    public void sendFileToStorage(String fileName) {

    }
}
