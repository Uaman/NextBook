package com.nextbook.services;

import com.nextbook.domain.pojo.Favorites;
import com.nextbook.domain.pojo.User;

import java.util.List;

/**
 * Created by Stacy on 10/10/15.
 */
public interface IFavoritesProvider {
    /**
     *
     * @param favorite - of the user
     * @return true if book was added to favorites
     */
    Favorites addToUserFavorites(Favorites favorite);

    /**
     *
     * @param userId
     * @param bookId - of the book to remove
     * @return true if book was removed from favorites
     */
    boolean deleteFromUserFavorites(int userId, int bookId);
    List<Favorites> getAllFavorites(User user);
    boolean isFavorite(int userId, int bookId);
    boolean hasFavorites(User user);
}
