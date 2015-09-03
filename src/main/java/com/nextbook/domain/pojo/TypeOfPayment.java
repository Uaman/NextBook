package com.nextbook.domain.pojo;

/**
 * Created by Dima on 03.09.2015.
 */
public class TypeOfPayment {

    int id;

    String nameOfThePayment;

    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfPayment that = (TypeOfPayment) o;

        if (getId() != that.getId()) return false;
        if (getNameOfThePayment() != null ? !getNameOfThePayment().equals(that.getNameOfThePayment()) : that.getNameOfThePayment() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getNameOfThePayment() != null ? getNameOfThePayment().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TypeOfPayment{" +
                "id=" + id +
                ", nameOfThePayment='" + nameOfThePayment + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfThePayment() {
        return nameOfThePayment;
    }

    public void setNameOfThePayment(String nameOfThePayment) {
        this.nameOfThePayment = nameOfThePayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
