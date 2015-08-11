package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.forms.publishers.SimplePublisherForm;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.Role;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.apache.http.HttpResponse;
import org.omg.CORBA.Request;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @RequestMapping(value="/new")
     public String addPublisher() {
        User user = sessionUtils.getCurrentUser();
        if (publisherProvider.getPublisherByUser(user)!=null)
            return "redirect:/cabinet/profile";
        Publisher publisher = new Publisher();
        publisher.setDescription("");
        publisher.setNameUa("");
        publisher.addUser(user);
        publisher = publisherProvider.updatePublisher(publisher);
        Role publisherRole = new Role();
        publisherRole.setId(3);
        user.setRole(publisherRole);
        user = userProvider.update(user);
        return "redirect:/publisher/update?publisherId="+publisher.getId();
    }

    @RequestMapping(value="/update")
    @PreAuthorize("@Secure.isPublisher()")
    //user that just create publication can not 'go though' this annotation,
    //cause he has no role publication. resign in resolve it
    public String updatePublisher(Model model,
                                  @RequestParam("publisherId") int id) {
        Publisher publisher = publisherProvider.getPublisherById(id);
        Publisher upublisher = publisherProvider.getPublisherByUser(sessionUtils.getCurrentUser());
        if (publisher==null || upublisher==null || publisher.getId() != upublisher.getId())
            return "redirect:/cabinet/profile";
        model.addAttribute("publisher", publisher);
        return "/publisher/edit-publisher";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("@Secure.isPublisher()")
    public @ResponseBody int updatePublisher(@RequestBody SimplePublisherForm form) {
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return -1;

        Publisher userPublisher = publisherProvider.getPublisherByUser(user);
        Publisher publisher = publisherProvider.getPublisherById(form.getId());
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
        User user = sessionUtils.getCurrentUser();
        if(user == null)
            return "redirect:/";
        Publisher publisher = publisherProvider.getPublisherById(id);
        if(publisher == null)
            return "redirect:/";
        if(!publisher.getUsers().contains(user))
            return "redirect:/";
        List<Book> books = bookProvider.getAllPublisherBooks(publisher.getId());
        List<User> users = publisher.getUsers();
        model.addAttribute("books", books);
        model.addAttribute("users", users);
        model.addAttribute("publisherId", publisher.getId());
        return "publisher/view-publisher";
    }

    @RequestMapping(value="/all")
    public @ResponseBody
    List<Publisher> getAllPublishers(@RequestParam (defaultValue = "0") int from, @RequestParam (defaultValue = "0")  int max) {
        return publisherProvider.getAllPublishers(from, max);
    }

}
