package com.nextbook.services.impl;

import com.nextbook.dao.ISubCategoryDao;
import com.nextbook.domain.entities.SubCategoryEntity;
import com.nextbook.services.ISubCategoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:04 PM
 */
@Service
public class SubCategoryProvider implements ISubCategoryProvider{

    @Autowired
    private ISubCategoryDao subCategoryDao;

    @Transactional
    public List<SubCategoryEntity> getAll(){
        return subCategoryDao.getAll();
    }

    @Transactional
    public SubCategoryEntity getById(int subCategoryId) {
        return subCategoryDao.getById(subCategoryId);
    }

    @Transactional
    public List<SubCategoryEntity> getAllByCategoryId(int categoryId) {
        return subCategoryDao.getAllByCategoryId(categoryId);
    }

    @Transactional
    public SubCategoryEntity getByLink(String link) {
        return subCategoryDao.getByLink(link);
    }
}
