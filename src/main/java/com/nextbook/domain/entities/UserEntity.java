package com.nextbook.domain.entities;

import com.nextbook.dao.base.objects.GetableById;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/14/2015
 * Time: 1:27 PM
 */
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = UserEntity.getAllUsers, query = "SELECT user FROM UserEntity user"),
        @NamedQuery(name = UserEntity.getUserByEmail, query = "SELECT user FROM UserEntity user WHERE user.email=:email")
        })
public class UserEntity implements GetableById {

    public static final String getAllUsers = "getAllUsers";
    public static final String getUserByEmail = "getUserByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleEntity roleEntity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_adresses", joinColumns = {@JoinColumn(name = "USER_ID",
            nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ADDRESS_ID",
                    nullable = false, updatable = false)}
    )
    private Set<AdressesEntity> deliveryAdressesEnt = new HashSet<AdressesEntity>();

    @ManyToOne
    @JoinTable(name="users_to_publisher",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="publisher_id")})
    private PublisherEntity publisher;

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public static String getGetAllUsers() {
        return getAllUsers;
    }

    public static String getGetUserByEmail() {
        return getUserByEmail;
    }

    public Set<AdressesEntity> getDeliveryAdressesEnt() {
        return deliveryAdressesEnt;
    }

    public void setDeliveryAdressesEnt(Set<AdressesEntity> deliveryAdressesEnt) {
        this.deliveryAdressesEnt = deliveryAdressesEnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }
/*
    public PublisherEntity getPublisherEntity() {
        return publisherEntity;
    }

    public void setPublisherEntity(PublisherEntity publisherEntity) {
        this.publisherEntity = publisherEntity;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity entity = (UserEntity) o;

        if (active != entity.active) return false;
        if (email != null ? !email.equals(entity.email) : entity.email != null) return false;
        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (password != null ? !password.equals(entity.password) : entity.password != null) return false;
        if (roleEntity != null ? !roleEntity.equals(entity.roleEntity) : entity.roleEntity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (roleEntity != null ? roleEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", roleEntity=" + roleEntity +
                '}';
    }
}
