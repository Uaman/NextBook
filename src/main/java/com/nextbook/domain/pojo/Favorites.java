package com.nextbook.domain.pojo;

/**
 * Created by Stacy on 10/11/15.
 */
public class Favorites {

    private int id;

    private Book book;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorites)) return false;

        Favorites favorites = (Favorites) o;

        if (id != favorites.id) return false;
        if (!book.equals(favorites.book)) return false;
        if (!user.equals(favorites.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + book.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
