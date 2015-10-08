package com.nextbook.dao.impl.base;

import com.nextbook.dao.Dao;
import com.nextbook.dao.base.objects.BaseDataObject;
import com.nextbook.domain.entities.SubCategoryEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eugene on 08.10.2015.
 */
@Repository
public class DaoImpl implements Dao{

    @Override
    public <T extends BaseDataObject> List<T> getAll(Session session, Class<T> tClass) throws NoSuchFieldException, IllegalAccessException {
        String queryString = tClass.getField("getAll").get(null).toString();
        Query query = session.getNamedQuery(queryString);
        return query.list();
    }
}
