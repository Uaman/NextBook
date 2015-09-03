package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 03.09.2015.
 */
@Entity
public class TypeOfPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME_OF_TYPE")
    private String nameOfType;

    @Override
    public String toString() {
        return "TypeOfPaymentEntity{" +
                "id=" + id +
                ", nameOfType='" + nameOfType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfPaymentEntity that = (TypeOfPaymentEntity) o;

        if (getId() != that.getId()) return false;
        return !(getNameOfType() != null ? !getNameOfType().equals(that.getNameOfType()) : that.getNameOfType() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getNameOfType() != null ? getNameOfType().hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public void setNameOfType(String nameOfType) {
        this.nameOfType = nameOfType;
    }
}
