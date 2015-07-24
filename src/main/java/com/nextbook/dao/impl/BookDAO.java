package com.nextbook.dao.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.Book;
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
 * Date: 7/23/2015
 * Time: 4:47 PM
 */
@Repository
public class BookDAO implements IBookDao {

    @Override
    public Book getBookById(int bookId) {
        Book result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getById);
            query.setParameter("id", bookId);
            List<BookEntity> list = query.list();
            if(list != null && list.size() > 0) {
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), Book.class);
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
    public List<Book> getAllBooks() {
        List<Book> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT book FROM BookEntity book");
            List<BookEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Book>();
                for (BookEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Book temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Book.class);
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
    public boolean deleteBook(int bookId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getById);
            query.setParameter("id", bookId);
            List<BookEntity> list = query.list();
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
    public Book updateBook(Book book) {
        Book result = null;
        if(book != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                BookEntity entity = DozerMapperFactory.getDozerBeanMapper().map(book, BookEntity.class);
                entity = (BookEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Book.class);
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
}
