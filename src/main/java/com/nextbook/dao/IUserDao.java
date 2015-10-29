package com.nextbook.dao;

import com.nextbook.domain.criterion.UserCriterion;
import com.nextbook.domain.entities.UserEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:57 PM
 */
public interface IUserDao {

    UserEntity getById(int userId);

    UserEntity update(UserEntity user);

    /**
     * pass (0, 0) to get all
     * @param from - start from
     * @param max - max number
     * @return
     */
    List<UserEntity> getAll(int from, int max);

    boolean delete(int userId);

    public List<UserEntity> getUsersByCriterion(UserCriterion criterion);

    public UserEntity getUserByEmail(String email);
}
