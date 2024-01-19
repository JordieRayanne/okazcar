package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "annonce")
public class Annonce implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date_annonce")
    private Timestamp dateAnnonce;

    @ManyToOne
    @JoinColumn(name = "id_commission", referencedColumnName = "id")
    private Commission commission;

    @Column(name = "status")
    private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDateAnnonce() {
		return dateAnnonce;
	}

	public void setDateAnnonce(Timestamp date_annonce) {
		this.dateAnnonce = date_annonce;
	}

	public Commission getCommission() {
		return commission;
	}

	public void setCommission(Commission commission) {
		this.commission = commission;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
