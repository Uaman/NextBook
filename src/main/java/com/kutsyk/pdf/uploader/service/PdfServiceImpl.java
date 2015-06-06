package com.kutsyk.pdf.uploader.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by KutsykV on 06.06.2015.
 */
public class PdfServiceImpl implements PdfService {

    String rootPath = System.getProperty("catalina.home");
    File dir;

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
                        + File.separator+name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
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

    @Override
    public void sendFileToStorage(String fileName) {

    }
}
