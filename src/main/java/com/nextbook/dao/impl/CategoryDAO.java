package com.nextbook.dao.impl;

import com.nextbook.dao.ICategoryDAO;
import com.nextbook.domain.entities.CategoryEntity;
import com.nextbook.domain.entities.SubCategoryEntity;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.pojo.SubCategory;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 26.09.2015.
 */
@Repository
public class CategoryDAO implements ICategoryDAO {

    @Override
    public List<Category> getAll() {
        List<Category> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(CategoryEntity.getAll);
            List<CategoryEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Category>();
                for (CategoryEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Category temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Category.class);
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
