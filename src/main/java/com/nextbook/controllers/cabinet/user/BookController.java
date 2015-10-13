package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.ResponseForAutoComplete;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.AuthorPreview;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.*;
import com.nextbook.services.impl.FavoritesProvider;
import com.nextbook.utils.SessionUtils;
import com.nextbook.utils.StatisticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

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
    private IBookStorageProvider bookStorageProvider;
    @Inject
    private StatisticUtil statisticUtil;
    @Inject
    private IFavoritesProvider favoritesProvider;

    @RequestMapping(value = "/new-book", method = RequestMethod.GET)
    public String newBook(){
        User user = sessionUtils.getCurrentUser();
        if(user == null){
            return "redirect:/";
        }
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null){
            // redirect to page where user can create publication
            return "redirect:/publisher/new";
        }
        Book book = defaultBook(user, publisher);
        book = bookProvider.updateBook(book);
        if(book == null)
            return "redirect:/cabinet/profile";
        return "redirect:/book/edit-book?bookId="+book.getId();
    }

    @RequestMapping(value = "/edit-book", method = RequestMethod.GET)
    public String addBook(@RequestParam("bookId")int bookId,
                          Model model, Locale locale){
        User user = sessionUtils.getCurrentUser();
        if(user == null){
            return "redirect:/";
        }
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null){
            // redirect to page where user can create publication
            return "redirect:/publisher/new";
        }
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/cabinet/profile";
        if(book.getPublisher().getId() != publisher.getId())
            return "redirect:/publisher/view?publisherId="+publisher.getId();
        model.addAttribute("subCategories", subCategoryProvider.getAll());
        model.addAttribute("book", book);
        model.addAttribute("authors", formAuthorsInLocale(book.getBookToAuthor(), locale.getLanguage()));
        model.addAttribute("numberOfPhotos", bookStorageProvider.getNumberOfPhotosInGallery(book.getId()));
        return "book/add-book";
    }

    private List<AuthorPreview> formAuthorsInLocale(List<BookAuthor> authors, String language){
        List<AuthorPreview> result = new ArrayList<AuthorPreview>();
        if(authors != null) {
            for (BookAuthor bookAuthor : authors) {
                String name;
                Author author = bookAuthor.getAuthor();
                if (language.equals("uk")) {
                    name = author.getFirstNameUa() + ' ' + author.getLastNameUa();
                } else if (language.equals("ru")) {
                    name = author.getFirstNameRu() + ' ' + author.getLastNameRu();
                } else {
                    name = author.getFirstNameEn() + ' ' + author.getLastNameEn();
                }
                result.add(new AuthorPreview(author.getId(), name));
            }
        }
        return result;
    }

    private Book defaultBook(User user, Publisher publisher){
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
        String storageLink = bookStorageProvider.uploadBookToStorage(book.getId());
        if(storageLink == null)
            return -1;
        book.setLinkToStorage(storageLink);
        copyBookFromBookForm(book, bookRegisterForm);
        bookProvider.updateBook(book);
        return 1;
    }

    @RequestMapping(value = "/getCover/{bookId}/{coverPage}", method = RequestMethod.GET)
    public void getCover(HttpServletResponse response,
                                 @PathVariable("bookId") int bookId,
                                 @PathVariable("coverPage") int coverPage){
        Cover cover;
        if(coverPage == 2) cover = Cover.LAST_PAGE;
        else cover = Cover.FIRST_PAGE;
        try {
            bookStorageProvider.getCover(response.getOutputStream(), bookId, cover);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getGalleryPhoto/{bookId}/{photoNumber}", method = RequestMethod.GET)
    public void getGalleryPhoto(HttpServletResponse response,
                                 @PathVariable("bookId") int bookId,
                                 @PathVariable("photoNumber") int photoNumber){
        try {
            bookStorageProvider.getGalleryPhoto(response.getOutputStream(), bookId, photoNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                bookProvider.updateBookToKeyword(bookKeyword);
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
                bookAuthor = bookProvider.updateBookToAuthor(bookAuthor);
                if(bookAuthor != null)
                    book.addAuthor(bookAuthor.getAuthor());
            }
        }
    }

    @RequestMapping(value = "/authors-auto-complete/{keyword}", method = RequestMethod.POST)
    public @ResponseBody List<ResponseForAutoComplete> authorsAutoComplete(@PathVariable("keyword") String keyword,
                                                          Locale locale){
        if(keyword.equals(""))
            return new ArrayList<ResponseForAutoComplete>();
        List<Author> authors = authorProvider.getAuthorsByCriterion(new AuthorCriterion(keyword));
        String language = locale.getLanguage();
        List<ResponseForAutoComplete> response = formAuthorsForAutoComplete(authors, language);
        return response;
    }

    private List<ResponseForAutoComplete> formAuthorsForAutoComplete(List<Author> authors, String language){
        List<ResponseForAutoComplete> result = new ArrayList<ResponseForAutoComplete>();
        if(authors != null) {
            for (Author author : authors) {
                String value;
                if (language.equals("uk")) {
                    value = author.getFirstNameUa() + ' ' + author.getLastNameUa();
                } else if (language.equals("ru")) {
                    value = author.getFirstNameRu() + ' ' + author.getLastNameRu();
                } else {
                    value = author.getFirstNameEn() + ' ' + author.getLastNameEn();
                }
                result.add(new ResponseForAutoComplete(author.getId(), value));
            }
        }
        return result;
    }

    @RequestMapping(value = "/keywords-auto-complete/{keyword}", method = RequestMethod.POST)
    public @ResponseBody List<ResponseForAutoComplete> keywordsAutoComplete(@PathVariable("keyword") String keyword,
                                                          Locale locale){
        if(keyword.equals(""))
            return new ArrayList<ResponseForAutoComplete>();
        List<Keyword> keywords = keywordProvider.getListByKeyword(keyword);
        List<ResponseForAutoComplete> response = new ArrayList<ResponseForAutoComplete>();
        if(keywords != null) {
            for (Keyword k : keywords) {
                response.add(new ResponseForAutoComplete(k.getId(), k.getKeyword()));
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

    @RequestMapping(value = "/send-gallery-photo", method = RequestMethod.POST)
    public @ResponseBody int upload(MultipartHttpServletRequest request,
                                        @RequestParam(value = "bookId", required = true) Integer bookId) {
        if (bookId == null || bookId == 0)
            return -1;
        User user = sessionUtils.getCurrentUser();
        Book book = bookProvider.getBookById(bookId);
        if(!checkBookToUser(user, book))
            return -1;
        Iterator<String> itr =  request.getFileNames();
        MultipartFile multipartFile = request.getFile(itr.next());
        bookStorageProvider.uploadGalleryPhoto(book.getId(), multipartFile);
        return bookStorageProvider.getNumberOfPhotosInGallery(bookId);
    }

    @RequestMapping(value = "/delete-gallery-image/{bookId}/{photoId}", method = RequestMethod.POST)
    public @ResponseBody int deleteGalleryImage(@PathVariable("bookId") int bookId,
                                  @PathVariable("photoId") int photoId){
        User user = sessionUtils.getCurrentUser();
        Book book = bookProvider.getBookById(bookId);
        if(!checkBookToUser(user, book))
            return -1;
        boolean success = bookStorageProvider.deleteGalleryPhoto(bookId, photoId);
        if(!success)
            return -1;
        return bookStorageProvider.getNumberOfPhotosInGallery(bookId);
    }

    private boolean saveCover(int bookId, MultipartFile file, Cover cover){
        if(file == null)
            return false;
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;
        boolean success = bookStorageProvider.uploadCoversToLocalStorage(bookId, file, cover);
        return success;
    }

    private boolean saveBook(int bookId, MultipartFile file){
        if(file == null)
            return false;
        Book book = bookProvider.getBookById(bookId);

        if(book == null)
            return false;
        boolean success = bookStorageProvider.uploadBookToLocalStorage(bookId, file);
        return success;
    }

    @RequestMapping(value = "/check-isbn/{isbn}", method = RequestMethod.POST)
    public @ResponseBody boolean isbnExist(@PathVariable("isbn") String isbn){
        if(isbn == null)
            return false;
        return bookProvider.isbnExist(isbn);
    }

    @RequestMapping(value = "/delete-keyword/{bookId}/{keywordId}", method = RequestMethod.POST)
    public @ResponseBody boolean deleteKeyword(@PathVariable("bookId") int bookId,
                                               @PathVariable("keywordId") int keywordId){
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return false;

        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null)
            return false;

        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;

        if(book.getPublisher().getId() != publisher.getId())
            return false;
        boolean success = bookProvider.deleteBookToKeyword(bookId, keywordId);
        return success;
    }

    @RequestMapping(value = "/delete-author/{bookId}/{authorId}", method = RequestMethod.POST)
    public @ResponseBody boolean deleteAuthor(@PathVariable("bookId") int bookId,
                                               @PathVariable("authorId") int authorId){
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return false;

        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null)
            return false;

        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return false;

        if(book.getPublisher().getId() != publisher.getId())
            return false;
        boolean success = bookProvider.deleteBookToAuthor(bookId, authorId);
        return success;
    }

    private boolean checkBookToUser(User user, Book book){
        if(user == null || book == null)
            return false;
        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher == null)
            return false;

        return book.getPublisher().getId() == publisher.getId();
    }

    @RequestMapping(value = "/add-favorite/{id}")
    public @ResponseBody int addToFavorite(@PathVariable int id){
        Book book = bookProvider.getBookById(id);
        if (book==null)
            return -1;
        if (sessionUtils.getCurrentUser()==null)
            return 0;
        Favorites favorites = new Favorites();
        favorites.setBook(book);
        favorites.setUser(sessionUtils.getCurrentUser());
        favoritesProvider.addToUserFavorites(favorites);
        return 1;
    }

    @RequestMapping(value = "/delete-favorite/{id}")
    public @ResponseBody int deleteFavorite(@PathVariable int id){
        Book book = bookProvider.getBookById(id);
        if (book==null)
            return -1;
        User user = sessionUtils.getCurrentUser();
        if (user==null)
            return 0;
        return favoritesProvider.deleteFromUserFavorites(user.getId(), book.getId())?1:-1;
    }

    @RequestMapping(value = "/is-favorite/{id}")
    public @ResponseBody int isFavorite(@PathVariable int id){
        Book book = bookProvider.getBookById(id);
        if (book==null)
            return -1;
        User user = sessionUtils.getCurrentUser();
        if (user==null)
            return 0;
        return favoritesProvider.isFavorite(user.getId(), book.getId())?1:0;
    }
}
