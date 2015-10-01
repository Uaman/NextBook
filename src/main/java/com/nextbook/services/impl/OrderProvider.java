package com.nextbook.services.impl;

import com.nextbook.dao.IOrderDao;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Order;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IOrderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Order getById(int orderId) {
        return orderDao.getById(orderId);
    }

    @Override
    public Order updateOrder(Order currentOrder) {
        return orderDao.updateOrder(currentOrder);
    }

    @Override
    public boolean delete(int orderId) {
        return orderDao.delete(orderId);
    }

    @Override
    public List<Order> getOrdersForUser(User user) {
        if(user == null)
            return null;
        return orderDao.getOrdersForUser(user);
    }

    @Override
    public Order getOrderByUserAndBook(User user, Book book) {
        if(user == null || book == null)
            return null;
        return orderDao.getOrderByUserAndBook(user, book);
    }
}
