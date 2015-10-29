package com.nextbook.services.impl;

import com.nextbook.dao.ICategoryDAO;
import com.nextbook.domain.entities.CategoryEntity;
import com.nextbook.services.ICategoryProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Polomani on 26.09.2015.
 */
@Service
public class CategoryProvider implements ICategoryProvider{
    @Inject
    private ICategoryDAO categoryDao;

    @Override
    public CategoryEntity getById(int id) {
        return categoryDao.getById(id);
    }

    public List<CategoryEntity> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public CategoryEntity getByLink(String link) {
        return categoryDao.getByLink(link);
    }
}
