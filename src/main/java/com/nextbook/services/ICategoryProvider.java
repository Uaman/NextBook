package com.nextbook.services;

import com.nextbook.domain.entities.CategoryEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by Polomani on 26.09.2015.
 */
public interface ICategoryProvider {

    CategoryEntity getById(int id);

    public Set<CategoryEntity> getAll();

    CategoryEntity getByLink(String link);
}
