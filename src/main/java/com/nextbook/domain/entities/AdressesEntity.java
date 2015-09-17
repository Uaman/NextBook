package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 07.09.2015.
 */
@Entity
@Table(name = "adresses")
public class AdressesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "BRANCH_NUMBER")
    private String branchNumber;

    @Override
    public String toString() {
        return "AdressesEntity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", branchNumber='" + branchNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdressesEntity that = (AdressesEntity) o;

        if (getId() != that.getId()) return false;
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) return false;
        return !(getBranchNumber() != null ? !getBranchNumber().equals(that.getBranchNumber()) : that.getBranchNumber() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getBranchNumber() != null ? getBranchNumber().hashCode() : 0);
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }
}
