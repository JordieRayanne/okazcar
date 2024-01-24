package com.okazcar.okazcar.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "historique_message")
@Table(name = "historique_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoriqueMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historique_message_id")
    private int historiqueMessageId;

    @Column(name = "utilisateur_id_historique_message1", nullable = false)
    private String utilisateurIdHistoriqueMessage1;

    @OneToOne
    @JoinColumn(name = "utilisateur_id_historique_message1", referencedColumnName = "utilisateur_id", insertable = false, updatable = false , nullable = false)
    private Utilisateur utilisateur1;


    @Column(name = "utilisateur_id_historique_message2", nullable = false)
    private String utilisateurIdHistoriqueMessage2;

    @OneToOne
    @JoinColumn(name = "utilisateur_id_historique_message2", referencedColumnName = "utilisateur_id", insertable = false, updatable = false , nullable = false)
    private Utilisateur utilisateur2;

    @Column(name = "date_heure_premiere_discussion", nullable = false)
    private Timestamp dateHeurePremiereDiscussion = Timestamp.valueOf(LocalDateTime.now());

    public HistoriqueMessage (String utilisateurIdHistoriqueMessage1, String utilisateurIdHistoriqueMessage2) {
        setUtilisateurIdHistoriqueMessage1(utilisateurIdHistoriqueMessage1);
        setUtilisateurIdHistoriqueMessage2(utilisateurIdHistoriqueMessage2);
    }
}
