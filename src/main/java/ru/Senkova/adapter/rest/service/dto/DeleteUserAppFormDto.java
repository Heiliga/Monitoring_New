package ru.Senkova.adapter.rest.service.dto;

import java.util.Set;

public class DeleteUserAppFormDto {
    private String login;
    private String email;
    private String firstName;
    private String patronymic;
    private String lastName;
    private Set<String> roles;
    private String password;
    private boolean confirmationMail;

    public boolean isConfirmationMail() {
        return confirmationMail;
    }

    public void setConfirmationMail(boolean confirmationMail) {
        this.confirmationMail = confirmationMail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
