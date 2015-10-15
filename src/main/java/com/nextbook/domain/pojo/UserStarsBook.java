package com.nextbook.domain.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/14/2015
 * Time: 10:04 PM
 */
public class UserStarsBook {

    private int id;

    private Book book;

    private User user;

    private float mark;

    public UserStarsBook(){}

    public UserStarsBook(User user, Book book, float mark){
        this.user = user;
        this.book = book;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

}
