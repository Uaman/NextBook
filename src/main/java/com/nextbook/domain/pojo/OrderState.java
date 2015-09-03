package com.nextbook.domain.pojo;

/**
 * Created by Dima on 01.09.2015.
 */
public class OrderState {

    int id;

    String stateOfTheOrder;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderState that = (OrderState) o;

        if (id != that.id) return false;
        return !(stateOfTheOrder != null ? !stateOfTheOrder.equals(that.stateOfTheOrder) : that.stateOfTheOrder != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (stateOfTheOrder != null ? stateOfTheOrder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderState{" +
                "id=" + id +
                ", stateOfTheOrder='" + stateOfTheOrder + '\'' +
                '}';
    }
}
