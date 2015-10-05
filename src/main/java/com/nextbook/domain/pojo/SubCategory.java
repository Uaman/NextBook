package com.nextbook.domain.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:33 PM
 */
public class SubCategory {

    private int id;

    private String nameUa;

    private int order;

    private String link;

    @JsonIgnore
    private Category category;

    private String nameEn;

    private String nameRu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCategory that = (SubCategory) o;

        if (id != that.id) return false;
        if (order != that.order) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (nameEn != null ? !nameEn.equals(that.nameEn) : that.nameEn != null) return false;
        if (nameRu != null ? !nameRu.equals(that.nameRu) : that.nameRu != null) return false;
        if (nameUa != null ? !nameUa.equals(that.nameUa) : that.nameUa != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameUa != null ? nameUa.hashCode() : 0);
        result = 31 * result + order;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", order=" + order +
                ", link='" + link + '\'' +
                ", category=" + category +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                '}';
    }
}
