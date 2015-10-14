package com.nextbook.controllers.book;

import com.nextbook.domain.pojo.*;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Locale;

/**
 * Created by Stacy on 9/25/15.
 */
@Controller
@RequestMapping("/bookInfo")
public class BookViewController {
    @Autowired
    private IBookProvider bookProvider;
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private IBookStorageProvider bookStorageProvider;
    @Autowired
    private IPublisherProvider publisherProvider;
    @Autowired
    private IOrderProvider orderProvider;
    @Autowired
    private ICommentsProvider commentsProvider;
    @Autowired
    private IFavoritesProvider favoritesProvider;

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public String infoBook(@PathVariable("bookId")int bookId, Model model,Locale locale){
        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return "redirect:/";
        User user = sessionUtils.getCurrentUser();
        BookPreview preview = new BookPreview(book,locale);
        model.addAttribute("book",preview);
        model.addAttribute("type",book.getTypeOfBook());
        model.addAttribute("category",getCategoryLocated(book.getSubCategory().getCategory(),locale));
        model.addAttribute("keywords", book.getKeywords());
        model.addAttribute("bookName", bookNameInLocale(book, locale));
        model.addAttribute("shareLink", HOST_NAME+"bookInfo/"+bookId);
        model.addAttribute("numberOfPhotos", bookStorageProvider.getNumberOfPhotosInGallery(book.getId()));
        if(userBuyBook(user, book)){
            model.addAttribute("urlToFile", book.getLinkToStorage());
            model.addAttribute("pass", Constants.USER_PASSWORD);
        } else {
            model.addAttribute("urlToFile", bookStorageProvider.getUrlForPreviewBook(book.getId()));
            model.addAttribute("pass", 1111);
        }
        if(user != null){
            model.addAttribute("userId", user.getId());
            preview.setFavorite(favoritesProvider.isFavorite(user.getId(), book.getId()));
        }

        return "book/bookPage";
    }

    private String getCategoryLocated(Category category,Locale locate){
        String locatedCategory = "";
        if (locate.getLanguage().equals("uk")) {
            locatedCategory = category.getNameUa();
        } else if (locate.getLanguage().equals("ru")) {
            locatedCategory = category.getNameRu();
        } else {
            locatedCategory = category.getNameEn();
        }
        return locatedCategory;
    }

    private String bookNameInLocale(Book book, Locale locale){
        String bookName;
        if (locale.getLanguage().equals("uk")) {
            bookName = book.getUaName();
        } else if (locale.getLanguage().equals("ru")) {
            bookName = book.getRuName();
        } else {
            bookName = book.getEnName();
        }
        return bookName;
    }

    private boolean userBuyBook(User user, Book book){
        if(user == null)
            return false;

        Publisher publisher = publisherProvider.getPublisherByUser(user);
        if(publisher != null && publisher.getId() == book.getPublisher().getId())
            return true;

        //check if admin or moderator
        if(user.getRole().getId() == 4 || user.getRole().getId() == 5)
            return true;

        Order order = orderProvider.getOrderByUserAndBook(user, book);
        if(order != null && order.isPaid())
            return true;
        return false;
    }

    /**
     *
     * @param commentRequest
     * @return -1 - if user doesn't login, 0 - if failed to add comment, 1 if success
     */
    @RequestMapping(value = "/addComment", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody int addComment(@RequestBody CreateCommentRequest commentRequest,
                          Principal principal, HttpServletRequest request){
        User user = null;
        if(principal == null || (user = sessionUtils.getCurrentUser()) == null) {
            HttpSession session = request.getSession();
            session.setAttribute("comment", commentRequest.getText());
            session.setAttribute("bookId", commentRequest.getBookId());
            return -1;
        }

        Book book = bookProvider.getBookById(commentRequest.getBookId());
        if(book == null)
            return 0;

        Comment comment = new Comment(user, book, commentRequest.getText());
        comment = commentsProvider.update(comment);

        return comment != null ? comment.getId() : 0;
    }

    @RequestMapping(value = "/deleteComment/{commentId}", method = RequestMethod.POST)
    public @ResponseBody boolean deleteComment(@PathVariable("commentId") int commentId){
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return false;

        Comment comment = commentsProvider.getById(commentId);
        if(comment == null || !comment.getUser().getId().equals(user.getId()))
            return false;

        boolean success = commentsProvider.removeComment(comment);

        return success;
    }


    @RequestMapping(value = "/voteForBook/{bookId}/{userMark}", method = RequestMethod.POST)
    public @ResponseBody float voteForBook(@PathVariable("bookId") int bookId,
                                         @PathVariable("userMark") int userMark){
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return -1;

        Book book = bookProvider.getBookById(bookId);
        if(book == null)
            return 0;

        book = bookProvider.userStarBook(user, book, userMark/10f);
        if(book == null)
            return 0;
        return book.getRating();
    }

    private static final String HOST_NAME = "http://nextbookdemo.azurewebsites.net/";
}


class CreateCommentRequest{
    private String text;
    private int bookId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}