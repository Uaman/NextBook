package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IOrderDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.OrderEntity;
import com.nextbook.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dima on 18.09.2015.
 */
@Repository
public class OrderDao implements IOrderDao {

    @Inject
    private Dao baseDao;

    @Override
    public OrderEntity getById(int orderId) {
        return baseDao.getById(OrderEntity.class, orderId);
    }

    @Override
    public OrderEntity updateOrder(OrderEntity currentOrder) {
        return baseDao.attachWithMerge(currentOrder);
    }

    @Override
    public boolean delete(int orderId) {
        return baseDao.deleteById(OrderEntity.class, orderId);
    }

    @Override
    public List<OrderEntity> getOrdersForUser(final UserEntity currentUser) {
        List<OrderEntity> result =
                baseDao.executeNamedQueryWithParams(
                        OrderEntity.class,
                        OrderEntity.getOrdersByUser,
                        new HashMap<String, Object>() {{
                            put("u_id", currentUser.getId());
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Override
    public OrderEntity getOrderByUserAndBook(final UserEntity user, final BookEntity book){
        List<Integer> result =
                baseDao.executeNamedQueryWithParams(
                        Integer.class,
                                "SELECT DISTINCT(ORDERS.ID)" +
                                " FROM ORDERS JOIN ORDERS_WITH_BOOK" +
                                " ON ORDERS.ID = ORDERS_WITH_BOOK.ID" +
                                " WHERE ORDERS.USER_ID=:user_id" +
                                " AND ORDERS_WITH_BOOK.BOOK_ID=:book_id",
                        new HashMap<String, Object>() {{
                            put("user_id", user.getId());
                            put("book_id", book.getId());
                        }});
        Integer id = (result == null || result.isEmpty()) ? null : result.get(0);
        if(id != null) {
            return getById(id);
        }
        return null;
    }

}
