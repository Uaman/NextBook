package com.nextbook.domain.forms.user;


import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;

/**
 * Created by Polomani on 21.07.2015.
 */
public class RegisterUserForm {

    @Email(message = "sign.up.error.wrong.email.format")
    @NotNull(message = "sign.up.error.blank.email")
    @Size(max = MAX_EMAIL_LENGTH, message = "sign.up.error.length.email")
    private String email;

    @NotNull(message = "sign.up.error.blank.password")
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = "sign.up.error.length.password")
    private String password;

    @NotNull(message = "sign.up.error.blank.name")
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "sign.up.error.length.name")
    private String name;

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

    public static final int MAX_EMAIL_LENGTH = 45;

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 30;

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 45;
}
