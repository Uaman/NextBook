package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IRoleDao;
import com.nextbook.domain.entities.RoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 5:53 PM
 */
@Repository
public class RoleDao implements IRoleDao{

    @Inject
    private Dao baseDao;

    @Transactional
    public List<RoleEntity> getAll() {
        return baseDao.getAll(RoleEntity.class);
    }
}
