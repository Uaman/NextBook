package com.nextbook.domain.entities;

import com.nextbook.dao.base.objects.GetableById;

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
@NamedQueries({
        @NamedQuery(name = AuthorEntity.getById, query = "SELECT author FROM AuthorEntity author WHERE author.id=:id"),
        @NamedQuery(name = AuthorEntity.getAllUsers, query = "SELECT author FROM AuthorEntity author"),
        @NamedQuery(name = AuthorEntity.getByFirstAndLastName, query = "SELECT author FROM AuthorEntity author WHERE (author.firstNameUa=:fName AND author.lastNameUa=:lName) " +
                "OR (author.firstNameEn=:fName AND author.lastNameEn=:lName) OR (author.firstNameRu=:fName AND author.lastNameRu=:lName)")
})

public class AuthorEntity implements GetableById{

    public static final String getById = "getAuthorById";
    public static final String getAllUsers = "getAllAuthors";
    public static final String getByFirstAndLastName = "getAuthorByFirstAndLastName";

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

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookAuthorEntity> bookToAuthor = new ArrayList<BookAuthorEntity>();

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

    public List<BookAuthorEntity> getBookToAuthor() {
        return bookToAuthor;
    }

    public void setBookToAuthor(List<BookAuthorEntity> bookToAuthor) {
        this.bookToAuthor = bookToAuthor;
    }
}

