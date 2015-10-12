package com.nextbook.dao.impl;

import com.nextbook.dao.ICommentsDAO;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:02 AM
 */
@Repository
public class CommentsDAO implements ICommentsDAO {

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

}
