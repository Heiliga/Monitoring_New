package ru.Senkova.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.Senkova.domain.UserApp;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserAppPrinciple implements UserDetails {

    private Long id;

    private String login;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String patronymic;

    private Collection<? extends GrantedAuthority> authorities;

    public UserAppPrinciple() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAppPrinciple user = (UserAppPrinciple) o;
        return Objects.equals(id, user.id);
    }
    public static UserAppPrinciple build(UserApp user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());

        UserAppPrinciple userAppPrinciple=new UserAppPrinciple();
        userAppPrinciple.setId(user.getId());
        userAppPrinciple.setAuthorities(authorities);
        userAppPrinciple.setEmail(user.getEmail());
        userAppPrinciple.setFirstName(user.getFirstName());
        userAppPrinciple.setLastName(user.getLastName());
        userAppPrinciple.setLogin(user.getLogin());
        userAppPrinciple.setPassword(user.getHashPassword());
        userAppPrinciple.setPatronymic(user.getPatronymic());

        return userAppPrinciple;
    }
}
