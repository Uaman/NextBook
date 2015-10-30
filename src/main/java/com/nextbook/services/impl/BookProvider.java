package com.nextbook.services.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.entities.*;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.QueryType;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.exceptions.IsbnAlreadyExistsException;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 8:13 PM
 */
@Service
public class BookProvider implements IBookProvider{

    @Inject
    private IAuthorProvider authorProvider;
    @Inject
    private ISubCategoryProvider subCategoryProvider;
    @Inject
    private IKeywordProvider keywordProvider;

    @Inject
    private IBookDao bookDao;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private IFavoritesProvider favoritesProvider;

    @Transactional
    public BookEntity getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    @Transactional
    public List<BookEntity> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Transactional
    public boolean deleteBook(BookEntity book) {
        if(book == null)
            return false;
        return deleteBook(book.getId());
    }

    @Transactional
    public boolean deleteBook(int bookId) {
        return bookDao.deleteBook(bookId);
    }

    @Transactional
    public BookEntity updateBook(BookEntity book) throws IsbnAlreadyExistsException{
        if(book == null)
            return null;
        if(isbnExist(book.getIsbn(), book))
            throw new IsbnAlreadyExistsException();
        return bookDao.updateBook(book);
    }

    @Transactional
    public boolean isbnExist(String isbn, BookEntity book) {
        if(isbn == null || isbn.equals("") || book == null)
            return false;
        return bookDao.isbnExist(isbn, book);
    }

    @Transactional
    public List<BookEntity> getBooksByCriterion(BookCriterion criterion) {
        if(criterion == null)
            return null;
        criterion.setQueryType(QueryType.GET);
        return bookDao.getBooksByCriterion(criterion);
    }

    @Transactional
    public List<BookEntity> getAllPublisherBooks(int publisherId) {
        return bookDao.getAllPublisherBooks(publisherId);
    }

    @Transactional
    public BookKeywordEntity getBookToKeyword(int bookId, int keywordId) {
        return bookDao.getBookToKeyword(bookId, keywordId);
    }

    @Transactional
    public BookKeywordEntity updateBookToKeyword(BookKeywordEntity bookKeyword) {
        if(bookKeyword == null)
            return null;
        return bookDao.updateBookToKeyword(bookKeyword);
    }

    @Transactional
    public boolean deleteBookToKeyword(int bookId, int keywordId) {
        return bookDao.deleteBookToKeyword(bookId, keywordId);
    }

    @Transactional
    public BookAuthorEntity getBookToAuthor(int bookId, int authorId) {
        return bookDao.getBookToAuthor(bookId, authorId);
    }

    @Transactional
    public BookAuthorEntity updateBookToAuthor(BookAuthorEntity bookAuthor) {
        if(bookAuthor == null)
            return null;
        return bookDao.updateBookToAuthor(bookAuthor);
    }

    @Transactional
    public boolean deleteBookToAuthor(int bookId, int authorId) {
        return bookDao.deleteBookToAuthor(bookId, authorId);
    }

    @Transactional
    public int getCountByCriterion(BookCriterion criterion) {
        if(criterion == null)
            return 0;
        criterion.setQueryType(QueryType.COUNT);
        return bookDao.getCountByCriterion(criterion);
    }

    @Transactional
    public int getBooksQuantity() { return bookDao.getBooksQuantity(); }

