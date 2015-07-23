package com.nextbook.services;

import com.nextbook.domain.pojo.SubCategory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 10:04 PM
 */
public interface ISubCategoryProvider {

    List<SubCategory> getAll();

}
