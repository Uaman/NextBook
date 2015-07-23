package com.nextbook.dao.impl;

import com.nextbook.dao.IUserDao;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.pojo.User;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                added = true;
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
            if(from > 0)
                query.setFirstResult(from);
            if(max > 0)
                query.setMaxResults(max);
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

    @Override
    public List<User> getUsersByCriterion(UserCriterion criterion) {
        List<User> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<UserEntity> entities = null;
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
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
    public User getUserByEmail(String email) {
        User result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(UserEntity.getUserByEmail);
            query.setParameter("email", email);
            List<Object> list = query.list();

            if(list != null && list.size() > 0) {
                UserEntity entity = (UserEntity) list.get(0);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, User.class);
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

    private Query createQueryFromCriterion(Session session, UserCriterion criterion){
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT user FROM UserEntity user");
        Map<String, Object> params = new HashMap<String, Object>();

        boolean where = false;

        if(validString(criterion.getName())){
            queryString.append(" WHERE user.name LIKE :name");
            params.put("name", '%'+criterion.getName()+'%');
            where = true;
        }
        if(validString(criterion.getEmail())){
            if(where) {
                queryString.append(" AND user.email LIKE :email");
            } else {
                queryString.append(" WHERE user.email LIKE :email");
            }
            params.put("email", '%'+criterion.getEmail()+'%');
            where = true;
        }
        if (!criterion.getState().equals("all")) {
            if (where) {
                queryString.append(" AND user.active=:active");
            } else {
                queryString.append(" WHERE user.active=:active");
            }
            params.put("active", criterion.getState().equalsIgnoreCase("active"));
            where = true;
        }
        if(criterion.getRoleId() > 0) {
            if(where) {
                queryString.append(" AND user.roleId=:roleId");
            } else {
                queryString.append(" WHERE user.roleId=:roleId");
            }
            params.put("roleId", criterion.getRoleId());
            where = true;
        }

        Query result = session.createQuery(queryString.toString());

        for (Map.Entry<String, Object> param : params.entrySet())
            result.setParameter(param.getKey(), param.getValue());

        if(criterion.getFrom() > 0)
            result.setFirstResult(criterion.getFrom());

        if(criterion.getMax() > 0)
            result.setFirstResult(criterion.getMax());

        return result;
    }

    private boolean validString(String s){
        return s != null && !s.equals("");
    }

}
