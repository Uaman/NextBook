package com.nextbook.controllers;

import com.nextbook.domain.forms.SimplePublisherForm;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.impl.PublisherProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value="/update", method = RequestMethod.POST, headers = "Accept=application/json")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    Publisher updatePublisher(@RequestBody SimplePublisherForm form) {
        Publisher result = null;
        Publisher publisher = new Publisher();
        publisher.setId(form.getId());
        publisher.setNameEn(form.getNameEn());
        publisher.setNameRu(form.getNameRu());
        publisher.setNameUa(form.getNameUa());
        publisher.setDescription(form.getDescription());
        result = publisherProvider.updatePublisher(publisher);
        return result;
    }

    @RequestMapping(value="/delete/{id}")
    public @ResponseBody
    boolean deletePublisher(@PathVariable int id) {
        return publisherProvider.deletePublisher(id);
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
