package com.nextbook.domain.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:42 PM
 */
public class Publisher {

    private int id;

    private String nameUa;

    private String description;

    private String nameEn;

    private String nameRu;

    private List<User> users = new ArrayList<User>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (users == null)
            users = new ArrayList<User>();

        for (User u: users)
            if (u.getId().equals(user.getId()))
                return;

        users.add(user);
    }

    public void deleteUser(int userID) {
        if (users != null) {
            for (User user: users) {
                if (user.getId().equals(userID)) {
                    users.remove(user);
                    return;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;

        if (id != publisher.id) return false;
        if (description != null ? !description.equals(publisher.description) : publisher.description != null)
            return false;
        if (nameEn != null ? !nameEn.equals(publisher.nameEn) : publisher.nameEn != null) return false;
        if (nameRu != null ? !nameRu.equals(publisher.nameRu) : publisher.nameRu != null) return false;
        if (nameUa != null ? !nameUa.equals(publisher.nameUa) : publisher.nameUa != null) return false;
        if (users != null ? !users.equals(publisher.users) : publisher.users != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameUa != null ? nameUa.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
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
