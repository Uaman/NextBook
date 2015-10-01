package com.nextbook.controllers;

import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.IBookStorageProvider;
import com.nextbook.services.IOrderProvider;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;

/**
 * Created by Stacy on 9/25/15.
 */
@Controller
@RequestMapping("/bookInfo")
public class BookViewController {
    @Autowired
    private IBookProvider bookProvider;
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private IBookStorageProvider bookStorageProvider;
    @Autowired
    private IPublisherProvider publisherProvider;
    @Autowired
    private IOrderProvider orderProvider;

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public String infoBook(@PathVariable("bookId")int bookId, Model model,Locale locale){
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/";
        User user = sessionUtils.getCurrentUser();
        BookPreview preview = new BookPreview(book,locale);
        model.addAttribute("book",preview);
        model.addAttribute("type",book.getTypeOfBook());
        model.addAttribute("category",getCategoryLocated(book.getSubCategory().getCategory(),locale));
        model.addAttribute("keywords", book.getKeywords());
        model.addAttribute("bookName", bookNameInLocale(book, locale));

        if(userBuyBook(user, book)){
            model.addAttribute("urlToFile", book.getLinkToStorage());
            model.addAttribute("pass", Constants.USER_PASSWORD);
        } else {
            model.addAttribute("urlToFile", bookStorageProvider.getUrlForPreviewBook(book.getId()));
            model.addAttribute("pass", 1111);
        }

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

    private String bookNameInLocale(Book book, Locale locale){
        String bookName;
        if (locale.getLanguage().equals("uk")) {
            bookName = book.getUaName();
        } else if (locale.getLanguage().equals("ru")) {
            bookName = book.getRuName();
        } else {
            bookName = book.getEnName();
        }
        return bookName;
    }

    private boolean userBuyBook(User user, Book book){
        if(user == null)
            return false;
/*
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher.getId() == book.getPublisher().getId())
            return true;

        //check if admin or moderator
        if(user.getRole().getId() == 4 || user.getRole().getId() == 5)
            return true;
*/
        Order order = orderProvider.getOrderByUserAndBook(user, book);
        if(order != null && order.isPaid())
            return true;
        return false;
    }

}
