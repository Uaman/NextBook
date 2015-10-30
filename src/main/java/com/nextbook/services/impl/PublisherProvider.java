package com.nextbook.services.impl;

import com.nextbook.dao.IPublisherDao;
import com.nextbook.domain.criterion.PublisherCriterion;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.services.IPublisherProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
@Service
public class PublisherProvider implements IPublisherProvider{

    @Inject
    private IPublisherDao publisherDAO;

    @Transactional
    public PublisherEntity updatePublisher(PublisherEntity publisher) {
        return publisherDAO.updatePublisher(publisher);
    }

    @Transactional
    public boolean deletePublisher(int id) {
        return publisherDAO.deletePublisher(id);
    }

    @Transactional
    public boolean deletePublisher(PublisherEntity publisher) {
        return publisherDAO.deletePublisher(publisher.getId());
    }

    @Transactional
    public PublisherEntity getPublisherById(int id) {
        return publisherDAO.getPublisherById(id);
    }

    @Transactional
    public List<PublisherEntity> getAllPublishers(int from, int max) {
        return publisherDAO.getAllPublishers(from, max);
    }

    @Transactional
    public List<PublisherEntity> getAll(){
        return getAllPublishers(0, 0);
    }

    @Transactional
    public List<PublisherEntity> getPublishersByCriterion(PublisherCriterion criterion) {
        return publisherDAO.getPublishersByCriterion(criterion);
    }

    @Transactional
    public PublisherEntity getPublisherByUser(UserEntity user) {
        if(user == null)
            return null;
        return publisherDAO.getPublisherByUser(user);
    }

    @Transactional
    public int getPublishersQuantity() {
        return publisherDAO.getPublishersQuantity();
    }

}
