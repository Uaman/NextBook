package com.nextbook.dao.impl;

import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IBookProvider;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Stacy on 10/11/15.
 */
@Repository
public class FavoritesDao implements IFavoritesDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private IBookProvider bookProvider;

    @Override
    public boolean addToUserFavorites(User user, Book book) {
        if(user != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                UserEntity entity = DozerMapperFactory.getDozerBeanMapper().map(user, UserEntity.class);
                BookEntity bookEntity = DozerMapperFactory.getDozerBeanMapper().map(book,BookEntity.class);
                entity = (UserEntity) session.merge(entity);
                bookEntity = (BookEntity)session.merge(bookEntity);
                entity.getFavoriteBooksEnt().add(bookEntity);
                session.getTransaction().commit();
            } catch (Exception e) {
                if(session != null && session.getTransaction().isActive())
                    session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return true;
    }

    @Override
    public boolean deleteFromUserFavorites(User user, Book book) {
        if(user != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                UserEntity entity = DozerMapperFactory.getDozerBeanMapper().map(user, UserEntity.class);
                BookEntity bookEntity = DozerMapperFactory.getDozerBeanMapper().map(book,BookEntity.class);
                entity = (UserEntity) session.merge(entity);
                bookEntity = (BookEntity)session.merge(bookEntity);
                entity.getFavoriteBooksEnt().remove(bookEntity);
                session.getTransaction().commit();
            } catch (Exception e) {
                if(session != null && session.getTransaction().isActive())
                    session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return false;
    }

    @Override
    public Set<Book> getAllFavorites(User user) {
        Set<Book> result = new HashSet<Book>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            UserEntity userEntity = dozerBeanMapper.map(user, UserEntity.class);
            if(userEntity != null){
                Query query = session.createSQLQuery("SELECT BOOK.ID FROM BOOK JOIN FAVORITES ON BOOK.ID =  FAVORITES.book_id WHERE FAVORITES.USER_ID =:user_id");
                query.setParameter("user_id", userEntity.getId());
                List<Integer> ids = query.list();
                if(ids != null && ids.size() > 0) {
                    for(int id:ids) {
                        Book book = bookProvider.getBookById(id);
                        if(book!=null)
                            result.add(book);
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
