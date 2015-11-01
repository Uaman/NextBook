package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.ICategoryDAO;
import com.nextbook.domain.entities.CategoryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Polomani on 26.09.2015.
 */
@Service
@Transactional
public class CategoryDAO implements ICategoryDAO {

    @Inject
    private Dao baseDao;

    @Override
    public CategoryEntity getById(int id) {
        return baseDao.getById(CategoryEntity.class, id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Set<CategoryEntity> getAll(){
        try {
            return new HashSet<CategoryEntity>(baseDao.getAll(CategoryEntity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CategoryEntity getByLink(final String link) {
        List<CategoryEntity> result =
                baseDao.executeNamedQueryWithParams(
                CategoryEntity.class,
                CategoryEntity.getByLink,
                new HashMap<String, Object>(){{
                    put("link",link);
                }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }
}
