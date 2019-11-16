package ru.levelp.junior.web;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationFormBean {
    @Size(min = 4, max = 32, message = "Login length should be at least 4 and at most 32 characters length.")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z_0-9]*", message = "Login should consist of characters: ...")
    private String login;

    @Size(min = 4, max = 32, message = "Password length ...")
    private String password;

    private String passwordConfirmation;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
