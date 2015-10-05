package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.Author;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/12/2015
 * Time: 9:23 AM
 */
public class AuthorPreview {

    private int id;

    private String name;

    public AuthorPreview(){

    }

    public AuthorPreview(int id, String name){
        this.id = id;
        this.name = name;
    }

    public AuthorPreview(Author a, Locale locale) {
        this.id = a.getId();
        if (locale.getLanguage().equals("uk")) {
            this.name = a.getFirstNameUa() + " " +a.getLastNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = a.getFirstNameRu() + " " +a.getLastNameRu();
        } else {
            this.name = a.getFirstNameEn() + " " +a.getLastNameEn();
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
