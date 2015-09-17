package com.nextbook.domain.pojo;

/**
 * Created by Dima on 14.09.2015.
 */
public class Adresses {

    private int id;

    private String city;

    private String branchNumber;

    @Override
    public String toString() {
        return "Adresses{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", branchNumber='" + branchNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adresses adresses = (Adresses) o;

        if (getId() != adresses.getId()) return false;
        if (getCity() != null ? !getCity().equals(adresses.getCity()) : adresses.getCity() != null) return false;
        return !(getBranchNumber() != null ? !getBranchNumber().equals(adresses.getBranchNumber()) : adresses.getBranchNumber() != null);

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
