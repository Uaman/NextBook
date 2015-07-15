package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/14/2015
 * Time: 1:29 PM
 */
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity roleEntity = (RoleEntity) o;

        if (id != null ? !id.equals(roleEntity.id) : roleEntity.id != null) return false;
        if (name != null ? !name.equals(roleEntity.name) : roleEntity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}