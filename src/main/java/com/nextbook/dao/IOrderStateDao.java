package com.nextbook.dao;

import com.nextbook.domain.pojo.OrderState;

/**
 * Created by Polomani on 31.10.2015.
 */
public interface IOrderStateDao {

    public OrderState updateOrderState(OrderState orderState);

    public OrderState getOrderStateByName(String name);
}
