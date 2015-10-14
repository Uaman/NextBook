package com.nextbook.dao.impl;

import com.nextbook.dao.ICommentsDAO;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;
import com.nextbook.domain.filters.CommentsCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.domain.pojo.Order;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:02 AM
 */
@Repository
public class CommentsDAO implements ICommentsDAO {

    @Override
    public Comment getById(int id) {
        Comment result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            CommentEntity entity = (CommentEntity) session.load(CommentEntity.class, id);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, Comment.class);
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
    public Comment update(Comment comment) {
        Comment result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            CommentEntity entity = DozerMapperFactory.getDozerBeanMapper().map(comment, CommentEntity.class);
            entity = (CommentEntity) session.merge(entity);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, Comment.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public List<Comment> userComments(User user){
        List<Comment> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(CommentEntity.GET_ALL_COMMENTS_FOR_USER);
            query.setParameter("user_id", user.getId());
            List<CommentEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Comment>();
                for(CommentEntity entity : list) {
                    Comment comment = DozerMapperFactory.getDozerBeanMapper().map(entity, Comment.class);
                    result.add(comment);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public List<Comment> bookComments(Book book) {
        List<Comment> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(CommentEntity.GET_ALL_COMMENTS_FOR_BOOK);
            query.setParameter("book_id", book.getId());
            List<CommentEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Comment>();
                for(CommentEntity entity : list) {
                    Comment comment = DozerMapperFactory.getDozerBeanMapper().map(entity, Comment.class);
                    result.add(comment);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public boolean removeComment(Comment comment) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            CommentEntity toDelete = DozerMapperFactory.getDozerBeanMapper().map(comment, CommentEntity.class);
            if(toDelete != null){
                session.delete(toDelete);
            }
            session.getTransaction().commit();
            deleted = true;
        } catch (Exception e){
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return deleted;
    }

    @Override
    public List<Comment> getCommentsByCriterion(CommentsCriterion criterion) {
        List<Comment> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = buildQueryByCriterion(session, criterion);
            List<CommentEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Comment>();
                for(CommentEntity entity : list) {
                    Comment comment = DozerMapperFactory.getDozerBeanMapper().map(entity, Comment.class);
                    result.add(comment);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }


    private Query buildQueryByCriterion(Session session, CommentsCriterion criterion){
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT DISTINCT comment FROM CommentEntity comment");
        Map<String, Object> params = new HashMap<String, Object>();

        boolean where = false;

        if(criterion.getBook() != null){
            if(where) {
                queryString.append(" AND comment.book.id=:book_id");
            } else {
                queryString.append(" WHERE comment.book.id=:book_id");
            }
            where = true;
            params.put("book_id", criterion.getBook().getId());
        }
        if(criterion.getUser() != null){
            if(where) {
                queryString.append(" AND comment.user.id=:user_id");
            } else {
                queryString.append(" WHERE comment.user.id=:user_id");
            }
            where = true;
            params.put("user_id", criterion.getUser().getId());
        }
        if (criterion.getChangedBy() != null && criterion.getChangedBy() != StatusChangedBy.ALL){
            if(where) {
                queryString.append(" AND comment.changedBy=:changed_by");
            } else {
                queryString.append(" WHERE comment.changedBy=:changed_by");
            }
            where = true;
            params.put("changed_by", criterion.getChangedBy());
        }
        if (criterion.getStatus() != null && criterion.getStatus() != Status.ALL) {
            if(where) {
                queryString.append(" AND comment.status=:status");
            } else {
                queryString.append(" WHERE comment.status=:status");
            }
            where = true;
            params.put("status", criterion.getStatus());
        }

        long timeFrom = criterion.getTimeFrom();
        long timeTo = criterion.getTimeTo();
        if(timeFrom != 0 && timeTo != 0 && timeFrom > timeTo){
            criterion.setTimeTo(timeFrom);
            criterion.setTimeFrom(timeTo);
        }

        if(criterion.getTimeFrom() != 0){
            if(where) {
                queryString.append(" AND comment.time > :time_from");
            } else {
                queryString.append(" WHERE comment.time > :time_from");
            }
            params.put("time_from", criterion.getTimeFrom());
        }
        if(criterion.getTimeTo() != 0){
            if(where) {
                queryString.append(" AND comment.time < :time_to");
            } else {
                queryString.append(" WHERE comment.time < :time_to");
            }
            params.put("time_to", criterion.getTimeTo());
        }


        Query result = session.createQuery(queryString.toString());
        Set<String> keys = params.keySet();
        for(String s : keys)
            result.setParameter(s, params.get(s));

        if(criterion.getFrom() > 0)
            result.setFirstResult(criterion.getFrom());

        if(criterion.getMax() > 0)
            result.setMaxResults(criterion.getMax());

        return result;
    }
}
