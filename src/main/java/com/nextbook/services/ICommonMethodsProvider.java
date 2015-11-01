package com.nextbook.services;

import com.nextbook.domain.entities.*;
import com.nextbook.domain.preview.AuthorPreview;
import com.nextbook.domain.response.ResponseForAutoComplete;

import java.util.List;
import java.util.Locale;

/**
 * Created by oleh on 24.10.2015.
 */
public interface ICommonMethodsProvider {

    List<AuthorPreview> formAuthorsInLocale(List<BookAuthorEntity> authors, String language);

    List<ResponseForAutoComplete> formAuthorsForAutoComplete(List<AuthorEntity> authors, String language);

    boolean checkBookToUser(UserEntity user, BookEntity book);

    boolean userBuyBook(UserEntity user, BookEntity book);

    String getCategoryLocated(CategoryEntity category, Locale locate);

    String bookNameInLocale(BookEntity book, Locale locale);
}
