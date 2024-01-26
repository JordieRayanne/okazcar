package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "voiture_utilisateur")
@Table(name = "voiture_utilisateur")
public class VoitureUtilisateur {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_utilisateur",nullable = true)
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "id_voiture",nullable = false)
    private Voiture voiture;

    @Column(name="immatriculation",nullable = false, unique = true)
    private String immatriculation;

    @Column(name = "etat")
    private int etat = 0;

    @Column(name="date_heure_demande", nullable = false, unique = true)
    private Timestamp dateHeureDemande = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "prix", nullable = false)
    private double prix;

    @Setter
    @OneToOne
    @JoinColumn(name = "id_devise",nullable = false)
    private Devise devise;

    public void setPrix(double prix) throws Exception {
        if (prix <= 0)
            throw new Exception("Erreur! Prix de voiture négatif ou null: " + prix);
        this.prix = prix;
    }

    public void setPrix(String prix) throws Exception {
        setPrix(Double.parseDouble(prix));
    }

}
