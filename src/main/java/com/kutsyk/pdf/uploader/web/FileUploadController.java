package com.kutsyk.pdf.uploader.web;

import com.kutsyk.pdf.uploader.domain.FileMeta;
import com.kutsyk.pdf.uploader.service.PdfService;
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
@RequestMapping("/controller")
public class FileUploadController {

    @Autowired
    private PdfService pdfService;

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;

    /**
     * ************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     *
     * @param request  : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     * **************************************************
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        //2. get each file
        while (itr.hasNext()) {
            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());
            //2.2 if files > 10 remove the first from the list
            if (files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {
                fileMeta.setBytes(mpf.getBytes());
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "pdffiles");
                if (!dir.exists())
                    dir.mkdirs();
                String resultFile = rootPath + File.separator + "pdffiles" + File.separator + mpf.getOriginalFilename();
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(rootPath + File.separator + "pdffiles" + File.separator + "temp.pdf"));
                pdfService.setPasswordToPdfFile(resultFile);
                pdfService.loadFileToStorage(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.add(fileMeta);
        }
        return files;
    }

    /**
     * ************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     *
     * @param response : passed by the server
     * @param value    : value from the URL
     * @return void
     * **************************************************
     */
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
