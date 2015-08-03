package com.nextbook.dao.impl;

import com.nextbook.dao.IRoleDao;
import com.nextbook.domain.entities.RoleEntity;
import com.nextbook.domain.pojo.Role;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 5:53 PM
 */
@Repository
public class RoleDao implements IRoleDao{

    @Override
    public List<Role> getAll() {
        List<Role> result = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.getNamedQuery(RoleEntity.getAll);
            List<RoleEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Role>();
                for(RoleEntity roleEntity : list){
                    Role temp = DozerMapperFactory.getDozerBeanMapper().map(roleEntity, Role.class);
                    result.add(temp);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }

        return result;
    }
}
