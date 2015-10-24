package com.nextbook.domain.request;

/**
 * Created by oleh on 24.10.2015.
 */
public class CatalogRequest {

    private int category;
    private int subCategory;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

}
