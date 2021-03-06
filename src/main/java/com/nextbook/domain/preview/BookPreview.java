package com.nextbook.domain.preview;

import com.nextbook.domain.entities.BookAuthorEntity;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.CommentEntity;

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

    private List<CommentPreview> comments;

    private float rating;

    private int voted;

    private boolean favorite;

    public BookPreview(BookEntity b, Locale locale) {
        this.id = b.getId();
        this.yearOfPublication = b.getYearOfPublication();
        this.pages = b.getNumberOfPages();
        this.eighteenPlus = b.isEighteenPlus();
        if (locale.getLanguage().equals("uk")) {
            this.name = b.getUaName();
            this.description = b.getDescriptionUa();
            this.subCategory = b.getSubCategoryEntity().getNameUa();
        } else if (locale.getLanguage().equals("ru")) {
            this.name = b.getRuName();
            this.description = b.getDescriptionRu();
            this.subCategory = b.getSubCategoryEntity().getNameRu();
        } else {
            this.name = b.getEnName();
            this.description = b.getDescriptionEn();
            this.subCategory = b.getSubCategoryEntity().getNameEn();
        }
        this.authors = new ArrayList<AuthorPreview>();
        for (BookAuthorEntity a:b.getBookToAuthor())
            this.authors.add(new AuthorPreview(a.getAuthor(), locale));
        this.publisher = new PublisherPreview(b.getPublisherEntity(), locale);
        this.comments = new ArrayList<CommentPreview>();
        for(CommentEntity comment : b.getComments())
            this.comments.add(new CommentPreview(comment));

        this.rating = b.getRating();
        this.voted = b.getVoted();
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

    public List<CommentPreview> getComments() {
        return comments;
    }

    public void setComments(List<CommentPreview> comments) {
        this.comments = comments;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getVoted() {
        return voted;
    }

    public void setVoted(int voted) {
        this.voted = voted;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
