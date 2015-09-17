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

    byte[] isPaid;

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
                ", isPaid=" + Arrays.toString(isPaid) +
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

        if (getId() != order.getId()) return false;
        if (getOrderState() != null ? !getOrderState().equals(order.getOrderState()) : order.getOrderState() != null)
            return false;
        if (!Arrays.equals(getIsPaid(), order.getIsPaid())) return false;
        if (getUser() != null ? !getUser().equals(order.getUser()) : order.getUser() != null) return false;
        if (getDateOfOrder() != null ? !getDateOfOrder().equals(order.getDateOfOrder()) : order.getDateOfOrder() != null)
            return false;
        if (getTypeOfPaument() != null ? !getTypeOfPaument().equals(order.getTypeOfPaument()) : order.getTypeOfPaument() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(order.getDescription()) : order.getDescription() != null)
            return false;
        return !(getDelivering() != null ? !getDelivering().equals(order.getDelivering()) : order.getDelivering() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getOrderState() != null ? getOrderState().hashCode() : 0);
        result = 31 * result + (getIsPaid() != null ? Arrays.hashCode(getIsPaid()) : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getDateOfOrder() != null ? getDateOfOrder().hashCode() : 0);
        result = 31 * result + (getTypeOfPaument() != null ? getTypeOfPaument().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDelivering() != null ? getDelivering().hashCode() : 0);
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

    public byte[] getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(byte[] isPaid) {
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
