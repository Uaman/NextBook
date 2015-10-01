package com.nextbook.domain.pojo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Dima on 01.09.2015.
 */
public class Order {

    int id;

    OrderState orderState;

    boolean isPaid;

    User user;

    Date dateOfOrder;

    TypeOfPayment typeOfPayment;

    List<OrderedBook> orderedBooksList;

    String description;

    Delivering delivering;

    public List<OrderedBook> getOrderedBooksList() {
        return orderedBooksList;
    }

    public void setOrderedBooksList(List<OrderedBook> orderedBooksList) {
        this.orderedBooksList = orderedBooksList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderState=" + orderState +
                ", isPaid=" + isPaid +
                ", user=" + user +
                ", dateOfOrder=" + dateOfOrder +
                ", typeOfPaument=" + typeOfPayment +
                ", description='" + description + '\'' +
                ", delivering=" + delivering +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (isPaid != order.isPaid) return false;
        if (dateOfOrder != null ? !dateOfOrder.equals(order.dateOfOrder) : order.dateOfOrder != null) return false;
        if (delivering != null ? !delivering.equals(order.delivering) : order.delivering != null) return false;
        if (description != null ? !description.equals(order.description) : order.description != null) return false;
        if (orderState != null ? !orderState.equals(order.orderState) : order.orderState != null) return false;
        if (typeOfPayment != null ? !typeOfPayment.equals(order.typeOfPayment) : order.typeOfPayment != null)
            return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateOfOrder != null ? dateOfOrder.hashCode() : 0);
        result = 31 * result + (typeOfPayment != null ? typeOfPayment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (delivering != null ? delivering.hashCode() : 0);
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public TypeOfPayment getTypeOfPaument() {
        return typeOfPayment;
    }

    public void setTypeOfPaument(TypeOfPayment typeOfPaument) {
        this.typeOfPayment = typeOfPaument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Delivering getDelivering() {
        return delivering;
    }

    public void setDelivering(Delivering delivering) {
        this.delivering = delivering;
    }
}
