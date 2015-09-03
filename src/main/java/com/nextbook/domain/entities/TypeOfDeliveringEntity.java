package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 03.09.2015.
 */
@Entity
public class TypeOfDeliveringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "DESCRIPTION")
    private String description;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TypeOfDeliveringEntity{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfDeliveringEntity that = (TypeOfDeliveringEntity) o;

        if (getId() != that.getId()) return false;
        if (getTypeName() != null ? !getTypeName().equals(that.getTypeName()) : that.getTypeName() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getTypeName() != null ? getTypeName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
