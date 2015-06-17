package com.kutsyk.pdf.uploader.web;

import com.itextpdf.text.pdf.PdfReader;
import com.kutsyk.pdf.uploader.domain.Constants;
import com.kutsyk.pdf.uploader.domain.FileMeta;
import com.kutsyk.pdf.uploader.service.PdfService;
import com.microsoft.azure.storage.blob.CloudBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.LinkedList;

/**
 * Created by Oleh Kurpiak on 6/9/2015.
 */

@Controller
public class ViewController {

    @Autowired
    private PdfService pdfService;


    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public String onLoad(@RequestParam("url") final String url, Model model, HttpServletResponse response){
        model.addAttribute("title", "View - " + url);
        model.addAttribute("urlToFile", url);
        model.addAttribute("pass", Constants.USER_PASSWORD);
        return "view";
    }

}