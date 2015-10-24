package com.nextbook.services.impl;

import com.nextbook.domain.enums.Status;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.*;
import com.nextbook.domain.response.ResponseForAutoComplete;
import com.nextbook.services.ICommonMethodsProvider;
import com.nextbook.services.IOrderProvider;
import com.nextbook.services.IPublisherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by oleh on 24.10.2015.
 */
@Service
public class CommonMethodsProvider implements ICommonMethodsProvider {

    @Autowired
    private IPublisherProvider publisherProvider;

    @Autowired
    private IOrderProvider orderProvider;

    @Override
    public List<AuthorPreview> formAuthorsInLocale(List<BookAuthor> authors, String language){
        List<AuthorPreview> result = new ArrayList<AuthorPreview>();
        if(authors != null) {
            for (BookAuthor bookAuthor : authors) {
                String name;
                Author author = bookAuthor.getAuthor();
                if (language.equals("uk")) {
                    name = author.getFirstNameUa() + ' ' + author.getLastNameUa();
                } else if (language.equals("ru")) {
                    name = author.getFirstNameRu() + ' ' + author.getLastNameRu();
                } else {
                    name = author.getFirstNameEn() + ' ' + author.getLastNameEn();
                }
                result.add(new AuthorPreview(author.getId(), name));
            }
        }
        return result;
    }

    @Override
    public List<ResponseForAutoComplete> formAuthorsForAutoComplete(List<Author> authors, String language){
        List<ResponseForAutoComplete> result = new ArrayList<ResponseForAutoComplete>();
        if(authors != null) {
            for (Author author : authors) {
                String value;
                if (language.equals("uk")) {
                    value = author.getFirstNameUa() + ' ' + author.getLastNameUa();
                } else if (language.equals("ru")) {
                    value = author.getFirstNameRu() + ' ' + author.getLastNameRu();
                } else {
                    value = author.getFirstNameEn() + ' ' + author.getLastNameEn();
                }
                result.add(new ResponseForAutoComplete(author.getId(), value));
            }
        }
        return result;
    }

    @Override
    public boolean checkBookToUser(User user, Book book){
        if(user == null || book == null)
            return false;
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null)
            return false;

        return book.getPublisher().getId() == publisher.getId();
    }

    @Override
    public boolean userBuyBook(User user, Book book){
        if(user == null)
            return false;

        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher != null && publisher.getId() == book.getPublisher().getId())
            return true;

        //check if admin or moderator
        if(user.getRole().getId() == 4 || user.getRole().getId() == 5)
            return true;

        if(book.getStatus() != Status.ACTIVE)
            return false;

        Order order = orderProvider.getOrderByUserAndBook(user, book);
        if(order != null && order.isPaid())
            return true;
        return false;
    }

    @Override
    public String getCategoryLocated(Category category, Locale locate){
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

    @Override
    public String bookNameInLocale(Book book, Locale locale){
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
}
