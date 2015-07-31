package com.nextbook.dao.impl;

import com.nextbook.dao.IKeywordDao;
import com.nextbook.domain.entities.KeywordEntity;
import com.nextbook.domain.pojo.Keyword;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:26 PM
 */
@Repository
public class KeywordDAO implements IKeywordDao{

    @Override
    public Keyword getByName(String keyword) {
        Keyword result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(KeywordEntity.getByKeyword);
            query.setParameter("keyword", keyword);
            List<KeywordEntity> list = query.list();
            if(list != null && list.size() > 0)
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), Keyword.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }

        return result;
    }

    @Override
    public Keyword update(Keyword keyword) {
        Keyword result = null;
        if(keyword != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                KeywordEntity entity = DozerMapperFactory.getDozerBeanMapper().map(keyword, KeywordEntity.class);
                entity = (KeywordEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Keyword.class);
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
        return result;
    }

}
