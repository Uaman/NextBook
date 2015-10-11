package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.pojo.SubCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Polomani on 26.09.2015.
 */
public class CategoryPreview {

    private int id;

    private String name;
    private String link;

    private List<SubcategoryPreview> subcategories;

    public CategoryPreview(Category c, Locale locale) {
        this.id = c.getId();
        if (locale.getLanguage().equals("uk")) {
            this.name = c.getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = c.getNameRu();
        } else {
            this.name = c.getNameEn();
        }
        link = c.getNameEn().toLowerCase().replaceAll(" ", "_");
        subcategories = new ArrayList<SubcategoryPreview>();
        List<SubCategory> ss = c.getSubCategory();
        for (SubCategory s:c.getSubCategory()) {
            subcategories.add(new SubcategoryPreview(s, locale));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<SubcategoryPreview> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryPreview> subcategories) {
        this.subcategories = subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
