package com.nextbook.dao.impl;

import com.google.common.collect.Maps;
import com.nextbook.dao.Dao;
import com.nextbook.dao.IPublisherDao;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.criterion.PublisherCriterion;
import com.nextbook.domain.entities.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
@Repository
public class PublisherDAO implements IPublisherDao {

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private Dao baseDao;

    @Transactional
    public PublisherEntity updatePublisher(PublisherEntity publisher) {
        return baseDao.attachWithMerge(publisher);
    }

    @Transactional
    public boolean deletePublisher(int id) {
        return baseDao.deleteById(PublisherEntity.class, id);
    }

    @Transactional
    public PublisherEntity getPublisherById(int id) {
        return baseDao.getById(PublisherEntity.class, id);
    }

    @Transactional
    public List<PublisherEntity> getAllPublishers(int from, int max) {
        List<PublisherEntity> result =
                baseDao.executeNamedQueryWithParams(
                        PublisherEntity.class,
                        PublisherEntity.GET_ALL,
                        from,
                        max);
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public int getPublishersQuantity() {
        return baseDao.executeCountNamedQueryWithParams
                                (PublisherEntity.class,
                                PublisherEntity.GET_PUBLISHERS_QUANTITY,
                                Maps.<String, Object>newHashMap());
    }

    @Transactional
    public List<PublisherEntity> getPublishersByCriterion(PublisherCriterion criterion) {
        List<PublisherEntity> result = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            result = query.list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    private Query createQueryFromCriterion(Session session, PublisherCriterion criterion) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT publisher FROM PublisherEntity publisher");

        boolean where = false;

        if(criterion.getId() > 0){
            queryString.append(" WHERE publisher.id=:id");
            where = true;
        }
        if (validString(criterion.getName())){
            if(where) {
                queryString.append(" AND (publisher.nameEn LIKE '%'||:name||'%'");
            } else {
                queryString.append(" WHERE (publisher.nameEn LIKE '%'||:name||'%'");
            }
            queryString.append(" OR publisher.nameRu LIKE '%'||:name||'%'");
            queryString.append(" OR publisher.nameUa LIKE '%'||:name||'%')");
            where = true;
        }
        if (validString(criterion.getDescription())){
            if(where) {
                queryString.append(" AND publisher.description LIKE '%'||:description||'%'");
            } else {
                queryString.append(" WHERE publisher.description LIKE '%'||:description||'%'");
            }
            where = true;
        }

        Query result = session.createQuery(queryString.toString());
        result.setProperties(criterion);

        if(criterion.getFrom() > 0)
            result.setFirstResult(criterion.getFrom());

        if(criterion.getMax() > 0)
            result.setMaxResults(criterion.getMax());

        return result;
    }

    private boolean validString(String s){
        return s != null && !s.equals("");
    }

    @Transactional
    public PublisherEntity getPublisherByUser(final UserEntity user) {
        return user.getPublisher();
//        List<Integer> publisherIds  =
//                baseDao.executeQueryWithParams(
//                        Integer.class,
//                        "SELECT DISTINCT(PUBLISHER.ID) " +
//                                "FROM PUBLISHER JOIN USERS_TO_PUBLISHER " +
//                                "ON PUBLISHER.ID=USERS_TO_PUBLISHER.PUBLISHER_ID " +
//                                "WHERE USERS_TO_PUBLISHER.USER_ID=:user_id",
//                        new HashMap<String, Object>(){{
//                            put("user_id", user.getId());
//                        }});
//        return  publisherIds != null && ! publisherIds.isEmpty()
//                ? getPublisherById(publisherIds.get(0)) : null;
//        "SELECT publisher FROM PublisherEntity publisher " +
//                "WHERE :user IN (publisher.users)",


//        List<PublisherEntity> allPublishers = baseDao.getAll(PublisherEntity.class);
//                sessionFactory.getCurrentSession().createQuery("from PublisherEntity").list();
//        for(PublisherEntity publisherEntity: allPublishers) {
//            System.out.println("COOL: "+publisherEntity);
//            if (publisherEntity.getUsers().contains(user)) {
//                System.out.println("HERE: "+publisherEntity);
//                return publisherEntity;
//            }
//        }
//        return user.getPublisherEntity();
    }

}
