package com.nextbook.services;

import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;
import com.nextbook.domain.pojo.BookAuthor;
import com.nextbook.domain.pojo.BookKeyword;
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
@Service
public interface IBookProvider {

    Book getBookById(int bookId);

    List<Book> getAllBooks();

    boolean deleteBook(Book book);

    boolean deleteBook(int bookId);

    Book updateBook(Book book);

    boolean isbnExist(String isbn);

    List<Book> getBooksByCriterion(BookCriterion criterion);

    List<Book> getAllPublisherBooks(int publisherId);

    BookKeyword getBookToKeyword(int bookId, int keywordId);

    BookKeyword updateBookToKeyword(BookKeyword bookKeyword);

    boolean deleteBookToKeyword(int bookId, int keywordId);

    BookAuthor getBookToAuthor(int bookId, int authorId);

    BookAuthor updateBookToAuthor(BookAuthor bookAuthor);

    boolean deleteBookToAuthor(int bookId, int authorId);

    int getBooksQuantity();

    Book userStarBook(User user, Book book, float mark);

    List<BookPreview> booksToBookPreviews(List<Book> books, Locale locale);

    Book adminActivateBook(Book book);

    Book adminDeactivateBook(Book book);

    Book publisherSendBookForReview(Book book);
}
