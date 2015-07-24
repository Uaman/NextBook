package com.nextbook.controllers;

import com.microsoft.azure.storage.blob.CloudBlob;
import com.nextbook.domain.upload.FileMeta;
import com.nextbook.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.LinkedList;

@Controller
public class FileUploadController {

////    @Autowired
////    private PdfService pdfService;
//    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
//
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
//        pdfService.uploadFile(request);
//        return files;
//    }
//
//    @RequestMapping("/")
//    public ModelAndView main() {
//        ModelAndView model = new ModelAndView("upload");
//        LinkedList<CloudBlob> filesInStorage = (LinkedList) pdfService.getAllFiles();
//        files.clear();
//        for (CloudBlob file : filesInStorage) {
//            FileMeta fileMeta = new FileMeta();
//            try {
//                fileMeta.setFileName(file.getName());
//                fileMeta.setFileSize(file.getProperties().getLength() / 1024 + " Kb");
//                fileMeta.setFileType("pdf");
//
//                try {
//                    fileMeta.setUrl(file.getUri().toURL().toString());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                files.add(fileMeta);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//        model.addObject("files", files);
//        return model;
//    }

}
