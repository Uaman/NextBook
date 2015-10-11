package com.nextbook.domain.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:31 PM
 */
public class Category {

    private int id;

    private String nameUa;

    private int order;

    private String link;

    private String nameEn;

    private String nameRu;

    private List<SubCategory> subCategory = new ArrayList<SubCategory>();

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (order != category.order) return false;
        if (link != null ? !link.equals(category.link) : category.link != null) return false;
        if (nameEn != null ? !nameEn.equals(category.nameEn) : category.nameEn != null) return false;
        if (nameRu != null ? !nameRu.equals(category.nameRu) : category.nameRu != null) return false;
        if (nameUa != null ? !nameUa.equals(category.nameUa) : category.nameUa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameUa != null ? nameUa.hashCode() : 0);
        result = 31 * result + order;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", order=" + order +
                ", link='" + link + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                '}';
    }
}
