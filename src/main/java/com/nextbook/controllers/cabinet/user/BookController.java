package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 9:18 PM
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private IBookProvider bookProvider;
    @Autowired
    private ISubCategoryProvider subCategoryProvider;
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private IAuthorProvider authorProvider;
    @Autowired
    private IPublisherProvider publisherProvider;
    @Autowired
    private IKeywordProvider keywordProvider;
    @Autowired
    private IBookUploadingProvider bookUploadingProvider;

    @RequestMapping(value = "/new-book", method = RequestMethod.GET)
    public String newBook(){
        User user = sessionUtils.getCurrentUser();
        if(user == null){
            return "redirect:/";
        }
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null){
            // redirect to page where user can create publication
            return "redirect:/publisher/add?first=true";
        }
        Book book = defaultBook(user, publisher);
        book = bookProvider.updateBook(book);
        if(book == null)
            return "redirect:/cabinet/profile";
        return "redirect:/book/edit-book?bookId="+book.getId();
    }

    @RequestMapping(value = "/edit-book", method = RequestMethod.GET)
    public String addBook(@RequestParam("bookId")int bookId,
                          Model model){
        User user = sessionUtils.getCurrentUser();
        if(user == null){
            return "redirect:/";
        }
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null){
            // redirect to page where user can create publication
            return "redirect:/publisher/add?first=true";
        }
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/cabinet/profile";
        if(book.getPublisher().getId() != publisher.getId())
            return "redirect:/publisher/view?publisherId="+publisher.getId();
        model.addAttribute("subCategories", subCategoryProvider.getAll());
        model.addAttribute("book", book);
        model.addAttribute("bookId", book.getId());
        return "book/add-book";
    }

    private Book defaultBook(User user, Publisher publisher){
        Book book = new Book();
        book.setUaName("def-name");
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1);
        book.setSubCategory(subCategory);
        book.setYearOfPublication(0);
        book.setPublisher(publisher);
        book.setLanguage("def-lang");
        book.setTypeOfBook(BookTypeEnum.ELECTRONIC);
        book.setDescriptionUa("def-desc");
        book.setIsbn("def-isbn");

        return book;
    }

    @RequestMapping(value = "/edit-book", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody int saveBook(@RequestBody BookRegisterForm bookRegisterForm,
                                      Principal principal){
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
        author.setLastNameUa("lastName");

        author = authorProvider.updateAuthor(author);
*/
        //book.addAuthor(author);
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


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String onLoad(@RequestParam("bookId") int bookId,
                         Model model){
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/";
        String url = book.getLinkToStorage();
        if(url == null || url.equals(""))
            return "redirect:/";
        model.addAttribute("urlToFile", url);
        model.addAttribute("pass", Constants.USER_PASSWORD);
        return "book/view";
    }
}