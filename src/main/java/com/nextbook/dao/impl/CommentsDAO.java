package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.ICommentsDAO;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;
import com.nextbook.domain.criterion.CommentsCriterion;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:02 AM
 */
@Repository
public class CommentsDAO implements ICommentsDAO {

    @Inject
    private Dao baseDao;

    @Transactional
    public CommentEntity getById(int id) {
        return baseDao.getById(CommentEntity.class, id);
    }

    @Transactional
    public CommentEntity update(CommentEntity comment) {
        return baseDao.attachWithMerge(comment);
    }

    @Transactional
    public List<CommentEntity> userComments(final UserEntity user){
        List<CommentEntity> result =
                baseDao.executeNamedQueryWithParams(
                        CommentEntity.class,
                        CommentEntity.GET_ALL_COMMENTS_FOR_USER,
                        new HashMap<String, Object>() {{
                            put("user_id", user.getId());
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public List<CommentEntity> bookComments(final BookEntity book) {
        List<CommentEntity> result =
                baseDao.executeNamedQueryWithParams(
                        CommentEntity.class,
                        CommentEntity.GET_ALL_COMMENTS_FOR_BOOK,
                        new HashMap<String, Object>() {{
                            put("book_id", book.getId());
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public boolean removeComment(CommentEntity comment) {
        return baseDao.deleteById(CommentEntity.class, comment.getId());
    }

    @Transactional
    public List<CommentEntity> getCommentsByCriterion(CommentsCriterion criterion) {
        List<CommentEntity> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = buildQueryByCriterion(session, criterion);
            result = query.list();
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
