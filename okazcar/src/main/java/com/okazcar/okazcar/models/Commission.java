package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commission")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commission {

    @Setter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Setter
	private int idVoitureUtilisateur;

    private double commission;

	public void setCommission(double commission) throws Exception {
		if (commission <= 0)
			throw new Exception("Commission négative");
		this.commission = commission;
	}

}
