package com.nextbook.domain.filters;

/**
 * Created by Stacy on 7/29/15.
 */

public class AuthorCriterion {

    private int from;
    private int max;

    private String firstNameUa;

    private String lastNameUa;

    private String firstNameEn;

    private String lastNameEn;

    private String firstNameRu;

    private String lastNameRu;


    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorCriterion)) return false;

        AuthorCriterion that = (AuthorCriterion) o;

        if (from != that.from) return false;
        if (max != that.max) return false;
        if (firstNameEn != null ? !firstNameEn.equals(that.firstNameEn) : that.firstNameEn != null) return false;
        if (firstNameRu != null ? !firstNameRu.equals(that.firstNameRu) : that.firstNameRu != null) return false;
        if (firstNameUa != null ? !firstNameUa.equals(that.firstNameUa) : that.firstNameUa != null) return false;
        if (lastNameEn != null ? !lastNameEn.equals(that.lastNameEn) : that.lastNameEn != null) return false;
        if (lastNameRu != null ? !lastNameRu.equals(that.lastNameRu) : that.lastNameRu != null) return false;
        if (lastNameUa != null ? !lastNameUa.equals(that.lastNameUa) : that.lastNameUa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + max;
        result = 31 * result + (firstNameUa != null ? firstNameUa.hashCode() : 0);
        result = 31 * result + (lastNameUa != null ? lastNameUa.hashCode() : 0);
        result = 31 * result + (firstNameEn != null ? firstNameEn.hashCode() : 0);
        result = 31 * result + (lastNameEn != null ? lastNameEn.hashCode() : 0);
        result = 31 * result + (firstNameRu != null ? firstNameRu.hashCode() : 0);
        result = 31 * result + (lastNameRu != null ? lastNameRu.hashCode() : 0);
        return result;
    }

    public String getFirstNameUa() {
        return firstNameUa;
    }

    public void setFirstNameUa(String firstNameUa) {
        this.firstNameUa = firstNameUa;
    }

    public String getLastNameUa() {
        return lastNameUa;
    }

    public void setLastNameUa(String lastNameUa) {
        this.lastNameUa = lastNameUa;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getFirstNameRu() {
        return firstNameRu;
    }

    public void setFirstNameRu(String firstNameRu) {
        this.firstNameRu = firstNameRu;
    }

    public String getLastNameRu() {
        return lastNameRu;
    }

    public void setLastNameRu(String lastNameRu) {
        this.lastNameRu = lastNameRu;
    }



}
