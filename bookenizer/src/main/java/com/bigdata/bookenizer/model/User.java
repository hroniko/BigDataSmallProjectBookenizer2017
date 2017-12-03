package com.bigdata.bookenizer.model;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class User implements UserDetails {
    Long id;
    String firstName ;
    String lastName ;
    String login;
    String mail;
    String age ;
    String country ;
    String password ;

    public User(Long id, String firstName, String lastName, String login, String password, String mail, String age, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.mail = mail;
        this.age = age;
        this.country = country;
        this.password = password;
    }

    public User(String firstName, String lastName, String login, String mail, String age, String country, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.mail = mail;
        this.age = age;
        this.country = country;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
}
