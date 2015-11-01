package com.nextbook.dao;

import com.nextbook.domain.entities.SubCategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:05 PM
 */
@Repository
public interface ISubCategoryDao {
    List<SubCategoryEntity> getAll();
    List<SubCategoryEntity> getAllByCategoryId(int categoryId);
    SubCategoryEntity getById(int subCategoryId);
    SubCategoryEntity getByLink(String link);
}
