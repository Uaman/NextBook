package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.criterion.AuthorCriterion;
import com.nextbook.domain.entities.AuthorEntity;
import com.nextbook.domain.forms.admin.AdminAuthorForm;
import com.nextbook.services.IAuthorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Stacy on 7/25/15.
 */
@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {
    @Autowired
    private IAuthorProvider authorProvider;

    @RequestMapping(value = "/all")
    @PreAuthorize("@Secure.isAdmin()")
    public String authors(Model model) {
        List<AuthorEntity> authors = authorProvider.getAll();
        model.addAttribute("authors", authors);
        return "admin/authors/authors";
    }
    
    @RequestMapping(value = "/update-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody AuthorEntity updateAuthor(@RequestBody AdminAuthorForm adminAuthorForm) {
        AuthorEntity author = authorProvider.getById(adminAuthorForm.getId());
        if (author == null) author = new AuthorEntity();
            author.setFirstNameEn(adminAuthorForm.getFirstNameEn());
            author.setFirstNameUa(adminAuthorForm.getFirstNameUa());
            author.setFirstNameRu(adminAuthorForm.getFirstNameRu());
            author.setLastNameEn(adminAuthorForm.getLastNameEn());
            author.setLastNameRu(adminAuthorForm.getLastNameRu());
            author.setLastNameUa(adminAuthorForm.getLastNameUa());
            authorProvider.updateAuthor(author);
        return author;
    }

    @RequestMapping(value="/edit-author/{id}")
    @PreAuthorize("@Secure.isAdmin()")
    public String updatePublisherPage(@PathVariable("id") int id, Model model) {
        AuthorEntity author = authorProvider.getById(id);
        if (author==null) {
            model.addAttribute("edit", false);
        } else {
            model.addAttribute("edit", true);
            model.addAttribute("author", author);
        }
        return "/admin/authors/edit-author";
    }
    @RequestMapping(value="/add-author")
    @PreAuthorize("@Secure.isAdmin()")
    public String addAuthor(Model model) {
        model.addAttribute("edit", false);
        return "/admin/authors/edit-author";
    }

    @RequestMapping(value = "/delete-author/{id}", method = RequestMethod.GET)
    @PreAuthorize("@Secure.isAdmin()")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorProvider.deleteAuthor(id);
        return "redirect:/admin/authors/all";

    }

    @RequestMapping(value = "/authors-filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody
    List<AuthorEntity> filter(@RequestBody AuthorCriterion authorCriterion){
        List<AuthorEntity> result = authorProvider.getAuthorsByCriterion(authorCriterion);
        if(result == null)
            result = new ArrayList<AuthorEntity>();
        return result;
    }
}