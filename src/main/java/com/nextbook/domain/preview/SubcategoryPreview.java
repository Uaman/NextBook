package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.SubCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Polomani on 26.09.2015.
 */
public class SubcategoryPreview {

    private int id;

    private String name;

    public SubcategoryPreview(SubCategory s, Locale locale) {
        this.id = s.getId();
        if (locale.getLanguage().equals("uk")) {
            this.name = s.getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = s.getNameRu();
        } else {
            this.name = s.getNameEn();
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

    public void setName(String name) {
        this.name = name;
    }
}
