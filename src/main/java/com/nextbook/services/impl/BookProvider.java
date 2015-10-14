package com.nextbook.services.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.pojo.*;
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

    @Override
    public BookKeyword getBookToKeyword(int bookId, int keywordId) {
        return bookDao.getBookToKeyword(bookId, keywordId);
    }

    @Override
    public BookKeyword updateBookToKeyword(BookKeyword bookKeyword) {
        if(bookKeyword == null)
            return null;
        return bookDao.updateBookToKeyword(bookKeyword);
    }

    @Override
    public boolean deleteBookToKeyword(int bookId, int keywordId) {
        return bookDao.deleteBookToKeyword(bookId, keywordId);
    }

    @Override
    public BookAuthor getBookToAuthor(int bookId, int authorId) {
        return bookDao.getBookToAuthor(bookId, authorId);
    }

    @Override
    public BookAuthor updateBookToAuthor(BookAuthor bookAuthor) {
        if(bookAuthor == null)
            return null;
        return bookDao.updateBookToAuthor(bookAuthor);
    }

    @Override
    public boolean deleteBookToAuthor(int bookId, int authorId) {
        return bookDao.deleteBookToAuthor(bookId, authorId);
    }

    @Override
    public int getBooksQuantity() { return bookDao.getBooksQuantity(); }

    @Override
    public Book userStarBook(User user, Book book, float mark){
        if(user == null || book == null)
            return null;

        UserStarsBook userStarsBook = new UserStarsBook(user, book, mark);
        userStarsBook = bookDao.userStarsBook(userStarsBook);
        if(userStarsBook == null)
            return null;

        int numberOfVoted = book.getVoted() + 1;
        float newRating = (book.getRating() * book.getVoted() + mark) / numberOfVoted;

        book.setRating(newRating);
        book.setVoted(numberOfVoted);
        book = updateBook(book);

        return book;
    }
}
