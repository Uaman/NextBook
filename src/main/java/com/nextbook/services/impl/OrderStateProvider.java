package com.nextbook.services.impl;

import com.nextbook.dao.IOrderStateDao;
import com.nextbook.domain.pojo.OrderState;
import com.nextbook.services.IOrderStateProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Polomani on 31.10.2015.
 */
@Service
public class OrderStateProvider implements IOrderStateProvider {

    @Inject
    private IOrderStateDao orderStateDao;

    @Override
    public OrderState updateOrderState(OrderState orderState) {
        return orderStateDao.updateOrderState(orderState);
    }

    @Override
    public OrderState getOrderStateByName(String name) {
        return orderStateDao.getOrderStateByName(name);
    }
}
