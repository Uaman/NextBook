package com.nextbook.services.impl;

import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IFavoritesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Stacy on 10/10/15.
 */
@Service
public class FavoritesProvider implements IFavoritesProvider {
    @Autowired
    private IFavoritesDao favoriteDao;
    @Override
    public boolean addToUserFavorites(User user, Book book) {
        return favoriteDao.addToUserFavorites(user,book);
    }

    @Override
    public boolean deleteFromUserFavorites(User user, Book book) {
        return favoriteDao.deleteFromUserFavorites(user,book);
    }

    @Override
    public Set<Book> getAllFavorites(User user) {
        return favoriteDao.getAllFavorites(user);
    }
}
