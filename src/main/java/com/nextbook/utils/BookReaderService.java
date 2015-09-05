package com.nextbook.utils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by KutsykV on 05.09.2015.
 */
public class BookReaderService {

    private Book book;
    private User user;
    private String key;
    private final static String bookDir = "temp_book";

    BookReaderService() {

    }

    public Book getBookForUser(User user, Book book) {
        String key = buildKey(user, book);
        File bookCopy = createLocalCopy(book.getLinkToStorage(), key);
        String encodedFileName = setPasswordToPdfFile(user, bookCopy.getPath());
        bookCopy.delete();

        return null;
    }

    private String buildKey(User user, Book book) {
        return user.getId() + "-" + book.getLinkToStorage();
    }

    public String setPasswordToPdfFile(User user, String fileName) {
        String fileNameResult = bookDir+File.separator+fileName.substring(fileName.lastIndexOf("."+1))+"_encoded.pdf";
        try {
            PdfReader reader = new PdfReader(fileNameResult);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileName));
            stamper.setEncryption(user.getEmail().getBytes(), key.getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNameResult;
    }

    private File createLocalCopy(String fileUrl, String key) {
        try {
            String fileName = bookDir+File.separator+key + ".pdf"; //The file that will be saved on your computer
            URL link = new URL(fileUrl); //The file that you want to download
            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf)))
                out.write(buf, 0, n);
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(response);
            fos.close();
            return new File(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
