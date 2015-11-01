package com.nextbook.dao;

import com.nextbook.domain.criterion.PublisherCriterion;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;

import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */

public interface IPublisherDao {

    PublisherEntity updatePublisher(PublisherEntity publisher);

    public boolean deletePublisher(int id);

    public PublisherEntity getPublisherById(int id);

    List<PublisherEntity> getAllPublishers(int from, int max);

    int getPublishersQuantity();

    List<PublisherEntity> getPublishersByCriterion(PublisherCriterion criterion);

    PublisherEntity getPublisherByUser(UserEntity user);
}
