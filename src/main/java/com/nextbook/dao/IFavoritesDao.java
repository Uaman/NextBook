package com.nextbook.dao;

import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;

import java.util.List;

/**
 * Created by Stacy on 10/9/15.
 */
public interface IFavoritesDao {
    /**
     *
     * @param favorite
     * @return
     */
    FavoritesEntity addToUserFavorites(FavoritesEntity favorite);

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

    List<FavoritesEntity> getAllFavorites(UserEntity user);

    boolean hasFavorites(UserEntity user);

    /**
     *
     * @param user
     * @return int - number of books in user`s favorites
     */
    int getQuantityOfUserFavorites(UserEntity user);
}
