package com.nextbook.dao;

import com.nextbook.domain.entities.CategoryEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by Polomani on 26.09.2015.
 */
public interface ICategoryDAO {

    CategoryEntity getById(int id);

    Set<CategoryEntity> getAll();

    CategoryEntity getByLink(String link);
}
