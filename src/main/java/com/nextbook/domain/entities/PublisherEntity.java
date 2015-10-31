package com.nextbook.domain.entities;


import com.nextbook.dao.base.objects.Getable;
import com.nextbook.dao.base.objects.GetableById;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:25 PM
 */

@Entity
@Table(name = "publisher")
@NamedQueries({
        @NamedQuery(name = PublisherEntity.GET_BY_ID, query = "SELECT publisher FROM PublisherEntity publisher WHERE publisher.id=:id"),
        @NamedQuery(name = PublisherEntity.GET_ALL, query = "SELECT publisher FROM PublisherEntity publisher"),
        @NamedQuery(name = PublisherEntity.GET_PUBLISHERS_QUANTITY, query = "SELECT COUNT(publisher) FROM PublisherEntity publisher")
})
public class PublisherEntity implements Getable, GetableById{

    public static final String GET_BY_ID = "getPublisherById";
    public static final String GET_ALL = "getAllPublishers";
    public static final String GET_PUBLISHERS_QUANTITY = "getPublishersQuantity";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME_UA", nullable = false)
    private String nameUa;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "NAME_RU")
    private String nameRu;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL}, mappedBy = "publisher")
//    @JoinTable(name="users_to_publisher",
//            joinColumns={@JoinColumn(name="publisher_id")},
//            inverseJoinColumns={@JoinColumn(name="user_id")})
    private Set<UserEntity> users = new HashSet<UserEntity>();

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "PublisherEntity{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", description='" + description + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                ", users=" + users +
                '}';
    }
}
