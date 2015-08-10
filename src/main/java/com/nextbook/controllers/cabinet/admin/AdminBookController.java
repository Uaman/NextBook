package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.info.BookMainInfo;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/edit-book", method = RequestMethod.GET)
    public String addBook(@RequestParam("bookId")int bookId,
                          Model model){
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/admin/books/all";
        model.addAttribute("subCategories", subCategoryProvider.getAll());
        model.addAttribute("book", book);
        model.addAttribute("bookId", book.getId());
        return "admin/books/add-book";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/edit-book", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody int editBook(@RequestBody BookRegisterForm bookRegisterForm){
        Book book = bookProvider.getBookById(bookRegisterForm.getBookId());
        if(book == null)
            return -1;
        copyBookFromBookForm(book, bookRegisterForm);
        book = bookProvider.updateBook(book);
        if(book == null)
            return -1;
        String storageLink = bookUploadingProvider.uploadBookToStorage(book.getId());
        book.setLinkToStorage(storageLink);
        bookProvider.updateBook(book);
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

    @RequestMapping(value = "/authors-auto-complete/{keyword}", method = RequestMethod.POST)
    public @ResponseBody List<String> authorsAutoComplete(@PathVariable("keyword") String keyword,
                                                          Locale locale){
        if(keyword.equals(""))
            return new ArrayList<String>();
        List<Author> authors = authorProvider.getAuthorsByCriterion(new AuthorCriterion(keyword));
        String language = locale.getLanguage();
        List<String> response = formAuthorsForAutoComplete(authors, language);
        return response;
    }

    private List<String> formAuthorsForAutoComplete(List<Author> authors, String language){
        List<String> result = new ArrayList<String>();
        if(authors != null) {
            for (Author author : authors) {
                if (language.equals("uk")) {
                    result.add(author.getFirstNameUa() + ' ' + author.getLastNameUa());
                } else if (language.equals("ru")) {
                    result.add(author.getFirstNameRu() + ' ' + author.getLastNameRu());
                } else {
                    result.add(author.getFirstNameEn() + ' ' + author.getLastNameEn());
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/keywords-auto-complete/{keyword}", method = RequestMethod.POST)
    public @ResponseBody List<String> keywordsAutoComplete(@PathVariable("keyword") String keyword,
                                                           Locale locale){
        if(keyword.equals(""))
            return new ArrayList<String>();
        List<Keyword> keywords = keywordProvider.getListByKeyword(keyword);
        List<String> response = new ArrayList<String>();
        if(keywords != null) {
            for (Keyword k : keywords) {
                response.add(k.getKeyword());
            }
        }
        return response;
    }

    @RequestMapping(value = "/send-first-page", method = RequestMethod.POST)
    public @ResponseBody boolean firstPage(@RequestParam("first_page")MultipartFile file,
                                           @RequestParam("bookId") int bookId){
        return saveCover(bookId, file, Cover.FIRST_PAGE);
    }

    @RequestMapping(value = "/send-last-page", method = RequestMethod.POST)
    public @ResponseBody boolean lastPage(@RequestParam("last_page")MultipartFile file,
                                          @RequestParam("bookId") int bookId){
        return saveCover(bookId, file, Cover.LAST_PAGE);
    }

    @RequestMapping(value = "/send-book", method = RequestMethod.POST)
    public @ResponseBody boolean uploadBook(@RequestParam("book")MultipartFile file,
                                            @RequestParam("bookId") int bookId){
        return saveBook(bookId, file);
    }

    private boolean saveCover(int bookId, MultipartFile file, Cover cover){
        if(file == null)
            return false;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        boolean success = bookUploadingProvider.uploadCoverToLocalStorage(bookId, file, cover);
        return success;
    }

    private boolean saveBook(int bookId, MultipartFile file){
        if(file == null)
            return false;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        boolean success = bookUploadingProvider.uploadFileToLocalStorage(bookId, file);
        return success;
    }

    @RequestMapping(value = "/check-isbn/{isbn}", method = RequestMethod.POST)
    public @ResponseBody boolean isbnExist(@PathVariable("isbn") String isbn){
        if(isbn == null)
            return false;
        return bookProvider.isbnExist(isbn);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewBook(@PathVariable("id") int bookId,
                           Model model){

        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/admin/books/all";

        String url = book.getLinkToStorage();
        if(url == null || url.equals(""))
            return "404";
        model.addAttribute("urlToFile", url);
        model.addAttribute("pass", Constants.USER_PASSWORD);
        return "book/view";
    }
}
