package com.nextbook.dao.impl;

import com.nextbook.dao.ISubCategoryDao;
import com.nextbook.domain.entities.SubCategoryEntity;
import com.nextbook.domain.pojo.SubCategory;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:06 PM
 */
public class SubCategoryDao implements ISubCategoryDao{

    @Override
    public List<SubCategory> getAll() {
        List<SubCategory> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(SubCategoryEntity.getAll);
            List<SubCategoryEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<SubCategory>();
                for (SubCategoryEntity entity : entities) {
                    if (entity != null) {
                        try {
                            SubCategory temp = DozerMapperFactory.getDozerBeanMapper().map(entity, SubCategory.class);
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

}
