package com.nextbook.domain.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/15/2015
 * Time: 7:53 AM
 */
public class Role {

    private Integer id;

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

        Role roleEntity = (Role) o;

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
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
