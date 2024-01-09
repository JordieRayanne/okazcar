package com.okazcar.okazcar.models;

import com.google.firebase.auth.UserRecord;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.dto.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateur")
@Entity(name = "utilisateur")
public class Utilisateur {
    @Id
    @Column(name = "utilisateur_id", length = 150)
    private String utilisateurId;
    @Column(name = "email", unique = true, length = 80, nullable = false)
    private String email="";
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password = null;
    @Column(name = "phone_number")
    private String phoneNumber = null;
    @Column(name = "platform", nullable = false)
    private String platform;
    @Column(name = "image_url")
    private String imageUrl = "";
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateurs_role", joinColumns = @JoinColumn(name = "utilisateur_id_utilisateurs_role", referencedColumnName = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id_users_role", referencedColumnName = "role_id") )
    Set<Role> roles = new HashSet<Role>();
    public void setRole(Role role) {
        this.roles.add(role);
    }

    public Utilisateur(UserInsertDto userDto) throws ForgetException {
        if (userDto.getEmail().isEmpty())
            throw new ForgetException("Error! You forgot to enter email", new Throwable("You doesn't enter any email"));
        setEmail(userDto.getEmail());
        Role role = new Role();
        role.setRoleId(userDto.getRole());
        setRole(role);
        setUsername(userDto.getUsername());
        setPlatform(userDto.getPlatform());
        if (userDto.getPlatform().isEmpty() && userDto.getPassword() == null)
            throw new ForgetException("Error! You forgot to enter password", new Throwable("You doesn't enter any password"));
        setPassword(userDto.getPassword());
        setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getImageFile() != null)
            setImageUrl(userDto.getImageFile().getResource().getFilename());
    }

    public Utilisateur(UserRecord userRecord, UserInsertDto userDto) {
        if (userRecord.getEmail() != null)
            setEmail(userRecord.getEmail());
        setUsername(userRecord.getDisplayName());
        setPhoneNumber(userRecord.getPhoneNumber());
        setImageUrl(userRecord.getPhotoUrl());
        Role role = new Role();
        role.setRoleId(userDto.getRole());
        setRole(role);
        setPlatform(userDto.getPlatform());
    }
}
