package com.okazcar.okazcar.models;

import java.sql.Timestamp;
import java.util.Locale.Category;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="voiture")
public class Voiture {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nom",nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_categorie",nullable =false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name="id_marque", nullable = false)
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = true)
    private Type type;
    
    @ManyToOne
    @JoinColumn(name = "id_modele", nullable = false)
    private Modele modele;

    @Column(name = "couleur", nullable = false)
    private String couleur;

    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Column(name = "date_demande",nullable = true)
    private Timestamp dateDemande;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "prix", nullable = false)
    private double prix;

    @ManyToOne
    @JoinColumn(name = "id_devise",nullable = true)
    private Devise devise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Timestamp getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Timestamp dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }
}
