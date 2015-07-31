package com.nextbook.services.impl;

import com.nextbook.dao.IPublisherDao;
import com.nextbook.dao.impl.PublisherDAO;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IPublisherProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
@Service
public class PublisherProvider implements IPublisherProvider{

    @Inject
    private IPublisherDao publisherDAO;

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        return publisherDAO.updatePublisher(publisher);
    }

    @Override
    public boolean deletePublisher(int id) {
        return publisherDAO.deletePublisher(id);
    }

    @Override
    public boolean deletePublisher(Publisher publisher) {
        return publisherDAO.deletePublisher(publisher.getId());
    }

    @Override
    public Publisher getPublisherById(int id) {
        return publisherDAO.getPublisherById(id);
    }

    @Override
    public List<Publisher> getAllPublishers(int from, int max) {
        return publisherDAO.getAllPublishers(from, max);
    }

    @Override
    public Publisher getPublisherByUser(User user) {
        if(user == null)
            return null;
        return publisherDAO.getPublisherByUser(user);
    }

}
