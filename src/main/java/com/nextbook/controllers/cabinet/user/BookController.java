package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.forms.BookRegisterForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/add-book", method = RequestMethod.GET)
    public String addBook(Model model){
        User user = sessionUtils.getCurrentUser();
        if(user == null){
            return "redirect:/";
        }
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null){
            // redirect to page where user can create publication
            return "redirect:/";
        }
        Book book = defaultBook(user, publisher);
        book = bookProvider.updateBook(book);
        if(book == null)
            return "redirect:/";
        model.addAttribute("subCategories", subCategoryProvider.getAll());
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

    @RequestMapping(value = "/add-book", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody int saveBook(@RequestBody BookRegisterForm bookRegisterForm,
                                      Principal principal){
        System.out.println("HERE book");
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

        Author author = new Author();
        author.setFirstNameUa(bookRegisterForm.getAuthor());
        author.setLastNameUa("last name");

        author = authorProvider.updateAuthor(author);

        book.setAuthor(author);
    }

    @RequestMapping(value = "/send-first-page", method = RequestMethod.POST)
    public @ResponseBody boolean firstPage(@RequestParam("first_page")MultipartFile file,
                                           @RequestParam("bookId") int bookId){
        System.out.println("Send first book");
        if(file == null)
            return false;
        boolean success = true;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        return success;
    }

    @RequestMapping(value = "/send-last-page", method = RequestMethod.POST)
    public @ResponseBody boolean lastPage(@RequestParam("last_page")MultipartFile file,
                                          @RequestParam("bookId") int bookId){
        System.out.println("Send last book");
        if(file == null)
            return false;
        boolean success = true;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        return success;
    }

    @RequestMapping(value = "/send-book", method = RequestMethod.POST)
    public @ResponseBody boolean uploadBook(@RequestParam("book")MultipartFile file,
                                            @RequestParam("bookId") int bookId){
        System.out.println("Send WTF book");
        if(file == null)
            return false;
        boolean success = true;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        return success;
    }

    @RequestMapping(value = "/check-isbn/{isbn}", method = RequestMethod.POST)
    public @ResponseBody boolean isbnExist(@PathVariable("isbn") String isbn){
        if(isbn == null)
            return false;
        return bookProvider.isbnExist(isbn);
    }

}