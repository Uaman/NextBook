package com.nextbook.controllers;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.services.IBookProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Stacy on 9/25/15.
 */
@Controller
@RequestMapping("/bookInfo")
public class BookViewController {
    @Autowired
    private IBookProvider bookProvider;

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String infoBook(@PathVariable("bookId")int bookId, Model model,Locale locale){
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/";
        BookPreview preview = new BookPreview(book,locale);
        model.addAttribute("book",preview);
        model.addAttribute("type",book.getTypeOfBook());
        model.addAttribute("category",getCategoryLocated(book.getSubCategory().getCategory(),locale));
        model.addAttribute("keywords", book.getKeywords());
        return "book/bookPage";
    }

    private String getCategoryLocated(Category category,Locale locate){
        String locatedCategory = "";
        if (locate.getLanguage().equals("uk")) {
            locatedCategory = category.getNameUa();
        } else if (locate.getLanguage().equals("ru")) {
            locatedCategory = category.getNameRu();
        } else {
            locatedCategory = category.getNameEn();
        }
        return locatedCategory;
    }

}
