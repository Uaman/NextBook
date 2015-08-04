package com.nextbook.dao.impl;

import com.nextbook.dao.IAuthorDao;
import com.nextbook.domain.entities.AuthorEntity;
import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.pojo.Author;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
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
 * Date: 7/24/2015
 * Time: 4:18 PM
 */
@Repository
public class AuthorDao implements IAuthorDao{

    @Override
    public Author updateAuthor(Author author) {
        Author result = null;
        if(author != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                AuthorEntity entity = DozerMapperFactory.getDozerBeanMapper().map(author, AuthorEntity.class);
                entity = (AuthorEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Author.class);
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
    public boolean deleteAuthor(int authorId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(AuthorEntity.getById);
            query.setParameter("id", authorId);
            List<AuthorEntity> list = query.list();
            if(list != null && list.size() > 0) {
                session.delete(list.get(0));
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
    public Author getById(int authorId) {
        Author result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(AuthorEntity.getById);
            query.setParameter("id", authorId);
            List<AuthorEntity> list = query.list();
            if(list != null && list.size() > 0) {
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), Author.class);
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
    public List<Author> getFromMax(int from, int max) {
        List<Author> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<AuthorEntity> entities = null;
            session.beginTransaction();
            Query query = session.getNamedQuery(AuthorEntity.getAllUsers);
            if(from > 0)
                query.setFirstResult(from);
            if(max > 0)
                query.setMaxResults(max);
            entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Author>();
                for (AuthorEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Author temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Author.class);
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
    public List<Author> getAuthorsByCriterion(AuthorCriterion criterion) {
        List<Author> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<AuthorEntity> entities = null;
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Author>();
                for (AuthorEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Author temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Author.class);
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
    public Author getByFirstAndLastName(String fName, String lName) {
        Author author = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(AuthorEntity.getByFirstAndLastName);
            query.setParameter("fName", fName);
            query.setParameter("lName", lName);
            List<AuthorEntity> list = query.list();
            if(list != null && list.size() > 0){
                author = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), Author.class);
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }

        return author;
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
