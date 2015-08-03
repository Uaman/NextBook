package com.nextbook.domain.forms.user;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/3/2015
 * Time: 8:38 AM
 */
public class UserChangePassword {

    private String oldPassword;

    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
