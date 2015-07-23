package com.nextbook.dao;


import com.nextbook.domain.pojo.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:45 PM
 */
public interface IBookDao {

    boolean addBook(Book book);

    Book getBookById(int bookId);

    List<Book> getAllBooks();

    boolean deleteBook(int bookId);

    Book updateBook(Book book);

}
