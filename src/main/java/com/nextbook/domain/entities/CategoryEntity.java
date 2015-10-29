package com.nextbook.domain.entities;

import com.nextbook.dao.base.objects.Getable;
import com.nextbook.dao.base.objects.GetableById;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:18 PM
 */

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = CategoryEntity.getAll, query = "SELECT category FROM CategoryEntity category"),
        @NamedQuery(name = CategoryEntity.getByLink, query = "SELECT category FROM CategoryEntity category WHERE category.link=:link")
})
public class CategoryEntity implements GetableById, Getable{

    public static final String getAll = "getAllCategories";
    public static final String getByLink = "getByLink";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME_UA", nullable = false)
    private String nameUa;

    @Column(name = "ORDER")
    private Integer order;

    @Column(name = "LINK")
    private String link;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "NAME_RU")
    private String nameRu;

    @OneToMany(mappedBy="categoryEntity", fetch = FetchType.LAZY)
    private List<SubCategoryEntity> subCategoryEntity =  new ArrayList<SubCategoryEntity>();

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
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

    public List<SubCategoryEntity> getSubCategoryEntity() {
        return subCategoryEntity;
    }

    public void setSubCategoryEntity(List<SubCategoryEntity> subCategoryEntity) {
        this.subCategoryEntity = subCategoryEntity;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", order=" + order +
                ", link='" + link + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                '}';
    }
}
