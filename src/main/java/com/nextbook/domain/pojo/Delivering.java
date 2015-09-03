package com.nextbook.domain.pojo;

/**
 * Created by Dima on 03.09.2015.
 */
public class Delivering {

    int id;

    String typeOfTheDelivering;

    String desription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivering that = (Delivering) o;

        if (getId() != that.getId()) return false;
        if (getTypeOfTheDelivering() != null ? !getTypeOfTheDelivering().equals(that.getTypeOfTheDelivering()) : that.getTypeOfTheDelivering() != null)
            return false;
        return !(getDesription() != null ? !getDesription().equals(that.getDesription()) : that.getDesription() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getTypeOfTheDelivering() != null ? getTypeOfTheDelivering().hashCode() : 0);
        result = 31 * result + (getDesription() != null ? getDesription().hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfTheDelivering() {
        return typeOfTheDelivering;
    }

    @Override
    public String toString() {
        return "Delivering{" +
                "id=" + id +
                ", typeOfTheDelivering='" + typeOfTheDelivering + '\'' +
                ", desription='" + desription + '\'' +
                '}';
    }

    public void setTypeOfTheDelivering(String typeOfTheDelivering) {
        this.typeOfTheDelivering = typeOfTheDelivering;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }
}
