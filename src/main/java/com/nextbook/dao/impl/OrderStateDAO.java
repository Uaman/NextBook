package com.nextbook.dao.impl;

import com.nextbook.dao.IOrderStateDao;
import com.nextbook.domain.entities.OrderStateEntity;
import com.nextbook.domain.pojo.OrderState;
import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Polomani on 31.10.2015.
 */
@Repository
public class OrderStateDAO implements IOrderStateDao{

    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public OrderState updateOrderState(OrderState orderState) {
        OrderState result = null;
        if(orderState != null) {
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                OrderStateEntity entity = dozerBeanMapper.map(orderState, OrderStateEntity.class);
                session.saveOrUpdate(entity);
                entity = (OrderStateEntity) session.merge(entity);
                result = dozerBeanMapper.map(entity, OrderState.class);
                session.getTransaction().commit();
            } catch (Exception e) {
                if(session != null && session.getTransaction().isActive())
                    session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return result;
    }

    @Override
    public OrderState getOrderStateByName(String name) {
        OrderState result = null;
        Session session = sessionFactory.openSession();

        try{
            if(name != null && name.length()>0){
                Query query = session.createQuery("SELECT orderState FROM OrderStateEntity orderState WHERE orderState.stateOfTheOrder=:name");
                query.setParameter("name", name);
                List<OrderStateEntity> entities = query.list();
                result = dozerBeanMapper.map(entities.get(0), OrderState.class);
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
