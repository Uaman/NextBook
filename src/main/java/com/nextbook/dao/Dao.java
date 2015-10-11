package com.nextbook.dao;

import com.nextbook.dao.base.objects.BaseDataObject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Eugene on 08.10.2015.
 */
public interface Dao {

    <T extends BaseDataObject> List<T> getAll(Session session, Class<T> tClass) throws NoSuchFieldException, IllegalAccessException;
}
