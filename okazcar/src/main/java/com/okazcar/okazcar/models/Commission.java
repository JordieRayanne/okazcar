package com.okazcar.okazcar.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int idVoitureUtilisateur;

    private double commission;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdVoitureUtilisateur() {
		return idVoitureUtilisateur;
	}

	public void setIdVoitureUtilisateur(int idVoitureUtilisateur) {
		this.idVoitureUtilisateur = idVoitureUtilisateur;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

}
