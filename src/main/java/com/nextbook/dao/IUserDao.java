package com.nextbook.dao;

import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:57 PM
 */
public interface IUserDao {

    User getById(int userId);

    User update(User user);

    boolean add(User user);

    /**
     * pass (0, 0) to get all
     * @param from - start from
     * @param max - max number
     * @return
     */
    List<User> getAll(int from, int max);

    boolean delete(int userId);
}
