package com.nextbook.dao.impl;

import com.nextbook.dao.IPublisherDao;
import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.User;
import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 24.07.2015.
 */
@Repository
public class PublisherDAO implements IPublisherDao {

    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        Publisher result = null;
        if(publisher != null) {
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                PublisherEntity entity = dozerBeanMapper.map(publisher, PublisherEntity.class);
                session.saveOrUpdate(entity);
                entity = (PublisherEntity) session.merge(entity);
                result = dozerBeanMapper.map(entity, Publisher.class);
                session.getTransaction().commit();
            } catch (Exception e) {
                if(session.getTransaction().isActive())
                    session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return result;
    }

    @Override
    public boolean deletePublisher(int id) {
        boolean deleted = false;
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(PublisherEntity.GET_BY_ID);
            query.setParameter("id", id);
            List<PublisherEntity> list = query.list();
            if(list != null && list.size() > 0) {
                session.delete(list.get(0));
            }
            session.getTransaction().commit();
            deleted = true;
        } catch (Exception e){
            if(session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return deleted;
    }

    @Override
    public Publisher getPublisherById(int id) {
        Publisher result = null;
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(PublisherEntity.GET_BY_ID);
            query.setParameter("id", id);
            List<PublisherEntity> list = query.list();
            if(list != null && list.size() > 0) {
                result = dozerBeanMapper.map(list.get(0), Publisher.class);
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public List<Publisher> getAllPublishers(int from, int max) {
        List<Publisher> result = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(PublisherEntity.GET_ALL);
            if(from > 0)
                query.setFirstResult(from);
            if(max > 0)
                query.setMaxResults(max);
            List<PublisherEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Publisher>();
                for (PublisherEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Publisher temp = dozerBeanMapper.map(entity, Publisher.class);
                            if (temp != null)
                                result.add(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public Publisher getPublisherByUser(User user) {
        Publisher result = null;
        Session session = sessionFactory.openSession();

        try{
            UserEntity userEntity = dozerBeanMapper.map(user, UserEntity.class);
            if(userEntity != null){
                Query query = session.getNamedQuery(PublisherEntity.GET_ALL);
                List<PublisherEntity> list = query.list();
                if(list != null && list.size() > 0) {
                    for(PublisherEntity publisherEntity : list) {
                        if (publisherEntity.getUsers().contains(userEntity)) {
                            result = dozerBeanMapper.map(list.get(0), Publisher.class);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }

        return result;
    }


}
