package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "annonce")
@Table(name = "annonce")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date_annonce")
    private Timestamp dateAnnonce = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "status")
    private int status = 0;

	@Column(name = "prix_commission", nullable = false)
	private double prixCommission;


    @Column(name = "prix_voiture", nullable = false)
    private double prixVoiture;

	@OneToOne
	@JoinColumn(name = "id_voitureutilisateur",nullable = false)
	private VoitureUtilisateur voitureUtilisateur;
}
