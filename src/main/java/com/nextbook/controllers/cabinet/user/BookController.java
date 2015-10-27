package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.exceptions.IsbnAlreadyExistsException;
import com.nextbook.domain.response.ResponseForAutoComplete;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.Cover;
import com.nextbook.domain.criterion.AuthorCriterion;
import com.nextbook.domain.forms.book.BookRegisterForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.domain.response.ResponseOnAjaxRegistration;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import com.nextbook.utils.StatisticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
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
    @Inject
    private ICommonMethodsProvider methodsProvider;
    @Inject
    private Validator validator;
    @Inject
    private MessageSource messageSource;

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
        Book book = bookProvider.defaultBook(publisher);

        try {
            book = bookProvider.updateBook(book);
        } catch (IsbnAlreadyExistsException e) {
            e.printStackTrace();
        }
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
        model.addAttribute("publisherId", publisher.getId());
        model.addAttribute("subCategories", subCategoryProvider.getAll());
        model.addAttribute("book", book);
        model.addAttribute("authors", methodsProvider.formAuthorsInLocale(book.getBookToAuthor(), locale.getLanguage()));
        model.addAttribute("numberOfPhotos", bookStorageProvider.getNumberOfPhotosInGallery(book.getId()));
        return "book/add-book";
    }

    @RequestMapping(value = "/edit-book", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody ResponseOnAjaxRegistration saveBook(@RequestBody BookRegisterForm bookRegisterForm,
                                                             Locale locale){
        ResponseOnAjaxRegistration<BookRegisterForm> response =
                new ResponseOnAjaxRegistration<BookRegisterForm>(validator.validate(bookRegisterForm), messageSource, locale);
        if(!response.hasErrors()) {
            User user = sessionUtils.getCurrentUser();
            Book book = bookProvider.getBookById(bookRegisterForm.getBookId());
            if (methodsProvider.checkBookToUser(user, book)) {
                String storageLink = bookStorageProvider.uploadBookToStorage(book.getId());
                if (storageLink != null) {
                    book.setLinkToStorage(storageLink);
                    bookProvider.copyBookFromBookForm(book, bookRegisterForm);
                    try {
                        book = bookProvider.updateBook(book);
                        if(book == null) {
                            response.setCode(ResponseOnAjaxRegistration.PROBLEMS_WITH_SERVICE);
                        } else {
                            response.setCode(ResponseOnAjaxRegistration.OK);
                        }
                    } catch (IsbnAlreadyExistsException e) {
                        response.addError(messageSource.getMessage("book.registration.isbn.exist", null, locale));
                    }
                } else {
                    response.setCode(ResponseOnAjaxRegistration.PROBLEMS_WITH_SERVICE);
                }
            } else {
                response.addError(messageSource.getMessage("user.book.access", null, locale));
            }
        }
        return response;
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

    @RequestMapping(value = "/authors-auto-complete/{keyword}", method = RequestMethod.POST)
    public @ResponseBody List<ResponseForAutoComplete> authorsAutoComplete(@PathVariable("keyword") String keyword,
                                                          Locale locale){
        if(keyword.equals(""))
            return new ArrayList<ResponseForAutoComplete>();
        List<Author> authors = authorProvider.getAuthorsByCriterion(new AuthorCriterion(keyword));
        String language = locale.getLanguage();
        List<ResponseForAutoComplete> response = methodsProvider.formAuthorsForAutoComplete(authors, language);
        return response;
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
        if(!methodsProvider.checkBookToUser(user, book))
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
        if(!methodsProvider.checkBookToUser(user, book))
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
/*
    @RequestMapping(value = "/check-isbn/{isbn}", method = RequestMethod.POST)
    public @ResponseBody boolean isbnExist(@PathVariable("isbn") String isbn){
        if(isbn == null)
            return false;
        return bookProvider.isbnExist(isbn);
    }
*/
    @RequestMapping(value = "/delete-keyword/{bookId}/{keywordId}", method = RequestMethod.POST)
    public @ResponseBody boolean deleteKeyword(@PathVariable("bookId") int bookId,
                                               @PathVariable("keywordId") int keywordId){
        User user = sessionUtils.getCurrentUser();
        Book book = bookProvider.getBookById(bookId);
        if(!methodsProvider.checkBookToUser(user, book))
            return false;
        boolean success = bookProvider.deleteBookToKeyword(bookId, keywordId);
        return success;
    }

    @RequestMapping(value = "/delete-author/{bookId}/{authorId}", method = RequestMethod.POST)
    public @ResponseBody boolean deleteAuthor(@PathVariable("bookId") int bookId,
                                               @PathVariable("authorId") int authorId){
        User user = sessionUtils.getCurrentUser();
        Book book = bookProvider.getBookById(bookId);
        if(!methodsProvider.checkBookToUser(user, book))
            return false;

        boolean success = bookProvider.deleteBookToAuthor(bookId, authorId);
        return success;
    }

    @RequestMapping(value = "/add-favorite/{id}")
    public @ResponseBody int addToFavorite(@PathVariable int id){
        Book book = bookProvider.getBookById(id);
        if (book==null)
            return -1;
        User user = sessionUtils.getCurrentUser();
        if (user==null)
            return 0;
        Favorites favorites = new Favorites();
        favorites.setBook(book);
        favorites.setUser(user);
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
