package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IFavoritesDao;
import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Stacy on 10/11/15.
 */
@Repository
public class FavoritesDao implements IFavoritesDao {

    @Inject
    private Dao baseDao;

    @Transactional
    public FavoritesEntity addToUserFavorites(FavoritesEntity favorite) {
        return baseDao.attachWithMerge(favorite);
    }

    @Transactional
    public boolean isFavorite(final int userId, final int bookId) {
        List<FavoritesEntity> result =
                baseDao.executeNamedQueryWithParams(
                        FavoritesEntity.class,
                        FavoritesEntity.getByUserAndBook,
                        new HashMap<String, Object>(){{
                            put("bookId", bookId);
                            put("userId", userId);
                        }});
        return !(result == null || result.isEmpty());
    }

    @Transactional
    public boolean deleteFromUserFavorites(final int userId, final int bookId) {
        return baseDao.deleteByNamedQueryWithParams(
                FavoritesEntity.class,
                FavoritesEntity.getByUserAndBook,
                new HashMap<String, Object>() {{
                    put("bookId", bookId);
                    put("userId", userId);
                }});
    }

    @Transactional
    public List<FavoritesEntity> getAllFavorites(final UserEntity user) {
        List<FavoritesEntity> result =
                baseDao.executeNamedQueryWithParams(
                        FavoritesEntity.class,
                        FavoritesEntity.getAllFavorites,
                        new HashMap<String, Object>(){{
                            put("userId", user.getId());
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Transactional
    public boolean hasFavorites(UserEntity user) {
        return getQuantityOfUserFavorites(user) != 0;
    }

    @Transactional
    public int getQuantityOfUserFavorites(final UserEntity user){
        int result =
                baseDao.executeCountNamedQueryWithParams(
                        UserEntity.class,
                        FavoritesEntity.getFavoritesCount,
                        new HashMap<String, Object>() {{
                            put("userId", user.getId());
                        }});
        return result;
    }
}
