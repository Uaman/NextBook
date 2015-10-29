package com.nextbook.services;

import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.entities.*;
import com.nextbook.domain.exceptions.IsbnAlreadyExistsException;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.preview.BookPreview;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 8:12 PM
 */
public interface IBookProvider {

    BookEntity getBookById(int bookId);

    List<BookEntity> getAllBooks();

    boolean deleteBook(BookEntity book);

    boolean deleteBook(int bookId);

    BookEntity updateBook(BookEntity book) throws IsbnAlreadyExistsException;

    boolean isbnExist(String isbn, BookEntity book);

    List<BookEntity> getBooksByCriterion(BookCriterion criterion);

    List<BookEntity> getAllPublisherBooks(int publisherId);

    BookKeywordEntity getBookToKeyword(int bookId, int keywordId);

    BookKeywordEntity updateBookToKeyword(BookKeywordEntity bookKeyword);

    boolean deleteBookToKeyword(int bookId, int keywordId);

    BookAuthorEntity getBookToAuthor(int bookId, int authorId);

    BookAuthorEntity updateBookToAuthor(BookAuthorEntity bookAuthor);

    boolean deleteBookToAuthor(int bookId, int authorId);

    int getCountByCriterion(BookCriterion criterion);

    int getBooksQuantity();

    BookEntity userStarBook(UserEntity user, BookEntity book, float mark);

    List<BookPreview> booksToBookPreviews(List<BookEntity> books, Locale locale);

    BookEntity adminActivateBook(BookEntity book);

    BookEntity adminDeactivateBook(BookEntity book);

    BookEntity publisherSendBookForReview(BookEntity book);

    BookEntity defaultBook(PublisherEntity publisher);

    void copyBookFromBookForm(BookEntity book, BookRegisterForm bookRegisterForm);
}
