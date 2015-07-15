package com.nextbook.domain.filters;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/15/2015
 * Time: 11:03 PM
 */
public class UserCriterion {

    private String name;
    private String email;
    private String state;
    private int roleId;

    private int from;
    private int max;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCriterion that = (UserCriterion) o;

        if (from != that.from) return false;
        if (max != that.max) return false;
        if (roleId != that.roleId) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + from;
        result = 31 * result + max;
        return result;
    }

    @Override
    public String toString() {
        return "UserCriterion{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", state=" + state +
                ", roleId=" + roleId +
                ", from=" + from +
                ", max=" + max +
                '}';
    }
}
