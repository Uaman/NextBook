package com.nextbook.services;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Order;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/1/2015
 * Time: 1:46 PM
 */
@Service
public interface IOrderProvider {

    /**
     *
     * @param orderId - id of order
     * @return Order if exist order with id
     */
    Order getById(int orderId);

    /**
     *
     * @param currentOrder - order to be updated
     * @return updated order
     */
    Order updateOrder(Order currentOrder);

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
    List<Order> getOrdersForUser(User currentUser);

    /**
     *
     * @param user
     * @param book
     * @return return order for user and book if exist
     */
    Order getOrderByUserAndBook(User user, Book book);

}
