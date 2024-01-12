package com.okazcar.okazcar.models;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserAuthentication implements Authentication {
    private final Utilisateur utilisateur;
    private final Collection<? extends GrantedAuthority> authorities;
    public UserAuthentication(Utilisateur utilisateur, Collection<? extends GrantedAuthority> authorities) {
        this.utilisateur = utilisateur;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return utilisateur.getUsername();
    }
}
