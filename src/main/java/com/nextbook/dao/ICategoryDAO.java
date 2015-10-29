package com.nextbook.dao;

import com.nextbook.domain.entities.CategoryEntity;

import java.util.List;

/**
 * Created by Polomani on 26.09.2015.
 */
public interface ICategoryDAO {

    CategoryEntity getById(int id);

    List<CategoryEntity> getAll();

    CategoryEntity getByLink(String link);
}
