package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Polomani on 26.09.2015.
 */
public class BookPreview {

    private int id;

    private String name;

    private String subCategory;

    private int pages;

    private boolean eighteenPlus;

    private int yearOfPublication;

    private PublisherPreview publisher;

    private String description;

    private List<AuthorPreview> authors;

    private List<Comment> comments;

    public BookPreview(Book b, Locale locale) {
        this.id = b.getId();
        this.yearOfPublication = b.getYearOfPublication();
        this.pages = b.getNumberOfPages();
        this.eighteenPlus = b.isEighteenPlus();
        if (locale.getLanguage().equals("uk")) {
            this.name = b.getUaName();
            this.description = b.getDescriptionUa();
            this.subCategory = b.getSubCategory().getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = b.getRuName();
            this.description = b.getDescriptionRu();
            this.subCategory = b.getSubCategory().getNameRu();
        } else {
            this.name = b.getEnName();
            this.description = b.getDescriptionEn();
            this.subCategory = b.getSubCategory().getNameEn();
        }
        this.authors = new ArrayList<AuthorPreview>();
        for (Author a:b.getAuthors())
            this.authors.add(new AuthorPreview(a, locale));
        this.publisher = new PublisherPreview(b.getPublisher(), locale);
        this.comments = b.getComments();
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public PublisherPreview getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherPreview publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AuthorPreview> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorPreview> authors) {
        this.authors = authors;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
