package com.nextbook.dao;


import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.BookKeyword;
import com.nextbook.domain.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:45 PM
 */
public interface IBookDao {

    Book getBookById(int bookId);

    List<Book> getAllBooks();

    boolean deleteBook(int bookId);

    Book updateBook(Book book);

    boolean isbnExist(String isbn);

    List<Book> getBooksByCriterion(BookCriterion criterion);

    List<Book> getAllPublisherBooks(int publisherId);

    BookKeyword getBookToKeyword(int bookId, int keywordId);

    BookKeyword updateBookToKeyword(BookKeyword bookKeyword);
}
