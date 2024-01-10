package com.okazcar.okazcar.details;

import com.okazcar.okazcar.models.Role;
import com.okazcar.okazcar.models.Utilisateur;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static com.okazcar.okazcar.handlers.ResponseHandler.mapRolesToAuthorities;

public class UtilisateurDetails implements UserDetails {
    @Getter
    Utilisateur utilisateur;
    Collection<? extends GrantedAuthority> authorities;

    public UtilisateurDetails(Utilisateur utilisateur, Collection<? extends GrantedAuthority> authorities) {
        this.utilisateur = utilisateur;
        this.authorities = authorities;
    }

    public UtilisateurDetails(Utilisateur utilisateur, Set<Role> authorities) {
        this.utilisateur = utilisateur;
        this.authorities = mapRolesToAuthorities(authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
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
