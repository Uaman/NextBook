package com.nextbook.dao.impl;

import com.nextbook.dao.IOrderDao;
import com.nextbook.domain.entities.BookAuthorEntity;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.BookAuthor;
import com.nextbook.domain.pojo.Order;
import com.nextbook.domain.pojo.User;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dima on 18.09.2015.
 */
@Repository
public class OrderDao implements IOrderDao {
    @Override
    public Order getById(int orderId) {
        Order result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            OrderEntity entity = (OrderEntity) session.load(OrderEntity.class, orderId);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, Order.class);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public Order updateOrder(Order currentOrder) {
        Order result = null;
        if(currentOrder != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                OrderEntity entity = DozerMapperFactory.getDozerBeanMapper().map(currentOrder,
                        OrderEntity.class);
                entity = (OrderEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Order.class);
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
    public boolean delete(int orderId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            OrderEntity toDelete = (OrderEntity)session.load(UserEntity.class, orderId);
            if(toDelete != null){
                session.delete(toDelete);
            }
            session.getTransaction().commit();
            deleted = true;
        } catch (Exception e){
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return deleted;
    }

    @Override
    public List<Order> getOrdersForUser(User currentUser) {
        List<Order> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Query query = session.getNamedQuery(OrderEntity.getOrdersByUser);
            query.setParameter("u_id", currentUser.getId());
            List<OrderEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Order>();
                for(OrderEntity entity : list){
                    Order temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Order.class);
                    if(temp != null)
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
