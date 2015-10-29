package com.nextbook.services.impl;

import com.nextbook.dao.IUserDao;
import com.nextbook.domain.criterion.UserCriterion;
import com.nextbook.domain.entities.RoleEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.exceptions.EmailAlreadyExistsException;
import com.nextbook.services.IUserProvider;
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
    public UserEntity getById(int userId) {
        return userDao.getById(userId);
    }

    @Override
    public List<UserEntity> getFromMax(int from, int max) {
        return userDao.getAll(from, max);
    }

    @Override
    public UserEntity registerNewUser(UserEntity user) throws EmailAlreadyExistsException{
        UserEntity userByEmail = getUserByEmail(user.getEmail());
        if(userByEmail != null)
            throw new EmailAlreadyExistsException();
        RoleEntity role = new RoleEntity();
        role.setId(USER_ROLE_ID);
        user.setRoleEntity(role);
        return update(user);
    }

    @Override
    public UserEntity update(UserEntity user) {
        return userDao.update(user);
    }

    @Override
    public List<UserEntity> getAll() {
        return userDao.getAll(0, 0);
    }

    @Override
    public boolean delete(int userId) {
        return userDao.delete(userId);
    }

    @Override
    public List<UserEntity> getUsersByCriterion(UserCriterion criterion) {
        return userDao.getUsersByCriterion(criterion);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public static final int USER_ROLE_ID = 1;
}
