package com.kutsyk.pdf.uploader.web;

import com.kutsyk.pdf.uploader.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileUploadController {

    @Autowired
    private PdfService pdfService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        String result = pdfService.downloadPdf(file);
        pdfService.setPasswordToPdfFile(file.getOriginalFilename());
        return result;
    }

    @RequestMapping("/")
    public ModelAndView showDocuments() {
        ModelAndView model = new ModelAndView("upload");
        return model;
    }

}