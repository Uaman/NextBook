package com.nextbook.services.impl;

import com.nextbook.dao.ISubCategoryDao;
import com.nextbook.dao.impl.SubCategoryDao;
import com.nextbook.domain.pojo.SubCategory;
import com.nextbook.services.ISubCategoryProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:04 PM
 */
@Service
public class SubCategoryProvider implements ISubCategoryProvider{

    private ISubCategoryDao subCategoryDao = new SubCategoryDao();

    @Override
    public List<SubCategory> getAll() {
        return subCategoryDao.getAll();
    }
}
