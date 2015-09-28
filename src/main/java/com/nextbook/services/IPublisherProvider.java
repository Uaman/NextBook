package com.nextbook.services;

import com.nextbook.domain.filters.PublisherCriterion;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
public interface IPublisherProvider {

    public Publisher updatePublisher(Publisher publisher);

    public boolean deletePublisher(int id);

    public boolean deletePublisher(Publisher publisher);

    public Publisher getPublisherById (int id);

    List<Publisher> getAllPublishers(int from, int max);

    List<Publisher> getPublishersByCriterion(PublisherCriterion criterion);

    Publisher getPublisherByUser(User user);

    int getPublishersQuantity();
}
