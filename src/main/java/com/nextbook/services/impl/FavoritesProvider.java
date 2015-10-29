package com.nextbook.services.impl;

import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;

import com.nextbook.services.IFavoritesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Stacy on 10/10/15.
 */
@Service
public class FavoritesProvider implements IFavoritesProvider {

    @Autowired
    private IFavoritesDao favoriteDao;

    @Override
    public FavoritesEntity addToUserFavorites(FavoritesEntity favorite) {
        return favoriteDao.addToUserFavorites(favorite);
    }

    @Override
    public boolean deleteFromUserFavorites(int userId, int bookId) {
        return favoriteDao.deleteFromUserFavorites(userId,bookId);
    }

    @Override
    public List<FavoritesEntity> getAllFavorites(UserEntity user) {
        return favoriteDao.getAllFavorites(user);
    }

    @Override
    public boolean isFavorite(int userId, int bookId) {
        return favoriteDao.isFavorite(userId, bookId);
    }

    @Override
    public boolean hasFavorites(UserEntity user){
        return favoriteDao.hasFavorites(user);
    }
    @Override
    public int countOfUserFavorites(UserEntity user){
        return favoriteDao.getQuantityOfUserFavorites(user);
    }

}
