package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.filters.PublisherCriterion;
import com.nextbook.domain.forms.publishers.SimplePublisherForm;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import org.springframework.http.MediaType;
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
@RequestMapping("admin/publishers")
public class AdminPublisherController {

    @Inject
    private IPublisherProvider publisherProvider;
    @Inject
    IUserProvider userProvider;

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    Publisher updatePublisher(@RequestBody SimplePublisherForm form) {
        Publisher result = null;
        Publisher publisher = null;
        if (form.getId()!=0)
            publisher = publisherProvider.getPublisherById(form.getId());
        if (publisher==null)
            publisher = new Publisher();
        publisher.setNameEn(form.getNameEn());
        publisher.setNameRu(form.getNameRu());
        publisher.setNameUa(form.getNameUa());
        publisher.setDescription(form.getDescription());
        result = publisherProvider.updatePublisher(publisher);
        return result;
    }

    @RequestMapping(value="/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    boolean deletePublisher(@PathVariable int id) {
        return publisherProvider.deletePublisher(id);
    }

    @RequestMapping(value="/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    Publisher getPublisherById(@PathVariable int id) {
        return publisherProvider.getPublisherById(id);
    }

    @RequestMapping(value="/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllPublishers(Model model,
                                   @RequestParam (defaultValue = "0") int from,
                                   @RequestParam (defaultValue = "0")  int max) {
        model.addAttribute("publishers", publisherProvider.getAllPublishers(from, max));
        return "publishers/publishers-admin";
    }

    @RequestMapping(value="/add-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody boolean addUserToPublisher(@RequestParam int publisherID, @RequestParam int userID) {
        Publisher publisher = publisherProvider.getPublisherById(publisherID);
        boolean res = false;
        if (publisher!=null) {
            User user = userProvider.getById(userID);
            if (user!=null) {
                publisher.addUser(user);
                publisherProvider.updatePublisher(publisher);
                res = true;
            }
        }
        return res;
    }


    @RequestMapping(value="/delete-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody boolean deleteUserFromPublisher(@RequestParam int publisherID, @RequestParam int userID) {
        Publisher publisher = publisherProvider.getPublisherById(publisherID);
        if (publisher!=null) {
            publisher.deleteUser(userID);
            publisherProvider.updatePublisher(publisher);
            return true;
        }
        return false;
    }

    @RequestMapping(value="/filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    List<Publisher> getPublishersByCriterion(@RequestBody PublisherCriterion criterion) {
        return publisherProvider.getPublishersByCriterion(criterion);
    }

}
