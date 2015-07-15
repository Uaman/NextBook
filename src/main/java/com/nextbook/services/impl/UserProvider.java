package com.nextbook.services.impl;

import com.nextbook.dao.IUserDao;
import com.nextbook.dao.impl.UserDAO;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/13/2015
 * Time: 10:28 PM
 */
@Service
public class UserProvider implements IUserProvider{

    @Inject
    private IUserDao userDao;

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

    @Override
    public boolean delete(int userId) {
        return userDao.delete(userId);
    }

    @Override
    public List<User> getUsersByCriterion(UserCriterion criterion) {
        return userDao.getUsersByCriterion(criterion);
    }
}
