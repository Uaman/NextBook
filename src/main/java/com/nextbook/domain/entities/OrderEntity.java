package com.nextbook.domain.entities;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Dima on 03.09.2015.
 */

@Entity
@Table(name = "ORDERS")
@NamedQueries({
        @NamedQuery(name = OrderEntity.getAllOrders, query = "SELECT o FROM OrderEntity o"),
        @NamedQuery(name = OrderEntity.getOrdersByUser, query = "SELECT o FROM OrderEntity o WHERE o.user.id=:u_id")
})
public class OrderEntity{

    public static final String getAllOrders = "OrderEntity.getAllOrders";
    public static final String getOrdersByUser = "OrderEntity.getOrdersByUser";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "state_id")
    private OrderStateEntity orderState;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "date_of_order")
    private Date dateOfOrder;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "type_of_payment_id")
    private TypeOfPaymentEntity typeOfPayment;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivering_id")
    private TypeOfDeliveringEntity delivering;

    @OneToMany(mappedBy = "order")
    private Set<OrderedBookEntity> orderedBooksList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStateEntity getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEntity orderState) {
        this.orderState = orderState;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public TypeOfPaymentEntity getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(TypeOfPaymentEntity typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfDeliveringEntity getDelivering() {
        return delivering;
    }

    public void setDelivering(TypeOfDeliveringEntity delivering) {
        this.delivering = delivering;
    }

    public Set<OrderedBookEntity> getOrderedBooksList() {
        return orderedBooksList;
    }

    public void setOrderedBooksList(Set<OrderedBookEntity> orderedBooksList) {
        this.orderedBooksList = orderedBooksList;
    }
}
