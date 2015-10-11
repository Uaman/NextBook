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
     * @return
     */
boolean deleteFromUserFavorites(int userId, int bookId);
    List<Favorites> getAllFavorites(User user);
}
