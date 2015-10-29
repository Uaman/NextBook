package com.nextbook.services;

import com.nextbook.domain.entities.SubCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:04 PM
 */
@Service
public interface ISubCategoryProvider {
    List<SubCategoryEntity> getAll();
    List<SubCategoryEntity> getAllByCategoryId(int categoryId);
    SubCategoryEntity getById(int subCategoryId);
    SubCategoryEntity getByLink(String link);
}
