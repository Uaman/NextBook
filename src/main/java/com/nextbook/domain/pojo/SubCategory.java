package com.nextbook.domain.pojo;

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
