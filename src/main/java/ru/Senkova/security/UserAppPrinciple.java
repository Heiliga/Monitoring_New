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

    public UserAppPrinciple(Long id, String login, String email, String hashPassword, String firstName, String lastName, String patronymic,
        List<GrantedAuthority> authorities){
        this.id = id;
        this.password = hashPassword;
        this.email=email;
        this.login=login;
        this.authorities=authorities;
        this.firstName=firstName;
        this.lastName=lastName;
        this.patronymic=patronymic;

    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserAppPrinciple( // TODO
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getHashPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                authorities
        );
    }
}
