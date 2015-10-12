package com.nextbook.dao;

import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.pojo.SubCategory;

import java.util.List;

/**
 * Created by Polomani on 26.09.2015.
 */
public interface ICategoryDAO {
    List<Category> getAll();
    Category getByLink(String link);
}
