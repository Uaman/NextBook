package com.nextbook.dao;

import com.nextbook.domain.pojo.Order;
import com.nextbook.domain.pojo.User;

import java.util.List;

/**
 * Created by Dima on 18.09.2015.
 */
public interface IOrderDao {

    Order getById(int orderId);

    Order updateOrder(Order currentOrder);

    boolean delete(int orderId);

    public List<Order> getOrdersForUser(User currentUser);
}
