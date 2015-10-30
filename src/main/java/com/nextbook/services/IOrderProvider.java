package com.nextbook.services;

import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/1/2015
 * Time: 1:46 PM
 */
public interface IOrderProvider {

    /**
     *
     * @param orderId - id of order
     * @return Order if exist order with id
     */
    OrderEntity getById(int orderId);

    /**
     *
     * @param currentOrder - order to be updated
     * @return updated order
     */
    OrderEntity updateOrder(OrderEntity currentOrder);

    /**
     *
     * @param orderId - id of order to be deleted
     * @return success
     */
    boolean delete(int orderId);

    /**
     *
     * @param currentUser - get all orders for this user
     * @return list of orders
     */
    List<OrderEntity> getOrdersForUser(UserEntity currentUser);

    /**
     *
     * @param user
     * @param book
     * @return return order for user and book if exist
     */
    OrderEntity getOrderByUserAndBook(UserEntity user, BookEntity book);

}
