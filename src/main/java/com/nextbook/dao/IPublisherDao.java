package com.nextbook.dao;

import com.nextbook.domain.pojo.Publisher;

import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */

public interface IPublisherDao {

    public Publisher updatePublisher(Publisher publisher);

    public boolean deletePublisher(int id);

    public Publisher getPublisherById (int id);

    List<Publisher> getAllPublishers(int from, int max);

}
