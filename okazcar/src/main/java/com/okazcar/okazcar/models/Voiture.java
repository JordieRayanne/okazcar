package com.okazcar.okazcar.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity(name="voiture")
@Table(name="voiture")
public class Voiture {
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Setter
    @Column(name="nom",nullable = false, unique = true)
    private String nom;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id_categorie",nullable =false)
    private Categorie categorie;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;
    
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_modele", nullable = false)
    private Modele modele;

    @Setter
    @Column(name = "couleur", nullable = false)
    private String couleur;

    @Setter
    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Setter
    @Column(name = "date_demande",nullable = false)
    private Timestamp dateDemande = Timestamp.valueOf(LocalDateTime.now());

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    public Voiture(HttpServletRequest request) throws Exception {
        Categorie categorie = new Categorie();
        categorie.setId(Integer.parseInt(request.getParameter("categorie")));
        setCategorie(categorie);
        //
        Type type = new Type();
        type.setId(Integer.parseInt(request.getParameter("type")));
        setType(type);
        //
        Modele modele = new Modele();
        modele.setId(Integer.parseInt(request.getParameter("modele")));
        setModele(modele);

        setCouleur(request.getParameter("couleur"));

        setDescription(request.getParameter("description"));

        setLocalisation(request.getParameter("localisation"));

        setNom(request.getParameter("nom"));
    }
}
