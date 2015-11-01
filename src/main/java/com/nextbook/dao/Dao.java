package com.nextbook.dao;

import com.nextbook.dao.base.objects.Getable;
import com.nextbook.dao.base.objects.GetableById;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Eugene on 08.10.2015.
 */
public interface Dao {

    <T extends Getable> List<T> getAll(Class<T> tClass) throws Exception ;

    <T extends GetableById> T getById(Class<T> tClass, int id);

    <T> List<T>  executeNamedQueryWithParams
            (Class<T> categoryEntityClass, String getByLink, HashMap<String, Object> link);

    <T> List<T> executeQueryWithParams
            (Class<T> categoryEntityClass, String queryString, HashMap<String, Object> params);

    <T extends GetableById> List<T>  executeNamedQueryWithParams
            (Class<T> userEntityClass, String getAllUsers, int from, int max);

    void attachWithUpdate(GetableById object);

    <T extends GetableById> T attachWithMerge(T object);

    <T extends GetableById> boolean deleteByNamedQueryWithParams
            (Class<T> tClass, String query, HashMap<String, Object> params);

    <T extends GetableById> int executeCountNamedQueryWithParams(Class<T> entityClass, String query, HashMap<String, Object> params);

    <T extends GetableById> boolean deleteById(Class<T> tClass, int id);

}
