package com.nextbook.controllers;

import com.nextbook.domain.forms.AdminAuthorForm;
import com.nextbook.domain.pojo.Author;
import com.nextbook.services.IAuthorProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.inject.Inject;

/**
 * Created by Stacy on 7/25/15.
 */
@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {
    @Inject
    private IAuthorProvider authorProvider;

    @RequestMapping(value = "/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String authors(Model model) {
        List<Author> authors = authorProvider.getAll();
        model.addAttribute("authors", authors);
        return "authors/authors";
    }


    @RequestMapping(value = "/add-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addAuthor(@ModelAttribute("adminAuthorForm")AdminAuthorForm adminAuthorForm) {
        Author author = new Author();
        author.setFirstNameEn(adminAuthorForm.getFirstNameEn());
        author.setFirstNameUa(adminAuthorForm.getFirstNameUa());
        author.setFirstNameRu(adminAuthorForm.getFirstNameRu());
        author.setLastNameEn(adminAuthorForm.getLastNameEn());
        author.setLastNameRu(adminAuthorForm.getLastNameRu());
        author.setLastNameUa(adminAuthorForm.getLastNameUa());
        authorProvider.updateAuthor(author);
        return "redirect:/admin/authors/all";
    }
    @RequestMapping(value = "/update-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateAuthor(@ModelAttribute("adminAuthorForm")AdminAuthorForm adminAuthorForm) {
        Author author = authorProvider.getById(adminAuthorForm.getId());
        if (author != null) {
            author.setFirstNameEn(adminAuthorForm.getFirstNameEn());
            author.setFirstNameUa(adminAuthorForm.getFirstNameUa());
            author.setFirstNameRu(adminAuthorForm.getFirstNameRu());
            author.setLastNameEn(adminAuthorForm.getLastNameEn());
            author.setLastNameRu(adminAuthorForm.getLastNameRu());
            author.setLastNameUa(adminAuthorForm.getLastNameUa());
            authorProvider.updateAuthor(author);
        }
            return "redirect:/admin/authors/all";

    }


    @RequestMapping(value = "/delete-author/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorProvider.deleteAuthor(id);
        return "redirect:/admin/authors/all";

    }
}