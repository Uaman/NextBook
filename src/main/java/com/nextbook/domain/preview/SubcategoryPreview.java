package com.nextbook.domain.preview;

import com.nextbook.domain.entities.SubCategoryEntity;

import java.util.Locale;

/**
 * Created by Polomani on 26.09.2015.
 */
public class SubcategoryPreview {

    private int id;

    private String name;
    private String link;

    public SubcategoryPreview(SubCategoryEntity s, Locale locale) {
        this.id = s.getId();
        if (locale.getLanguage().equals("uk")) {
            this.name = s.getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = s.getNameRu();
        } else {
            this.name = s.getNameEn();
        }
        link = s.getNameEn() == null ? id+"" : s.getNameEn().toLowerCase().replaceAll(" ","_");
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
