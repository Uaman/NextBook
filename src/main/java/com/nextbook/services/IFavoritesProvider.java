package com.nextbook.services;

import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;

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
    FavoritesEntity addToUserFavorites(FavoritesEntity favorite);

    /**
     *
     * @param userId
     * @param bookId - of the book to remove
     * @return true if book was removed from favorites
     */
    boolean deleteFromUserFavorites(int userId, int bookId);
    List<FavoritesEntity> getAllFavorites(UserEntity user);
    boolean isFavorite(int userId, int bookId);
    boolean hasFavorites(UserEntity user);
    /**
     *
     * @param user
     * @return int - number of books in user`s favorites
     */
    int countOfUserFavorites(UserEntity user);
}
