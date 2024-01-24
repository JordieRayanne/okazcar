package com.okazcar.okazcar.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

	@ManyToOne
    @JoinColumn(name = "idvoitureutilisateur", referencedColumnName = "id")
    private VoitureUtilisateur voitureUtilisateur;

    private double commission;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VoitureUtilisateur getVoitureUtilisateur() {
		return voitureUtilisateur;
	}

	public void setVoitureUtilisateur(VoitureUtilisateur voitureUtilisateur) {
		this.voitureUtilisateur = voitureUtilisateur;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

}
