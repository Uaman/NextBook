package com.nextbook.services;

import com.nextbook.domain.entities.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 5:51 PM
 */
public interface IRoleProvider {

    List<RoleEntity> getAll();

}
