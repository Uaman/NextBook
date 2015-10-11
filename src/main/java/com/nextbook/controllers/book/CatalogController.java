package com.nextbook.controllers.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by KutsykV on 11.10.2015.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allBooks(Model model,Locale locale){
        return "catalog/catalog";
    }
}
