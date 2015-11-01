package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.criterion.PublisherCriterion;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.forms.publishers.SimplePublisherForm;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
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
    private IUserProvider userProvider;

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody
    PublisherEntity updatePublisher(@RequestBody SimplePublisherForm form) {
        PublisherEntity result = null;
        PublisherEntity publisher = null;
        if (form.getId()!=0)
            publisher = publisherProvider.getPublisherById(form.getId());
        if (publisher==null)
            publisher = new PublisherEntity();
        publisher.setNameEn(form.getNameEn());
        publisher.setNameRu(form.getNameRu());
        publisher.setNameUa(form.getNameUa());
        publisher.setDescription(form.getDescription());
        result = publisherProvider.updatePublisher(publisher);
        return result;
    }

    @RequestMapping(value="/edit-publisher/{id}")
    @PreAuthorize("@Secure.isAdmin()")
    public String updatePublisherPage(@PathVariable int id, Model model) {
        PublisherEntity publisher = publisherProvider.getPublisherById(id);
        if (publisher==null) {
            model.addAttribute("edit", false);
        } else {
            model.addAttribute("edit", true);
            model.addAttribute("publisher", publisher);
        }
        return "/admin/publishers/edit-publisher";
    }

    @RequestMapping(value="/manage-users")
    @PreAuthorize("@Secure.isAdmin()")
    public String updatePublisherPage(@RequestParam int publisher,
                                      @RequestParam(required = false, defaultValue = "0") int user,
                                      @RequestParam(required = false) String action,
                                      Model model) {
        PublisherEntity publ = publisherProvider.getPublisherById(publisher);
        if (publ==null) {
            return "404";
        } else {
            if (user != 0) {
                UserEntity us = userProvider.getById(user);
                if (us != null && action!=null) {
                    if (action.equals("add")) publ.getUsers().add(us);
                    if (action.equals("delete")) publ.getUsers().remove(us);
                    publisherProvider.updatePublisher(publ);
                }
            }
            List<UserEntity> users = userProvider.getAll();
            List<UserEntity> allUsers = new ArrayList<UserEntity>();
            List<UserEntity> publisherUsers = new ArrayList<UserEntity>();
            List<UserEntity> anotherPublisherUsers = new ArrayList<UserEntity>();
            for (UserEntity u:users) {
                if (isUserInPubisher(u, publ)) {
                    publisherUsers.add(u);
                } else if (publisherProvider.getPublisherByUser(u)==null){
                    allUsers.add(u);
                } else {
                    anotherPublisherUsers.add(u);
                }
            }
            model.addAttribute("publisher", publ);
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("publisherUsers", publisherUsers);
            model.addAttribute("anotherPublisherUsers", anotherPublisherUsers);
        }
        return "/admin/publishers/manage-users";
    }

    public boolean isUserInPubisher(UserEntity user, PublisherEntity publisher) {
        for (UserEntity userp:publisher.getUsers()) {
            if (user.getId().equals(userp.getId())) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value="/add-publisher")
    @PreAuthorize("@Secure.isAdmin()")
    public String updatePublisherPage(Model model) {
        model.addAttribute("edit", false);
        return "/admin/publishers/edit-publisher";
    }


    @RequestMapping(value="/delete/{id}")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody
    boolean deletePublisher(@PathVariable int id) {
        return publisherProvider.deletePublisher(id);
    }

    @RequestMapping(value="/{id}")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody
    PublisherEntity getPublisherById(@PathVariable int id) {
        return publisherProvider.getPublisherById(id);
    }

    @RequestMapping(value="/all")
    @PreAuthorize("@Secure.isAdmin()")
    public String getAllPublishers(Model model,
                                   @RequestParam (defaultValue = "0") int from,
                                   @RequestParam (defaultValue = "0")  int max) {
        model.addAttribute("publishers", publisherProvider.getAllPublishers(from, max));
        return "admin/publishers/publishers-admin";
    }

    @RequestMapping(value="/add-user")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody boolean addUserToPublisher(@RequestParam int publisherID, @RequestParam int userID) {
        PublisherEntity publisher = publisherProvider.getPublisherById(publisherID);
        boolean res = false;
        if (publisher!=null) {
            UserEntity user = userProvider.getById(userID);
            if (user!=null && publisherProvider.getPublisherByUser(user)==null) {
                publisher.getUsers().add(user);
                publisherProvider.updatePublisher(publisher);
                res = true;
            }
        }
        return res;
    }


    @RequestMapping(value="/delete-user")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody boolean deleteUserFromPublisher(@RequestParam int publisherID, @RequestParam int userID) {
        PublisherEntity publisher = publisherProvider.getPublisherById(publisherID);
        if (publisher!=null) {
            publisher.getUsers().remove(userProvider.getById(userID));
            publisherProvider.updatePublisher(publisher);
            return true;
        }
        return false;
    }

    @RequestMapping(value="/filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody
    List<PublisherEntity> getPublishersByCriterion(@RequestBody PublisherCriterion criterion) {
        return publisherProvider.getPublishersByCriterion(criterion);
    }

}
