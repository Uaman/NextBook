package com.nextbook.dao;

import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.entities.UserEntity;

import java.util.List;

/**
 * Created by Dima on 18.09.2015.
 */
public interface IOrderDao {

    OrderEntity getById(int orderId);

    OrderEntity updateOrder(OrderEntity currentOrder);

    boolean delete(int orderId);

    List<OrderEntity> getOrdersForUser(UserEntity currentUser);

    OrderEntity getOrderByUserAndBook(UserEntity user, BookEntity book);
}
