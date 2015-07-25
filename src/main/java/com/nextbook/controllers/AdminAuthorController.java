package com.nextbook.controllers;

import com.nextbook.domain.forms.AdminAuthorForm;
import com.nextbook.domain.pojo.Author;
import com.nextbook.services.IAuthorProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/update-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@RequestBody AdminAuthorForm tempAuthor) {
        Author author = authorProvider.getById(tempAuthor.getId());
        if (author != null) {
            author.setFirstNameEn(tempAuthor.getFirstNameEn());
            author.setFirstNameUa(tempAuthor.getFirstNameUa());
            author.setFirstNameRu(tempAuthor.getFirstNameRu());
            author.setLastNameEn(tempAuthor.getLastNameEn());
            author.setLastNameRu(tempAuthor.getLastNameRu());
            author.setLastNameUa(tempAuthor.getLastNameUa());
            authorProvider.updateAuthor(author);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/add-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUser(@RequestBody AdminAuthorForm tempAuthor) {
        Author author = new Author();
        author.setFirstNameEn(tempAuthor.getFirstNameEn());
        author.setFirstNameUa(tempAuthor.getFirstNameUa());
        author.setFirstNameRu(tempAuthor.getFirstNameRu());
        author.setLastNameEn(tempAuthor.getLastNameEn());
        author.setLastNameRu(tempAuthor.getLastNameRu());
        author.setLastNameUa(tempAuthor.getLastNameUa());
        authorProvider.updateAuthor(author);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete-author/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        authorProvider.deleteAuthor(id);
        return "redirect:/all";

    }
}