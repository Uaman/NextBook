package com.nextbook.domain.forms.admin;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 7:40 PM
 */
public class UserEditForm {

    private int id;
    private String email;
    private String name;
    private int roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
