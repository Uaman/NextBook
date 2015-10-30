package com.nextbook.services.impl;

import com.nextbook.dao.IOrderDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.services.IOrderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/1/2015
 * Time: 2:28 PM
 */

@Service
public class OrderProvider implements IOrderProvider{

    @Autowired
    private IOrderDao orderDao;

    @Transactional
    public OrderEntity getById(int orderId) {
        return orderDao.getById(orderId);
    }

    @Transactional
    public OrderEntity updateOrder(OrderEntity currentOrder) {
        return orderDao.updateOrder(currentOrder);
    }

    @Transactional
    public boolean delete(int orderId) {
        return orderDao.delete(orderId);
    }

    @Transactional
    public List<OrderEntity> getOrdersForUser(UserEntity user) {
        if(user == null)
            return null;
        return orderDao.getOrdersForUser(user);
    }

    @Transactional
    public OrderEntity getOrderByUserAndBook(UserEntity user, BookEntity book) {
        if(user == null || book == null)
            return null;
        return orderDao.getOrderByUserAndBook(user, book);
    }
}
