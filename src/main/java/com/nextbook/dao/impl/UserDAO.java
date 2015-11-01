package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IUserDao;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.criterion.UserCriterion;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    @Inject
    private Dao baseDao;

    @Transactional
    public UserEntity getById(int userId) {
        return baseDao.getById(UserEntity.class, userId);
    }

    @Transactional
    public UserEntity update(UserEntity user) {
        return baseDao.attachWithMerge(user);
    }

    @Transactional
    public List<UserEntity> getAll(int from, int max) {
        List<UserEntity> result =
                baseDao.executeNamedQueryWithParams(
                        UserEntity.class,
                        UserEntity.getAllUsers,
                        from,
                        max);
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public boolean delete(int userId) {
        return baseDao.deleteById(UserEntity.class, userId);
    }

    @Transactional
    public List<UserEntity> getUsersByCriterion(UserCriterion criterion) {
        List<UserEntity> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            result =  query.list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Transactional
    public UserEntity getUserByEmail(final String email) {
        List<UserEntity> result =
                baseDao.executeNamedQueryWithParams(
                        UserEntity.class,
                        UserEntity.getUserByEmail,
                        new HashMap<String, Object>(){{
                            put("email", email);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
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
                queryString.append(" AND user.roleEntity.id=:roleId");
            } else {
                queryString.append(" WHERE user.roleEntity.id=:roleId");
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
            result.setMaxResults(criterion.getMax());

        return result;
    }

    private boolean validString(String s){
        return s != null && !s.equals("");
    }

}
