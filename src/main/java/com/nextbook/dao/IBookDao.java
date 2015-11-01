package com.nextbook.dao;


import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.entities.BookAuthorEntity;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.BookKeywordEntity;
import com.nextbook.domain.entities.UserStarsBookEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:45 PM
 */
public interface IBookDao {

    BookEntity getBookById(int bookId);

    List<BookEntity> getAllBooks();

    int getBooksQuantity();

    int getCountByCriterion(BookCriterion criterion);

    boolean deleteBook(int bookId);

    BookEntity updateBook(BookEntity book);

    boolean isbnExist(String isbn, BookEntity entity);

    List<BookEntity> getBooksByCriterion(BookCriterion criterion);

    List<BookEntity> getAllPublisherBooks(int publisherId);

    BookKeywordEntity getBookToKeyword(int bookId, int keywordId);

    BookKeywordEntity updateBookToKeyword(BookKeywordEntity bookKeyword);

    boolean deleteBookToKeyword(int bookId, int keywordId);

    BookAuthorEntity getBookToAuthor(int bookId, int authorId);

    BookAuthorEntity updateBookToAuthor(BookAuthorEntity bookAuthor);

    boolean deleteBookToAuthor(int bookId, int authorId);

    UserStarsBookEntity userStarsBookUpdate(UserStarsBookEntity userStarsBook);
}
