package com.nextbook.dao;

import com.nextbook.domain.pojo.SubCategory;
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
    List<SubCategory> getAll();
    List<SubCategory> getAllByCategoryId(int categoryId);
    SubCategory getById(int subCategoryId);

}
