package com.nextbook.dao.impl;

import com.nextbook.dao.IUserDao;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.User;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:58 PM
 */
@Repository
public class UserDAO implements IUserDao{

    @Override
         public User getById(int userId) {
        User result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            UserEntity entity = (UserEntity) session.load(UserEntity.class, userId);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, User.class);
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
    public User update(User user) {
        User result = null;
        if(user != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                UserEntity entity = DozerMapperFactory.getDozerBeanMapper().map(user, UserEntity.class);
                entity = (UserEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, User.class);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return result;
    }

    @Override
    public boolean add(User user) {
        boolean added = false;
        if(user != null){
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                UserEntity entity = DozerMapperFactory.getDozerBeanMapper().map(user, UserEntity.class);
                session.save(entity);
                session.getTransaction().commit();
            } catch (Exception e){
                if(session.getTransaction().isActive())
                    session.getTransaction().rollback();
                added = false;
                e.printStackTrace();
            } finally {
                if(session != null && session.isOpen())
                    session.close();
            }
        }
        return added;
    }

    @Override
    public List<User> getAll(int from, int max) {
        List<User> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<UserEntity> entities = null;
            session.beginTransaction();
            Query query = session.getNamedQuery(UserEntity.getAllUsers);
            entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<User>();
                for (UserEntity entity : entities) {
                    if (entity != null) {
                        try {
                            User temp = DozerMapperFactory.getDozerBeanMapper().map(entity, User.class);
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
    public boolean delete(int userId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            UserEntity toDelete = (UserEntity)session.load(UserEntity.class, userId);
            if(toDelete != null){
                session.delete(toDelete);
            }
            session.getTransaction().commit();
            deleted = true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return deleted;
    }


}
