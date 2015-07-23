package com.nextbook.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:25 PM
 */

@Entity
@Table(name = "publisher")
public class PublisherEntity {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_to_publisher", joinColumns = {@JoinColumn(name = "PUBLISHER_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<UserEntity> users = new ArrayList<UserEntity>();

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

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
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
