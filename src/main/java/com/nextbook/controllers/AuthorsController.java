package com.nextbook.controllers;

import com.nextbook.domain.entities.AuthorEntity;
import com.nextbook.domain.forms.author.AuthorRegisterForm;
import com.nextbook.services.IAuthorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/4/2015
 * Time: 4:19 PM
 */
@Controller
@RequestMapping("/author")
public class AuthorsController {

    @Autowired
    private IAuthorProvider authorProvider;

    @RequestMapping(value = "/new-author", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody boolean newAuthor(@RequestBody AuthorRegisterForm authorRegisterForm) {
        AuthorEntity author = new AuthorEntity();
        author.setFirstNameEn(authorRegisterForm.getFirstNameEn());
        author.setFirstNameUa(authorRegisterForm.getFirstNameUa());
        author.setFirstNameRu(authorRegisterForm.getFirstNameRu());
        author.setLastNameEn(authorRegisterForm.getLastNameEn());
        author.setLastNameRu(authorRegisterForm.getLastNameRu());
        author.setLastNameUa(authorRegisterForm.getLastNameUa());
        authorProvider.updateAuthor(author);
        return true;
    }

}
