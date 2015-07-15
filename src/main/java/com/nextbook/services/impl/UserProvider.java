package com.nextbook.services.impl;

import com.nextbook.dao.IUserDao;
import com.nextbook.dao.impl.UserDAO;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:28 PM
 */
public class UserProvider implements IUserProvider{

    private IUserDao userDao = new UserDAO();

    @Override
    public User getById(int userId) {
        return userDao.getById(userId);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.add(user);
    }

    @Override
    public List<User> getFromMax(int from, int max) {
        return userDao.getAll(from, max);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll(0, 0);
    }
}
