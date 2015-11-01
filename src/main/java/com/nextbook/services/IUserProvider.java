package com.nextbook.services;

import com.nextbook.domain.criterion.UserCriterion;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.exceptions.EmailAlreadyExistsException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:27 PM
 */
public interface IUserProvider {

    UserEntity getById(int userId);

    List<UserEntity> getFromMax(int from, int max);

    UserEntity registerNewUser(UserEntity user) throws EmailAlreadyExistsException;

    UserEntity update(UserEntity user);

    List<UserEntity> getAll();

    boolean delete(int userId);

    List<UserEntity> getUsersByCriterion(UserCriterion criterion);

    public UserEntity getUserByEmail(String email);
}
