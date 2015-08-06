package com.nextbook.domain.filters;

import com.nextbook.domain.enums.BookTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 27.07.2015.
 */
public class BookCriterion {

    private int from;

    private int max;

    private int id;

    private String isbn;

    private String name;

    private int subCategory;

    private String state;

    private int yearOfPublication;

    private String publisher;

    private String language;

    private String typeOfBook;

    private int numberOfPages;

    //private String descriptionUa;

    //private String descriptionEn;

    //private String descriptionRu;

    private List<String> keywords = new ArrayList<String>();

    private int authorId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getPublisher() {
        return '%'+publisher+'%';
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getTypeOfBookString() {
        return typeOfBook;
    }

    public BookTypeEnum getTypeOfBook() {
        return BookTypeEnum.valueOf(typeOfBook);
    }

    public void setTypeOfBook(String typeOfBook) {
        this.typeOfBook = typeOfBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isEighteenPlus () { return state.equalsIgnoreCase("eighteenPlus");}

    public String getName() {
        return '%'+name+'%';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getLanguage() {
        return '%'+language+'%';
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
