package com.nextbook.services.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.dao.impl.BookDAO;
import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.services.IBookProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 8:13 PM
 */
@Service
public class BookProvider implements IBookProvider{

    @Inject
    private IBookDao bookDao;

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

    @Override
    public boolean isbnExist(String isbn) {
        return bookDao.isbnExist(isbn);
    }

    @Override
    public List<Book> getBooksByCriterion(BookCriterion criterion) {
        return bookDao.getBooksByCriterion(criterion);
    }

    @Override
    public List<Book> getAllPublisherBooks(int publisherId) {
        return bookDao.getAllPublisherBooks(publisherId);
    }
}
