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
@Entity(name = "HistoricLogin")
@Table(name = "HistoricLogin")
public class HistoricLogin {
    @Id
    @Column(name = "codeChiffre", unique = true, nullable = false)
    private String codeChiffre;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "utilisateur_id_historic_login", nullable = false)
    private String utilisateurId;

    @OneToOne
    @JoinColumn(name = "utilisateur_id_historic_login", referencedColumnName = "utilisateur_id", insertable = false, updatable = false , nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_heure_login", nullable = false)
    private Timestamp dateHeureLogin = Timestamp.valueOf(LocalDateTime.now());
}
