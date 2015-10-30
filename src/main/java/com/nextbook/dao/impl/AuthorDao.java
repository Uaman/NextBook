package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IAuthorDao;
import com.nextbook.domain.entities.AuthorEntity;
import com.nextbook.domain.criterion.AuthorCriterion;
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
 * Date: 7/24/2015
 * Time: 4:18 PM
 */
@Repository
public class AuthorDao implements IAuthorDao{

    @Inject
    private Dao baseDao;

    @Transactional
    public AuthorEntity updateAuthor(AuthorEntity author) {
        return baseDao.attachWithMerge(author);
    }

    @Transactional
    public boolean deleteAuthor(int authorId) {
        return baseDao.deleteById(AuthorEntity.class, authorId);
    }

    @Transactional
    public AuthorEntity getById(int authorId) {
        return baseDao.getById(AuthorEntity.class, authorId);
    }

    @Transactional
    public List<AuthorEntity> getFromMax(int from, int max) {
        List<AuthorEntity> result =
                baseDao.executeNamedQueryWithParams(
                        AuthorEntity.class,
                        AuthorEntity.getAllUsers,
                        from,
                        max);
        return (result == null || result.isEmpty()) ? null : result;
    }
    @Transactional
    public List<AuthorEntity> getAuthorsByCriterion(AuthorCriterion criterion) {
        List<AuthorEntity> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    @Transactional
    public AuthorEntity getByFirstAndLastName(final String fName, final String lName) {
        List<AuthorEntity> result =
                baseDao.executeNamedQueryWithParams(
                        AuthorEntity.class,
                        AuthorEntity.getByFirstAndLastName,
                        new HashMap<String, Object>() {{
                            put("fName", fName);
                            put("lName", lName);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }

    private Query createQueryFromCriterion(Session session, AuthorCriterion criterion){
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT author FROM AuthorEntity author");
        Map<String, Object> params = new HashMap<String, Object>();

        boolean where = false;

        if(validString(criterion.getFirstName())){
            queryString.append(" WHERE author.firstNameUa LIKE :firstName OR author.firstNameEn LIKE :firstName OR author.firstNameRu LIKE :firstName");
            params.put("firstName", '%'+criterion.getFirstName()+'%');
            where = true;
        }

        if(validString(criterion.getLastName())){
            if(where){
                queryString.append(" AND author.lastNameUa LIKE :lastName OR author.lastNameEn LIKE :lastName OR author.lastNameRu LIKE :lastName");
            } else {
                queryString.append(" WHERE author.lastNameUa LIKE :lastName OR author.lastNameEn LIKE :lastName OR author.lastNameRu LIKE :lastName");
                where = true;
            }
            params.put("lastName", '%'+criterion.getLastName()+'%');
        }

        Query result = session.createQuery(queryString.toString());

        for (Map.Entry<String, Object> param : params.entrySet()){
            result.setParameter(param.getKey(), param.getValue());}

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
