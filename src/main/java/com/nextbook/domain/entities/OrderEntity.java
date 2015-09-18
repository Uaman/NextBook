package com.nextbook.domain.entities;

import com.nextbook.domain.pojo.TypeOfPayment;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Dima on 03.09.2015.
 */

@Entity
@Table(name = "orders")
@NamedQueries(value = {
//        @NamedQuery(name = OrderEntity.getAllOrders, query = "SELECT order FROM OrderEntity orders"),
        @NamedQuery(name = OrderEntity.getOrdersByUser, query = "SELECT order FROM OrderEntity order WHERE order.userEntity.id =: userId")
})
public class OrderEntity implements java.io.Serializable {

    public static final String getAllOrders = "getAllOrders";
    public static final String getOrdersByUser = "getOrdersByUser";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;




    @OneToMany(mappedBy = "orderEntities",cascade=CascadeType.ALL)
    private Set<OrderedBookEntity> orderedBooksEnt = new HashSet<OrderedBookEntity>();

    public Set<OrderedBookEntity> getOrderedBooksEnt() {
        return orderedBooksEnt;
    }

    public void setOrderedBooksEnt(Set<OrderedBookEntity> orderedBooksEnt) {
        this.orderedBooksEnt = orderedBooksEnt;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATE_ID", nullable = false)
    private StateOfOrderEntity stateOfOrderEnt;

    @Column(name = "IS_PAID")
    private byte[] isPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @Column(name = "DATE_OF_ORDER")
    Date dateOfOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TYPE_OF_PAYMENT_ID", nullable = false)
    TypeOfPaymentEntity typeOfPaymentEnt;

    @Column(name = "DESCRIPTION")
    String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DELIVERING_ID", nullable = false)
    TypeOfDeliveringEntity typeOfDeliveringEnt;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", stateOfOrderEnt=" + stateOfOrderEnt +
                ", isPaid=" + Arrays.toString(isPaid) +
                ", user=" + userEntity +
                ", dateOfOrder=" + dateOfOrder +
                ", typeOfPaymentEnt=" + typeOfPaymentEnt +
                ", description='" + description + '\'' +
                ", typeOfDeliveringEnt=" + typeOfDeliveringEnt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getStateOfOrderEnt() != null ? !getStateOfOrderEnt().equals(that.getStateOfOrderEnt()) : that.getStateOfOrderEnt() != null)
            return false;
        if (!Arrays.equals(getIsPaid(), that.getIsPaid())) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        if (getDateOfOrder() != null ? !getDateOfOrder().equals(that.getDateOfOrder()) : that.getDateOfOrder() != null)
            return false;
        if (getTypeOfPaymentEnt() != null ? !getTypeOfPaymentEnt().equals(that.getTypeOfPaymentEnt()) : that.getTypeOfPaymentEnt() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return !(getTypeOfDeliveringEnt() != null ? !getTypeOfDeliveringEnt().equals(that.getTypeOfDeliveringEnt()) : that.getTypeOfDeliveringEnt() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStateOfOrderEnt() != null ? getStateOfOrderEnt().hashCode() : 0);
        result = 31 * result + (getIsPaid() != null ? Arrays.hashCode(getIsPaid()) : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getDateOfOrder() != null ? getDateOfOrder().hashCode() : 0);
        result = 31 * result + (getTypeOfPaymentEnt() != null ? getTypeOfPaymentEnt().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getTypeOfDeliveringEnt() != null ? getTypeOfDeliveringEnt().hashCode() : 0);
        return result;
    }

    public static String getGetAllOrders() {
        return getAllOrders;
    }

    public static String getGetOrdersByUser() {
        return getOrdersByUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateOfOrderEntity getStateOfOrderEnt() {
        return stateOfOrderEnt;
    }

    public void setStateOfOrderEnt(StateOfOrderEntity stateOfOrderEnt) {
        this.stateOfOrderEnt = stateOfOrderEnt;
    }

    public byte[] getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(byte[] isPaid) {
        this.isPaid = isPaid;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public TypeOfPaymentEntity getTypeOfPaymentEnt() {
        return typeOfPaymentEnt;
    }

    public void setTypeOfPaymentEnt(TypeOfPaymentEntity typeOfPaymentEnt) {
        this.typeOfPaymentEnt = typeOfPaymentEnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfDeliveringEntity getTypeOfDeliveringEnt() {
        return typeOfDeliveringEnt;
    }

    public void setTypeOfDeliveringEnt(TypeOfDeliveringEntity typeOfDeliveringEnt) {
        this.typeOfDeliveringEnt = typeOfDeliveringEnt;
    }
}
