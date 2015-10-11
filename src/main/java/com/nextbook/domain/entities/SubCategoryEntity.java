package com.nextbook.domain.entities;

import com.nextbook.dao.base.objects.BaseDataObject;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:00 PM
 */

@Entity
@Table(name = "subcategory")
@NamedQueries({
        @NamedQuery(name = SubCategoryEntity.getAll, query = "SELECT subCategory FROM SubCategoryEntity subCategory"),
        @NamedQuery(name = SubCategoryEntity.getById, query = "SELECT subCategory FROM SubCategoryEntity subCategory WHERE subCategory.id=:id"),
        @NamedQuery(name = SubCategoryEntity.getAllByCategoryId, query = "SELECT subCategory FROM SubCategoryEntity subCategory WHERE subCategory.categoryEntity.id=:id")
})
public class SubCategoryEntity extends BaseDataObject {

    public static final String getAll = "getAllSubCategories";
    public static final String getById = "getSubCategoryById";
    public static final String getAllByCategoryId = "getAllByCategoryId";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME_UA")
    private String nameUa;

    @Column(name = "ORDER")
    private Integer order;

    @Column(name = "LINK")
    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "NAME_RU")
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

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
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
        return "SubCategoryEntity{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", order=" + order +
                ", link='" + link + '\'' +
                ", categoryEntity=" + categoryEntity +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                '}';
    }
}
