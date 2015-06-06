package com.kutsyk.pdf.uploader.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by KutsykV on 06.06.2015.
 */
public class PdfServiceImpl implements PdfService {

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
                File dir = new File(rootPath + File.separator + "pdfFiles");
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());

                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

    @Override
    public void setPasswordToPdfFile(String fileName) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            writer.setEncryption("userpass".getBytes(), "cp123".getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
            writer.createXmpMetadata();
            document.open();
            document.add(new Paragraph("This is create PDF with Password demo."));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFileToStorage(String fileName) {

    }
}
