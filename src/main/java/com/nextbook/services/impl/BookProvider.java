package com.nextbook.services.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.QueryType;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.exceptions.IsbnAlreadyExistsException;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.stereotype.Service;

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
    public Book updateBook(Book book) throws IsbnAlreadyExistsException{
        if(book == null)
            return null;
        if(isbnExist(book.getIsbn(), book))
            throw new IsbnAlreadyExistsException();
        return bookDao.updateBook(book);
    }

    @Override
    public boolean isbnExist(String isbn, Book book) {
        if(isbn == null || isbn.equals("") || book == null)
            return false;
        return bookDao.isbnExist(isbn, book);
    }

    @Override
    public List<Book> getBooksByCriterion(BookCriterion criterion) {
        if(criterion == null)
            return null;
        criterion.setQueryType(QueryType.GET);
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
    public int getCountByCriterion(BookCriterion criterion) {
        if(criterion == null)
            return 0;
        criterion.setQueryType(QueryType.COUNT);
        return bookDao.getCountByCriterion(criterion);
    }

    @Override
    public int getBooksQuantity() { return bookDao.getBooksQuantity(); }

    @Override
    public Book userStarBook(User user, Book book, float mark) {
        if (user == null || book == null)
            return null;

        UserStarsBook userStarsBook = new UserStarsBook(user, book, mark);
        userStarsBook = bookDao.userStarsBook(userStarsBook);
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

    @Override
    public List<BookPreview> booksToBookPreviews(List<Book> books, Locale locale) {
        ArrayList<BookPreview> res = new ArrayList<BookPreview>();
        User user = sessionUtils.getCurrentUser();
        for (Book b:books) {
            BookPreview book = new BookPreview(b, locale);
            if (user!=null) {
                book.setFavorite(favoritesProvider.isFavorite(user.getId(), book.getId()));
            }
            res.add(book);
        }
        return res;
    }

    @Override
    public Book adminActivateBook(Book book) {
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

    @Override
    public Book adminDeactivateBook(Book book) {
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

    public Book publisherSendBookForReview(Book book){
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

    @Override
    public Book defaultBook(Publisher publisher){
        Book book = new Book();
        book.setUaName("");
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1);
        book.setSubCategory(subCategory);
        book.setYearOfPublication(0);
        book.setPublisher(publisher);
        book.setLanguage("");
        book.setTypeOfBook(BookTypeEnum.ELECTRONIC);
        book.setDescriptionUa("");
        book.setIsbn("");
        book.setStatus(Status.NEW);
        return book;
    }

    public void copyBookFromBookForm(Book book, BookRegisterForm bookRegisterForm){
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
        book.setSubCategory(subCategoryProvider.getById(bookRegisterForm.getSubCategoryId()));

        List<String> keywords = bookRegisterForm.getKeywords();
        for(String s : keywords){
            Keyword keyword = keywordProvider.getByName(s);
            if(keyword == null) {
                keyword = new Keyword();
                keyword.setKeyword(s);
                keyword = keywordProvider.update(keyword);
            }
            if(!book.getKeywords().contains(keyword)) {
                BookKeyword bookKeyword = new BookKeyword();
                bookKeyword.setBook(book);
                bookKeyword.setKeyword(keyword);
                book.addKeyword(keyword);
                updateBookToKeyword(bookKeyword);
            }
        }

        for(Integer id : bookRegisterForm.getAuthors()) {
            if(id == null)
                continue;
            Author author = authorProvider.getById(id);
            if(author != null) {
                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setAuthor(author);
                bookAuthor.setBook(book);
                bookAuthor = updateBookToAuthor(bookAuthor);
                if(bookAuthor != null)
                    book.addAuthor(bookAuthor.getAuthor());
            }
        }
    }
}
