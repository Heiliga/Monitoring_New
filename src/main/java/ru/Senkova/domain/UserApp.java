package ru.Senkova.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_APP")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_app_id_generator")
    @SequenceGenerator(name = "user_app_id_generator", sequenceName = "sq_user_app_id", allocationSize = 1)
    private Long id;

    @NaturalId
    private String login;

    private String firstName;

    private String patronymic;

    private String lastName;

    private String hashPassword;

    private String email;

    private boolean confirmationMail;

    @Transient
    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "ROLE_USER_APP",
            joinColumns = @JoinColumn(name = "ID_USER_APP"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE")
    )
    private Set<Role> roles = new HashSet<>();

    public UserApp(){

    }

/*    public UserApp(String login, String firstName, String patronymic, String lastName, String hashPassword, String email, boolean confirmationMail;) {
        this.login = login;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.hashPassword = hashPassword;
        this.email = email;
        this.confirmationMail=confirmationMail;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isConfirmationMail() {
        return confirmationMail;
    }

    public void setConfirmationMail(boolean confirmationMail) {
        this.confirmationMail = confirmationMail;
    }
}




