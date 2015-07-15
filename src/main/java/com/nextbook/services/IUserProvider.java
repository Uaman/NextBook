package com.nextbook.services;

import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:27 PM
 */
public interface IUserProvider {

    User getById(int userId);

    boolean addUser(User user);

    List<User> getFromMax(int from, int max);

    User update(User user);


    List<User> getAll();
}
