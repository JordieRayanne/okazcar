package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "commission")
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

	@Column(name = "commission", unique = true, nullable = false)
    private double commission;

	@Column(name = "date_heure_commission", nullable = false)
	private Timestamp dateHeureCommission = Timestamp.valueOf(LocalDateTime.now());

	public void setCommission(double commission) throws Exception {
		if (commission <= 0)
			throw new Exception("Commission négative ou null");
		this.commission = commission;
	}

}
