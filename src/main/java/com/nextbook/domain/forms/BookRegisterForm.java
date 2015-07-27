package com.nextbook.domain.forms;

import com.nextbook.domain.enums.BookTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 9:55 PM
 */
public class BookRegisterForm {

    private int bookId;

    private String isbn;

    private String uaName;

    private String enName;

    private String ruName;

    private boolean eighteenPlus;

    private int yearOfPublication;

    private String language;

    private BookTypeEnum typeOfBook;

    private int numberOfPages;

    private String descriptionUa;

    private String descriptionEn;

    private String descriptionRu;

    //private int numberOfImagesForCover;

    //private int numberOfImagesInGallery;

    private List<String> keywords = new ArrayList<String>();

    private String author;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUaName() {
        return uaName;
    }

    public void setUaName(String uaName) {
        this.uaName = uaName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BookTypeEnum getTypeOfBook() {
        return typeOfBook;
    }

    public void setTypeOfBook(BookTypeEnum typeOfBook) {
        this.typeOfBook = typeOfBook;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }
/*
    public int getNumberOfImagesForCover() {
        return numberOfImagesForCover;
    }

    public void setNumberOfImagesForCover(int numberOfImagesForCover) {
        this.numberOfImagesForCover = numberOfImagesForCover;
    }

    public int getNumberOfImagesInGallery() {
        return numberOfImagesInGallery;
    }

    public void setNumberOfImagesInGallery(int numberOfImagesInGallery) {
        this.numberOfImagesInGallery = numberOfImagesInGallery;
    }
*/
    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
