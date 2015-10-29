package com.nextbook.services;

import com.nextbook.domain.criterion.PublisherCriterion;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;

import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
public interface IPublisherProvider {

    PublisherEntity updatePublisher(PublisherEntity publisher);

    boolean deletePublisher(int id);

    boolean deletePublisher(PublisherEntity publisher);

    PublisherEntity getPublisherById(int id);

    List<PublisherEntity> getAllPublishers(int from, int max);

    List<PublisherEntity> getAll();

    List<PublisherEntity> getPublishersByCriterion(PublisherCriterion criterion);

    PublisherEntity getPublisherByUser(UserEntity user);

    int getPublishersQuantity();
}
