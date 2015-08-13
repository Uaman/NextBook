package com.nextbook.domain.info;

import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.pojo.Author;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.BookAuthor;
import com.nextbook.domain.pojo.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 29.07.2015.
 */
public class BookMainInfo {

    private int id;

    private String isbn;

    private String uaName;

    private String enName;

    private String ruName;

    private String subCategory;

    private boolean eighteenPlus;

    private int yearOfPublication;

    private String publisher;

    private String language;

    private BookTypeEnum typeOfBook;

    private int numberOfPages;

    private String descriptionUa;

    private String descriptionEn;

    private String descriptionRu;

    private String link;

    private String linkToStorage;

    private int numberOfImagesForCover;

    private int numberOfImagesInGallery;

    private String author;

    private List<String> keywords = new ArrayList<String>();

    public BookMainInfo() {

    }

    public BookMainInfo(Book book, String lang) {
        id = book.getId();
        isbn = book.getIsbn();
        uaName = book.getUaName();
        enName = book.getEnName();
        ruName = book.getRuName();
        if (book.getSubCategory()!=null)
            subCategory = book.getSubCategory().getNameUa();
        eighteenPlus = book.isEighteenPlus();
        yearOfPublication = book.getYearOfPublication();
        if (book.getPublisher()!=null)
            publisher = book.getPublisher().getNameUa();
        language = book.getLanguage();
        typeOfBook = book.getTypeOfBook();
        numberOfPages = book.getNumberOfPages();
        descriptionUa = book.getDescriptionUa();
        descriptionEn = book.getDescriptionEn();
        descriptionRu = book.getDescriptionRu();
        link = book.getLink();
        linkToStorage = book.getLinkToStorage();
        numberOfImagesForCover = book.getNumberOfImagesForCover();
        numberOfImagesInGallery = book.getNumberOfImagesInGallery();
        if (book.getBookToAuthor()!=null) {
            for(BookAuthor bookAuthor : book.getBookToAuthor()) {
                Author author = bookAuthor.getAuthor();
                String name;
                if (lang.equals("uk")) {
                    name = author.getFirstNameUa() + ' ' + author.getLastNameUa();
                } else if (lang.equals("ru")) {
                    name = author.getFirstNameRu() + ' ' + author.getLastNameRu();
                } else {
                    name = author.getFirstNameEn() + ' ' + author.getLastNameEn();
                }
                this.author += name;
            }
        }
        if (book.getKeywords()!=null)
            for (Keyword keyword:book.getKeywords())
                keywords.add(keyword.getKeyword());
    }

    @Override
    public String toString() {
        return "BookMainInfo{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", uaName='" + uaName + '\'' +
                ", enName='" + enName + '\'' +
                ", ruName='" + ruName + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", eighteenPlus=" + eighteenPlus +
                ", yearOfPublication=" + yearOfPublication +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                ", typeOfBook=" + typeOfBook +
                ", numberOfPages=" + numberOfPages +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionRu='" + descriptionRu + '\'' +
                ", link='" + link + '\'' +
                ", linkToStorage='" + linkToStorage + '\'' +
                ", numberOfImagesForCover=" + numberOfImagesForCover +
                ", numberOfImagesInGallery=" + numberOfImagesInGallery +
                ", author='" + author + '\'' +
                '}';
    }

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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkToStorage() {
        return linkToStorage;
    }

    public void setLinkToStorage(String linkToStorage) {
        this.linkToStorage = linkToStorage;
    }

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
}
