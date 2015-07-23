package com.nextbook.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 6:14 PM
 */

@Entity
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRST_NAME_UA", nullable = false)
    private String firstNameUa;

    @Column(name = "LAST_NAME_UA")
    private String lastNameUa;

    @Column(name = "FIRST_NAME_EN")
    private String firstNameEn;

    @Column(name = "LAST_NAME_EN")
    private String lastNameEn;

    @Column(name = "FIRST_NAME_RU")
    private String firstNameRu;

    @Column(name = "LAST_NAME_RU")
    private String lastNameRu;

    @OneToMany
    @JoinTable(name = "authors_to_book", joinColumns = {@JoinColumn(name = "AUTHOR_ID")}, inverseJoinColumns = {@JoinColumn(name = "BOOK_ID")})
    private List<BookEntity> books = new ArrayList<BookEntity>();

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

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}

