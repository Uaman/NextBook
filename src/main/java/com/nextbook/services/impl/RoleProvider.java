package com.nextbook.services.impl;

import com.nextbook.dao.IRoleDao;
import com.nextbook.domain.entities.RoleEntity;
import com.nextbook.services.IRoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 5:51 PM
 */
@Service
public class RoleProvider implements IRoleProvider{

    @Autowired
    private IRoleDao roleDao;

    @Transactional
    public List<RoleEntity> getAll(){
        return roleDao.getAll();
    }

}
