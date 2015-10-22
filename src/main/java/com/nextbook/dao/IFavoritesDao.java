package com.nextbook.dao;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Favorites;
import com.nextbook.domain.pojo.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Stacy on 10/9/15.
 */
public interface IFavoritesDao {
    /**
     *
     * @param favorite
     * @return
     */
    Favorites addToUserFavorites(Favorites favorite);

    /**
     *
     * @param userId
     * @param bookId
     * @return true if book is present in user favorites
     */
    boolean isFavorite(int userId, int bookId);

    /**
     *
     * @param userId
     * @param bookId
     * @return
     */
    boolean deleteFromUserFavorites(int userId, int bookId);
    List<Favorites> getAllFavorites(User user);
    boolean hasFavorites(User user);

    /**
     *
     * @param user
     * @return int - number of books in user`s favorites
     */
    int getQuantityOfUserFavorites(User user);
}
