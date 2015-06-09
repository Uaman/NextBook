package com.kutsyk.pdf.uploader.web;

import com.kutsyk.pdf.uploader.domain.FileMeta;
import com.kutsyk.pdf.uploader.service.PdfService;
import com.microsoft.azure.storage.blob.CloudBlob;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//@Controller
//public class FileUploadController {
//
//    @Autowired
//    private PdfService pdfService;
//
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
//        String result = pdfService.downloadPdf(file);
//        pdfService.setPasswordToPdfFile(result);
//        pdfService.loadFileToStorage(result);
//        return "redirect:/";
//    }
//
//    @RequestMapping("/")
//    public ModelAndView showDocuments() {
//        ModelAndView model = new ModelAndView("upload");
//        List<String> files = pdfService.getAllFiles();
//        return model;
//    }
//
//}

@Controller
public class FileUploadController {

    @Autowired
    private PdfService pdfService;

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        pdfService.downloadFile(request);
        return "redirect:/";
    }

    @RequestMapping("/")
    public ModelAndView main() {
        ModelAndView model = new ModelAndView("upload");
        LinkedList<CloudBlob> filesInStorage = (LinkedList) pdfService.getAllFiles();
        files.clear();
        for (CloudBlob file : filesInStorage) {
            FileMeta fileMeta = new FileMeta();
            try {
                fileMeta.setFileName(file.getName());
                fileMeta.setFileSize(file.getProperties().getLength() / 1024 + " Kb");
                fileMeta.setFileType("pdf");
                files.add(fileMeta);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        model.addObject("files", files);
        return model;
    }

}
