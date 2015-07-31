package com.nextbook.services;

import com.nextbook.domain.pojo.SubCategory;
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
    List<SubCategory> getAll();

    SubCategory getById(int subCategoryId);
}
