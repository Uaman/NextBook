package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 03.09.2015.
 */

@Entity
@Table(name = "ORDER_STATES")
public class OrderStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "state_of_the_order")
    private String stateOfTheOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStateOfTheOrder() {
        return stateOfTheOrder;
    }

    public void setStateOfTheOrder(String stateOfTheOrder) {
        this.stateOfTheOrder = stateOfTheOrder;
    }

    @Override
    public String toString() {
        return "StateOfOrderEntity{" +
                "id=" + id +
                ", stateOfTheOrder='" + stateOfTheOrder + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStateEntity that = (OrderStateEntity) o;

        if (getId() != that.getId()) return false;
        return !(getStateOfTheOrder() != null ? !getStateOfTheOrder().equals(that.getStateOfTheOrder()) : that.getStateOfTheOrder() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getStateOfTheOrder() != null ? getStateOfTheOrder().hashCode() : 0);
        return result;
    }
}
