package com.nextbook.services.impl;

import com.nextbook.dao.ICategoryDAO;
import com.nextbook.domain.entities.CategoryEntity;
import com.nextbook.services.ICategoryProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by Polomani on 26.09.2015.
 */
@Service
public class CategoryProvider implements ICategoryProvider{
    @Inject
    private ICategoryDAO categoryDao;

    @Transactional
    public CategoryEntity getById(int id) {
        return categoryDao.getById(id);
    }

    public Set<CategoryEntity> getAll() {
        return categoryDao.getAll();
    }

    @Transactional
    public CategoryEntity getByLink(String link) {
        return categoryDao.getByLink(link);
    }
}
