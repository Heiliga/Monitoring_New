package ru.Senkova.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERAPP")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userapp_id_generator")
    @SequenceGenerator(name = "userapp_id_generator", sequenceName = "sq_userapp_id", allocationSize = 1)
    private Long id;

    @NaturalId
    private String login;

    private String firstName;

    private String patronymic;

    private String lastName;

    private String hashPassword;

    private String email;

    @Transient
    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ROLE_USERAPP",
            joinColumns = @JoinColumn(name = "ID_USERAPP"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE")
    )
    private Set<Role> roles = new HashSet<>();

    public UserApp(){

    }

    public UserApp(String login, String firstName, String patronymic, String lastName, String hashPassword, String email) {
        this.login = login;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.hashPassword = hashPassword;
        this.email = email;
    }

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

    @JsonIgnore
    public void setFullName(String fullName) {
        String[] name = fullName.split(" ");
        this.firstName = name[0];
        if (name.length == 3) {
            this.patronymic = name[1];
            this.lastName = name[2];
        } else {
            this.lastName = name[1];
        }
    }

    @JsonIgnore
    public String getFullName() {
        return patronymic == null ? String.format("%s %s", firstName, lastName) : String.format("%s %s %s", firstName, patronymic, lastName);
    }
}




