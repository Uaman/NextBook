package com.nextbook.dao.impl;

import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Favorites;
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

import java.util.ArrayList;
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

    @Override
    public Favorites addToUserFavorites(Favorites favorite) {
        Favorites result = null;
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                FavoritesEntity entity = DozerMapperFactory.getDozerBeanMapper().map(favorite, FavoritesEntity.class);
                entity = (FavoritesEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Favorites.class);
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
    public boolean deleteFromUserFavorites(int userId, int bookId) {
        boolean result = false;
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query query = session.getNamedQuery(FavoritesEntity.getByUserAndBook);
                query.setParameter("bookId", bookId);
                query.setParameter("userId", userId);
                List<FavoritesEntity> list = query.list();
                if(list != null && list.size() > 0) {
                    session.delete(list.get(0));
                }
                session.getTransaction().commit();
                result = true;
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
    public List<Favorites> getAllFavorites(User user) {
        List<Favorites> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(FavoritesEntity.getAllFavorites);
            List<FavoritesEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Favorites>();
                for (FavoritesEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Favorites favorite = DozerMapperFactory.getDozerBeanMapper().map(entity, Favorites.class);
                            if (favorite != null)
                                result.add(favorite);
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

}
