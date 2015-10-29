package com.nextbook.domain.entities;

import com.nextbook.dao.base.objects.GetableById;

import javax.persistence.*;

/**
 * Created by Stacy on 10/11/15.
 */
@Entity
@Table(name = "favorites")
@NamedQueries({
        @NamedQuery(name = FavoritesEntity.getAllFavorites, query = "SELECT favorite FROM FavoritesEntity favorite WHERE favorite.user.id=:userId"),
        @NamedQuery(name = FavoritesEntity.getByUserAndBook, query = "SELECT favorite FROM FavoritesEntity favorite WHERE favorite.book.id=:bookId AND favorite.user.id=:userId"),
        @NamedQuery(name = FavoritesEntity.getFavoritesCount, query = "SELECT COUNT(favorite) FROM FavoritesEntity favorite WHERE favorite.user.id=:userId")
})
public class FavoritesEntity implements GetableById{
    public static final String getAllFavorites = "getAllFavorites";
    public static final String getByUserAndBook = "getByUserAndBook";
    public static final String getFavoritesCount = "getFavoritesCount";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritesEntity)) return false;

        FavoritesEntity that = (FavoritesEntity) o;

        if (id != that.id) return false;
        if (!book.equals(that.book)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + book.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
