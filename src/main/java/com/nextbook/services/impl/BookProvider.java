package com.nextbook.services.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.dao.impl.BookDAO;
import com.nextbook.domain.pojo.Book;
import com.nextbook.services.IBookProvider;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 8:13 PM
 */
public class BookProvider implements IBookProvider{

    private IBookDao bookDao = new BookDAO();

    @Override
    public boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Book getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public boolean deleteBook(Book book) {
        if(book == null)
            return false;
        return deleteBook(book.getId());
    }

    @Override
    public boolean deleteBook(int bookId) {
        return bookDao.deleteBook(bookId);
    }

    @Override
    public Book updateBook(Book book) {
        if(book == null)
            return null;
        return bookDao.updateBook(book);
    }
}