    @Transactional
    public BookEntity userStarBook(UserEntity user, BookEntity book, float mark) {
        if (user == null || book == null)
            return null;

        UserStarsBookEntity userStarsBook = new UserStarsBookEntity(user, book, mark);
        userStarsBook = bookDao.userStarsBookUpdate(userStarsBook);
        if (userStarsBook == null)
            return null;

        int numberOfVoted = book.getVoted() + 1;
        float newRating = (book.getRating() * book.getVoted() + mark) / numberOfVoted;

        book.setRating(newRating);
        book.setVoted(numberOfVoted);
        try {
            book = updateBook(book);
        } catch (IsbnAlreadyExistsException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Transactional
    public List<BookPreview> booksToBookPreviews(List<BookEntity> books, Locale locale) {
        ArrayList<BookPreview> res = new ArrayList<BookPreview>();
        UserEntity user = sessionUtils.getCurrentUser();
        if (books!=null) {
            for (BookEntity b : books) {
                BookPreview book = new BookPreview(b, locale);
                if (user != null) {
                    book.setFavorite(favoritesProvider.isFavorite(user.getId(), book.getId()));
                }
                res.add(book);
            }
        }
        return res;
    }

    @Transactional
    public BookEntity adminActivateBook(BookEntity book) {
        if(book == null)
            return null;
        book.setStatus(Status.ACTIVE);
        try {
            book = updateBook(book);
        } catch (IsbnAlreadyExistsException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Transactional
    public BookEntity adminDeactivateBook(BookEntity book) {
        if(book == null)
            return null;
        book.setStatus(Status.NOT_ACTIVE);
        try {
            book = updateBook(book);
        } catch (IsbnAlreadyExistsException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Transactional
    public BookEntity publisherSendBookForReview(BookEntity book){
        if(book == null)
            return null;
        book.setStatus(Status.READY_FOR_REVIEW);
        try {
            book = updateBook(book);
        } catch (IsbnAlreadyExistsException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Transactional
    public BookEntity defaultBook(PublisherEntity publisher){
        BookEntity book = new BookEntity();
        book.setUaName("");
        SubCategoryEntity subCategory = new SubCategoryEntity();

        subCategory.setId(1);
        book.setSubCategoryEntity(subCategory);
        book.setYearOfPublication(0);
        book.setPublisherEntity(publisher);
        book.setLanguage("");
        book.setTypeOfBook(BookTypeEnum.ELECTRONIC);
        book.setDescriptionUa("");
        book.setIsbn("");
        book.setStatus(Status.NEW);
        return book;
    }

    @Transactional
    public void copyBookFromBookForm(BookEntity book, BookRegisterForm bookRegisterForm){
        book.setIsbn(bookRegisterForm.getIsbn());
        book.setUaName(bookRegisterForm.getUaName());
        book.setEnName(bookRegisterForm.getEnName());
        book.setRuName(bookRegisterForm.getRuName());
        book.setEighteenPlus(bookRegisterForm.isEighteenPlus());
        book.setYearOfPublication(bookRegisterForm.getYearOfPublication());
        book.setLanguage(bookRegisterForm.getLanguage());
        book.setTypeOfBook(bookRegisterForm.getTypeOfBook());
        book.setNumberOfPages(bookRegisterForm.getNumberOfPages());
        book.setDescriptionUa(bookRegisterForm.getDescriptionUa());
        book.setDescriptionEn(bookRegisterForm.getDescriptionEn());
        book.setDescriptionRu(bookRegisterForm.getDescriptionRu());
        book.setSubCategoryEntity(subCategoryProvider.getById(bookRegisterForm.getSubCategoryId()));

        List<String> keywords = bookRegisterForm.getKeywords();
        for(String s : keywords){
            KeywordEntity keyword = keywordProvider.getByName(s);
            if(keyword == null) {
                keyword = new KeywordEntity();
                keyword.setKeyword(s);
                keyword = keywordProvider.update(keyword);
            }
            if(!book.getBookToKeywords().contains(keyword)) {
                BookKeywordEntity bookKeyword = new BookKeywordEntity();
                bookKeyword.setBook(book);
                bookKeyword.setKeyword(keyword);
                book.getBookToKeywords().add(bookKeyword);
                updateBookToKeyword(bookKeyword);
            }
        }

        for(Integer id : bookRegisterForm.getAuthors()) {
            if(id == null)
                continue;
            AuthorEntity author = authorProvider.getById(id);
            if(author != null) {
                BookAuthorEntity bookAuthor = new BookAuthorEntity();
                bookAuthor.setAuthor(author);
                bookAuthor.setBook(book);
                bookAuthor = updateBookToAuthor(bookAuthor);
                if(bookAuthor != null)
                    book.getBookToAuthor().add(bookAuthor);
            }
        }
    }
}
