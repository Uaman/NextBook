package com.nextbook.dao;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;

import java.util.Set;

/**
 * Created by Stacy on 10/9/15.
 */
public interface IFavoritesDao {
    /**
     *
     * @param user - of the user
     * @param book - of the book to add
     * @return true if book was added to favorites
     */
    boolean addToUserFavorites(User user, Book book);

    /**
     *
     * @param user
     * @param book - of the book to remove
     * @return true if book was removed from favorites
     */
    boolean deleteFromUserFavorites(User user, Book book);
    Set<Book> getAllFavorites(User user);
}
