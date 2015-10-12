package com.nextbook.services.impl;

import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.pojo.Favorites;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IFavoritesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Stacy on 10/10/15.
 */
@Service
public class FavoritesProvider implements IFavoritesProvider {
    @Autowired
    private IFavoritesDao favoriteDao;
    @Override
    public Favorites addToUserFavorites(Favorites favorite) {
        return favoriteDao.addToUserFavorites(favorite);
    }

    @Override
    public boolean deleteFromUserFavorites(int userId, int bookId) {
        return favoriteDao.deleteFromUserFavorites(userId,bookId);
    }

    @Override
    public List<Favorites> getAllFavorites(User user) {
        return favoriteDao.getAllFavorites(user);
    }

    @Override
    public boolean isFavorite(int userId, int bookId) {
        return favoriteDao.isFavorite(userId, bookId);
    }

}
