package com.nextbook.dao;

import com.nextbook.domain.entities.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 5:52 PM
 */
@Repository
public interface IRoleDao {

    List<RoleEntity> getAll();

}
