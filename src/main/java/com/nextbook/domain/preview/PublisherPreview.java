package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.Publisher;

import java.util.Locale;

/**
 * Created by Polomani on 26.09.2015.
 */
public class PublisherPreview {

    private int id;

    private String name;

    public PublisherPreview(Publisher publisher, Locale locale) {
        if (locale.getLanguage().equals("uk")) {
            this.name = publisher.getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = publisher.getNameRu();
        } else {
            this.name = publisher.getNameEn();
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
