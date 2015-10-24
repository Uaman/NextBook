package com.nextbook.services;

import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.AuthorPreview;
import com.nextbook.domain.response.ResponseForAutoComplete;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Created by oleh on 24.10.2015.
 */
@Service
public interface ICommonMethodsProvider {

    List<AuthorPreview> formAuthorsInLocale(List<BookAuthor> authors, String language);

    List<ResponseForAutoComplete> formAuthorsForAutoComplete(List<Author> authors, String language);

    boolean checkBookToUser(User user, Book book);

    boolean userBuyBook(User user, Book book);

    public String getCategoryLocated(Category category, Locale locate);

    public String bookNameInLocale(Book book, Locale locale);

}
