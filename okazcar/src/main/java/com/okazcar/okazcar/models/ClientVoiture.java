package com.okazcar.okazcar.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity(name = "client_voiture")
@Table(name = "client_voiture")
public class ClientVoiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="id_utilisateur",nullable=true)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name="id_annonce",nullable = true)
    private Annonce annonce;

    @Column(name="date_contact", nullable = true)
    private Timestamp dateContact = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "favori",nullable = true)
    private int favori;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Timestamp getDateContact() {
        return dateContact;
    }

    public void setDateContact(Timestamp dateContact) {
        this.dateContact = dateContact;
    }

    public int getFavori() {
        return favori;
    }

    public void setFavori(int favori) {
        this.favori = favori;
    }

    
}
