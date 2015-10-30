package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.ISubCategoryDao;
import com.nextbook.domain.entities.SubCategoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:06 PM
 */
@Repository
@Transactional
public class SubCategoryDao implements ISubCategoryDao{

    @Inject
    private Dao baseDao;

    @Transactional
    public List<SubCategoryEntity> getAll(){
        return baseDao.getAll(SubCategoryEntity.class);
    }

    @Transactional
    public SubCategoryEntity getById(int subCategoryId) {
        return baseDao.getById(SubCategoryEntity.class, subCategoryId);
    }

    @Transactional
    public List<SubCategoryEntity> getAllByCategoryId(final int categoryId) {
        List<SubCategoryEntity> result =
                baseDao.executeNamedQueryWithParams(
                        SubCategoryEntity.class,
                        SubCategoryEntity.getAllByCategoryId,
                        new HashMap<String, Object>(){{
                            put("id", categoryId);
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public SubCategoryEntity getByLink(final String link) {
        List<SubCategoryEntity> result =
                baseDao.executeNamedQueryWithParams(
                        SubCategoryEntity.class,
                        SubCategoryEntity.getSubcategoryByLink,
                        new HashMap<String, Object>(){{
                            put("link", link);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }
}
