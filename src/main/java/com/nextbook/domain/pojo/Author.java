package com.nextbook.domain.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:44 PM
 */
public class Author {

    private int id;

    private String firstNameUa;

    private String lastNameUa;

    private String firstNameEn;

    private String lastNameEn;

    private String firstNameRu;

    private String lastNameRu;

    private List<Book> books = new ArrayList<Book>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstNameUa() {
        return firstNameUa;
    }

    public void setFirstNameUa(String firstNameUa) {
        this.firstNameUa = firstNameUa;
    }

    public String getLastNameUa() {
        return lastNameUa;
    }

    public void setLastNameUa(String lastNameUa) {
        this.lastNameUa = lastNameUa;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getFirstNameRu() {
        return firstNameRu;
    }

    public void setFirstNameRu(String firstNameRu) {
        this.firstNameRu = firstNameRu;
    }

    public String getLastNameRu() {
        return lastNameRu;
    }

    public void setLastNameRu(String lastNameRu) {
        this.lastNameRu = lastNameRu;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
