package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "v_annonce")
@Entity(name = "v_annonce")
@Getter
@Setter
public class V_Annonce {
    @Id
    @Column(name = "id_annonce")
    private int idAnnonce;

    @Column(name = "date_annonce")
    private Timestamp dateAnnonce;

    @Column(name = "status")
    private int status;

    @Column(name = "id_commission")
    private int idCommission;

    @Column(name = "commission")
    private double commission;

    @Column(name = "id_voiture_utilisateur")
    private int idVoitureUtilisateur;

    @Column(name = "voiture_utilisateur_etat")
    private int voitureUtilisateurEtat;

    @Column(name = "id_voiture")
    private int idVoiture;

    @Column(name = "voiture")
    private String voiture;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "localisation")
    private String localisation;

    @Column(name = "date_demande")
    private Timestamp dateDemande;

    @Column(name = "description")
    private String description;

    @Column(name = "prix")
    private double prix;

    @Column(name = "id_devise")
    private int idDevise;

    @Column(name = "id_modele")
    private int idModele;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @Column(name = "id_type")
    private int idType;

    @Column(name = "type")
    private String type;

    @Column(name = "id_marque")
    private int idMarque;

    @Column(name = "marque")
    private String marque;

    @Column(name = "id_categorie")
    private int idCategorie;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "id_utilisateur")
    private int idUtilisateur;

    @Column(name = "vendeur_nom")
    private String vendeurNom;

    @Column(name = "vendeur_prenom")
    private String vendeurPrenom;

    @Column(name = "date_naissance")
    private Date dateNaissance;

    @Column(name = "vendeur_mail")
    private String vendeurMail;

    @Column(name = "contact")
    private String contact;

    @Column(name = "genre")
    private String genre;
}
