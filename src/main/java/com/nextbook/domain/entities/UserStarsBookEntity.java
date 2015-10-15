package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/14/2015
 * Time: 10:02 PM
 */

@Entity
@Table(name = "user_vote_for_book")
public class UserStarsBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName="ID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName="ID")
    private UserEntity user;

    @Column(name = "MARK")
    private float mark;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
