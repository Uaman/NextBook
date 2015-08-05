package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.forms.admin.AdminAuthorForm;
import com.nextbook.domain.pojo.Author;
import com.nextbook.services.IAuthorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stacy on 7/25/15.
 */
@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {
    @Autowired
    private IAuthorProvider authorProvider;

    @RequestMapping(value = "/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String authors(Model model) {
        List<Author> authors = authorProvider.getAll();
        model.addAttribute("authors", authors);
        return "admin/authors/authors";
    }
    @RequestMapping(value = "/update-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateAuthor(@ModelAttribute("adminAuthorForm")AdminAuthorForm adminAuthorForm) {
        Author author = authorProvider.getById(adminAuthorForm.getId());
        if (author == null) author = new Author();
            author.setFirstNameEn(adminAuthorForm.getFirstNameEn());
            author.setFirstNameUa(adminAuthorForm.getFirstNameUa());
            author.setFirstNameRu(adminAuthorForm.getFirstNameRu());
            author.setLastNameEn(adminAuthorForm.getLastNameEn());
            author.setLastNameRu(adminAuthorForm.getLastNameRu());
            author.setLastNameUa(adminAuthorForm.getLastNameUa());
            authorProvider.updateAuthor(author);
        return "redirect:/admin/authors/all";
    }


    @RequestMapping(value = "/delete-author/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorProvider.deleteAuthor(id);
        return "redirect:/admin/authors/all";

    }
    @RequestMapping(value = "/author-books/{id}",method = RequestMethod.GET)
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    public String getAuthorBooks(@PathVariable("id") int id){
        return "admin/authors/books";
    }
    @RequestMapping(value = "/authors-filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    List<Author> filter(@RequestBody AuthorCriterion authorCriterion){
        List<Author> result = authorProvider.getAuthorsByCriterion(authorCriterion);
        if(result == null)
            result = new ArrayList<Author>();
        return result;
    }
}