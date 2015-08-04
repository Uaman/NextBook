package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.forms.publishers.SimplePublisherForm;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.omg.CORBA.Request;
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

    @RequestMapping(value="/add")
    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public String addPublisher(Model model, @RequestParam boolean first) {
        model.addAttribute("first", first);
        model.addAttribute("edit", false);
        return "/publisher/edit-publisher";
    }

    @RequestMapping(value="/update/{id}")
    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public String updatePublisher(Model model,
                                  @PathVariable int id,
                                  @RequestParam (defaultValue = "false", required = false) boolean first) {
        Publisher publisher = publisherProvider.getPublisherById(id);
        Publisher upublisher = publisherProvider.getPublisherByUser(sessionUtils.getCurrentUser());
        if (publisher==null || upublisher==null || publisher.getId() != upublisher.getId())
            return "redirect:/cabinet/profile";
        model.addAttribute("publisher", publisher);
        model.addAttribute("first", first);
        model.addAttribute("edit", true);
        return "/publisher/edit-publisher";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public @ResponseBody
    Publisher updatePublisher(@RequestBody SimplePublisherForm form) {
        Publisher result = null;
        Publisher publisher = null;
        User user = sessionUtils.getCurrentUser();
        if (form.getId()!=0)
            publisher = publisherProvider.getPublisherById(form.getId());
        if (publisher==null) {
            publisher = new Publisher();
            publisher.addUser(sessionUtils.getCurrentUser());
        } else {
            Publisher upublisher = publisherProvider.getPublisherByUser(user);
            if (upublisher != null && publisher.getId() != upublisher.getId())
                return null;
        }
        publisher.setNameEn(form.getNameEn());
        publisher.setNameRu(form.getNameRu());
        publisher.setNameUa(form.getNameUa());
        publisher.setDescription(form.getDescription());
        result = publisherProvider.updatePublisher(publisher);
        return result;
    }

    @RequestMapping(value="/{id}")
    public @ResponseBody
    Publisher getPublisherById(@PathVariable int id) {
        return publisherProvider.getPublisherById(id);
    }

    @RequestMapping(value="/all")
    public @ResponseBody
    List<Publisher> getAllPublishers(@RequestParam (defaultValue = "0") int from, @RequestParam (defaultValue = "0")  int max) {
        return publisherProvider.getAllPublishers(from, max);
    }

}
