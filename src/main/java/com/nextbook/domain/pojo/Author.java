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

    //private List<Book> books = new ArrayList<Book>();

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

    /*public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != author.id) return false;
        //if (books != null ? !books.equals(author.books) : author.books != null) return false;
        if (firstNameEn != null ? !firstNameEn.equals(author.firstNameEn) : author.firstNameEn != null) return false;
        if (firstNameRu != null ? !firstNameRu.equals(author.firstNameRu) : author.firstNameRu != null) return false;
        if (firstNameUa != null ? !firstNameUa.equals(author.firstNameUa) : author.firstNameUa != null) return false;
        if (lastNameEn != null ? !lastNameEn.equals(author.lastNameEn) : author.lastNameEn != null) return false;
        if (lastNameRu != null ? !lastNameRu.equals(author.lastNameRu) : author.lastNameRu != null) return false;
        if (lastNameUa != null ? !lastNameUa.equals(author.lastNameUa) : author.lastNameUa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstNameUa != null ? firstNameUa.hashCode() : 0);
        result = 31 * result + (lastNameUa != null ? lastNameUa.hashCode() : 0);
        result = 31 * result + (firstNameEn != null ? firstNameEn.hashCode() : 0);
        result = 31 * result + (lastNameEn != null ? lastNameEn.hashCode() : 0);
        result = 31 * result + (firstNameRu != null ? firstNameRu.hashCode() : 0);
        result = 31 * result + (lastNameRu != null ? lastNameRu.hashCode() : 0);
        //result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}
