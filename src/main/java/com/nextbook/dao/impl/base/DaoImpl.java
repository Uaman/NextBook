package com.nextbook.dao.impl.base;

import com.nextbook.dao.Dao;
import com.nextbook.dao.base.objects.Getable;
import com.nextbook.dao.base.objects.GetableById;
import com.nextbook.dao.AttachingService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.TransactionRequiredException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eugene on 08.10.2015.
 */
@Repository
public class DaoImpl implements Dao, AttachingService {

    @Inject
    private SessionFactory sessionFactory;
    
    @Override
    public <T extends Getable> List<T> getAll(Class<T> tClass){
        return getSession().createCriteria(tClass).list();
    }
    @Override
    public <T extends GetableById> T getById(Class<T> tClass, int id){
        validateTransaction();
        return (T) getSession().load(tClass, id);
    }

    @Override
    public <T> List<T> executeNamedQueryWithParams
            (Class<T> categoryEntityClass, String getByLink, HashMap<String, Object> params) {
        validateTransaction();
        Query query = getSession().getNamedQuery(getByLink);
        for(String name : params.keySet()){
            query.setParameter(name, params.get(name));
        }
        return query.list();
    }

    @Override
    public <T extends GetableById> List<T> executeNamedQueryWithParams
            (Class<T> userEntityClass, String getAllUsers, int from, int max) {
        validateTransaction();
        Query query = getSession().getNamedQuery(getAllUsers);
        if(from > 0)
            query.setFirstResult(from);
        if(max > from)
            query.setMaxResults(max);
        return query.list();
    }

    @Override
    public void attachWithUpdate(GetableById object) {
        validateTransaction();
        getSession().update(object);
    }

    @Override
    public <T extends GetableById> T attachWithMerge(T object) {
        validateTransaction();
        return (T) getSession().merge(object);
    }

    @Override
    public <T extends GetableById> boolean deleteByNamedQueryWithParams(Class<T> tClass, String query, HashMap<String, Object> params) {
        validateTransaction();
        List<T> toDelete = executeNamedQueryWithParams(tClass,query, params);
        if(toDelete != null && !toDelete.isEmpty()) {
            for (T object : toDelete) {
                getSession().delete(object);
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public <T extends GetableById> int executeCountNamedQueryWithParams
            (Class<T> entityClass, String queryString, HashMap<String, Object> params) {
        validateTransaction();
        Query query = getSession().getNamedQuery(queryString);
        for(String name : params.keySet()){
            query.setParameter(name, params.get(name));
        }
        return ((Long) query.iterate().next()).intValue();
    }

    @Override
    public <T extends GetableById> boolean deleteById(Class<T> tClass, int id) {
        validateTransaction();
        T toDelete = getById(tClass, id);
        getSession().delete(toDelete);
        return true;
    }

    private void validateTransaction(){
        if(getSession().getTransaction() == null || !getSession().getTransaction().isActive()){
            throw new TransactionRequiredException();
        }
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
