package com.nextbook.dao;

import com.nextbook.domain.pojo.SubCategory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:05 PM
 */
public interface ISubCategoryDao {

    List<SubCategory> getAll();

}
