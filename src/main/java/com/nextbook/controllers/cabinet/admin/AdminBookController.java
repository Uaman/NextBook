package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.info.BookMainInfo;
import com.nextbook.domain.pojo.Author;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Keyword;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 27.07.2015.
 */
@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private IBookProvider bookProvider;
    @Inject
    private ISubCategoryProvider subCategoryProvider;
    @Inject
    private IAuthorProvider authorProvider;
    @Inject
    private IPublisherProvider publisherProvider;
    @Inject
    private IKeywordProvider keywordProvider;
    @Inject
    private IBookUploadingProvider bookUploadingProvider;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/all")
    public String allBooksPage(Model model) {
        model.addAttribute("books", bookProvider.getAllBooks());
        model.addAttribute("subCategories", subCategoryProvider.getAll());
        return "admin/books/manage-books";
    }

    @RequestMapping(value = "/edit-book", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody int editBook(@RequestBody BookRegisterForm bookRegisterForm){
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return -1;
        Book book = bookProvider.getBookById(bookRegisterForm.getBookId());
        if(book == null)
            return -1;
        copyBookFromBookForm(book, bookRegisterForm);
        book = bookProvider.updateBook(book);
        if(book == null)
            return -1;
        String localPath = "book-"+book.getId()+"";
        String storageLink = bookUploadingProvider.uploadBookToStorage(localPath);
//        book.setLinkToStorage(storageLink);
//        bookProvider.updateBook(book);
        return 1;
    }

    private void copyBookFromBookForm(Book book, BookRegisterForm bookRegisterForm){
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

        List<Keyword> keywordList = new ArrayList<Keyword>();
        List<String> keywords = bookRegisterForm.getKeywords();
        for(String s : keywords){
            Keyword keyword = new Keyword();
            keyword.setKeyword(s);
            keywordList.add(keyword);
        }
        //book.setKeywords(keywordList);
/*
        Author author = new Author();
        author.setFirstNameUa(bookRegisterForm.getAuthor());
        author.setLastNameUa("last name");

        author = authorProvider.updateAuthor(author);

        book.addAuthor(author);
        */
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping (value = "/filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody List<Book> getBooksByCriterion(@RequestBody BookCriterion criterion) {
        List<Book> books = bookProvider.getBooksByCriterion(criterion);
//        List<BookMainInfo> res = new ArrayList<BookMainInfo>();
//        if (books!=null)
//            for (Book book: books)
//                res.add(new BookMainInfo(book));
        return books;
    }

}
