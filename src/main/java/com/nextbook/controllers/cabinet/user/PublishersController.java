package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.entities.*;
import com.nextbook.domain.forms.publishers.SimplePublisherForm;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.ICommentsProvider;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
@Controller
@RequestMapping("/publisher")
public class PublishersController {

    @Inject
    private IPublisherProvider publisherProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private IBookProvider bookProvider;
    @Inject
    private IUserProvider userProvider;
    @Inject
    private ICommentsProvider commentsProvider;

    @RequestMapping(value="/new")
     public String addPublisher() {
        UserEntity user = sessionUtils.getCurrentUser();
        if (publisherProvider.getPublisherByUser(user)!=null)
            return "redirect:/cabinet/profile";
        PublisherEntity publisher = new PublisherEntity();
        publisher.setDescription("");
        publisher.setNameUa("");
        publisher.getUsers().add(user);
        publisher = publisherProvider.updatePublisher(publisher);
        RoleEntity publisherRole = new RoleEntity();
        publisherRole.setId(3);
        user.setRoleEntity(publisherRole);
        user = userProvider.update(user);
        return "redirect:/publisher/update?publisherId="+publisher.getId();
    }

    @RequestMapping(value="/update")
    @PreAuthorize("@Secure.isPublisher()")
    //user that just create publication can not 'go though' this annotation,
    //cause he has no role publication. resign in resolve it
    public String updatePublisher(Model model,
                                  @RequestParam("publisherId") int id) {
        PublisherEntity publisher = publisherProvider.getPublisherById(id);
        PublisherEntity upublisher = publisherProvider.getPublisherByUser(sessionUtils.getCurrentUser());
        if (publisher==null || upublisher==null || publisher.getId() != upublisher.getId())
            return "redirect:/cabinet/profile";
        model.addAttribute("publisher", publisher);
        return "/publisher/edit-publisher";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("@Secure.isPublisher()")
    public @ResponseBody int updatePublisher(@RequestBody SimplePublisherForm form) {
        UserEntity user = sessionUtils.getCurrentUser();
        if(user == null)
            return -1;

        PublisherEntity userPublisher = publisherProvider.getPublisherByUser(user);
        PublisherEntity publisher = publisherProvider.getPublisherById(form.getId());
        if(userPublisher == null || publisher == null || userPublisher.getId() != publisher.getId())
            return -1;

        publisher.setNameEn(form.getNameEn());
        publisher.setNameRu(form.getNameRu());
        publisher.setNameUa(form.getNameUa());
        publisher.setDescription(form.getDescription());
        publisher = publisherProvider.updatePublisher(publisher);

        return publisher.getId();
    }

    @RequestMapping(value="/view", method = RequestMethod.GET)
    public String publisherPreview(@RequestParam("publisherId") int id,
                                   Model model) {
        UserEntity user = sessionUtils.getCurrentUser();
        if(user == null)
            return "redirect:/";
        PublisherEntity publisher = publisherProvider.getPublisherById(id);
        if(publisher == null)
            return "redirect:/";
        if(!publisher.getUsers().contains(user))
            return "redirect:/";
        List<BookEntity> books = bookProvider.getAllPublisherBooks(publisher.getId());
        List<UserEntity> users = publisher.getUsers();
        model.addAttribute("books", books);
        model.addAttribute("users", users);
        model.addAttribute("publisherId", publisher.getId());
        return "publisher/view-publisher";
    }

    @RequestMapping(value="/all")
    public @ResponseBody
    List<PublisherEntity> getAllPublishers(@RequestParam (defaultValue = "0") int from, @RequestParam (defaultValue = "0")  int max) {
        return publisherProvider.getAllPublishers(from, max);
    }

    @PreAuthorize("@Secure.isPublisher()")
    @RequestMapping(value = "/activateComment/{commentId}", method = RequestMethod.POST)
    public @ResponseBody boolean activateComment(@PathVariable("commentId") int commentId){
        CommentEntity comment = commentsProvider.getById(commentId);
        if(comment == null)
            return false;
        comment = commentsProvider.publisherActivateComment(comment);

        return comment != null;
    }

    @PreAuthorize("@Secure.isPublisher()")
    @RequestMapping(value = "/deactivateComment/{commentId}", method = RequestMethod.POST)
    public @ResponseBody boolean deactivateComment(@PathVariable("commentId") int commentId){
        CommentEntity comment = commentsProvider.getById(commentId);
        if(comment == null)
            return false;
        comment = commentsProvider.publisherDeactivateComment(comment);

        return comment != null;
    }

    @PreAuthorize("@Secure.isPublisher()")
    @RequestMapping(value = "/sendBookForReview/{bookId}", method = RequestMethod.POST)
    public @ResponseBody boolean sendBookForReview(@PathVariable("bookId") int bookId){
        BookEntity book = bookProvider.getBookById(bookId);
        book = bookProvider.publisherSendBookForReview(book);
        return book != null;
    }
}
