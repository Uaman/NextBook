package com.nextbook.controllers;

import com.nextbook.domain.upload.Constants;
import com.nextbook.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Oleh Kurpiak on 6/9/2015.
 */

@Controller
public class ViewController {

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public String onLoad(@RequestParam("url") final String url, Model model, HttpServletResponse response){
        model.addAttribute("title", "View - " + url);
        model.addAttribute("urlToFile", url);
        model.addAttribute("pass", Constants.USER_PASSWORD);
        return "view";
    }

}