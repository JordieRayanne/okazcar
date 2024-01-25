package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Subselect("SELECT " +
        "annonce.id AS id_annonce, " +
        "annonce.date_annonce AS date_annonce, " +
        "annonce.status AS status, " +
        "commission.id AS id_commission, " +
        "commission.commission AS commission, " +
        "voiture_utilisateur.id_utilisateur AS id_voiture_utilisateur, " +
        "voiture_utilisateur.etat AS voiture_utilisateur_etat, " +
        "voiture.id AS id_voiture, " +
        "voiture.nom AS voiture, " +
        "voiture.couleur AS couleur, " +
        "voiture.localisation AS localisation, " +
        "voiture.date_demande AS date_demande, " +
        "voiture.description AS description, " +
        "voiture.prix AS prix, " +
        "devise.id AS id_devise, " +
        "modele.id AS id_modele, " +
        "modele.date_creation AS date_creation, " +
        "type.id AS id_type, " +
        "type.nom AS type, " +
        "marque.id AS id_marque, " +
        "marque.nom AS marque, " +
        "categorie.id AS id_categorie, " +
        "categorie.nom AS categorie, " +
        "utilisateur.utilisateur_id AS id_utilisateur, " +
        "utilisateur.username AS vendeur_nom, " +
        "utilisateur.birthday AS date_naissance, " +
        "utilisateur.email AS vendeur_mail, " +
        "utilisateur.phone_number AS contact, " +
        "utilisateur.platform AS genre " +
        "FROM " +
        "annonce " +
        "JOIN " +
        "commission ON commission.id = annonce.id " +
        "JOIN " +
        "voiture_utilisateur ON voiture_utilisateur.id = commission.idvoitureutilisateur " +
        "JOIN " +
        "voiture ON voiture.id = voiture_utilisateur.id_voiture " +
        "JOIN " +
        "devise ON devise.id = voiture.id_devise " +
        "JOIN " +
        "modele ON modele.id = voiture.id_modele " +
        "JOIN " +
        "type ON type.id = voiture.id_type " +
        "JOIN " +
        "marque ON marque.id = modele.id_marque " +
        "JOIN " +
        "categorie ON categorie.id = voiture.id_categorie " +
        "JOIN " +
        "utilisateur ON utilisateur.utilisateur_id = voiture_utilisateur.id_utilisateur;")
@Synchronize({ "annonce", "commission", "voiture_utilisateur", "voiture", "devise", "modele", "type", "marque", "categorie", "utilisateur" })
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

    @Column(name = "date_naissance")
    private Date dateNaissance;

    @Column(name = "vendeur_mail")
    private String vendeurMail;

    @Column(name = "contact")
    private String contact;

    @Column(name = "genre")
    private String genre;
}
