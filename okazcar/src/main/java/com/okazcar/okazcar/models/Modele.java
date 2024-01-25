package com.okazcar.okazcar.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "modele")
public class Modele {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "nom", nullable = false, unique = true)
    String nom;

    @Column(name = "date_creation")
    Date dateCreation;

    @OneToOne
    @JoinColumn(name = "id_marque",nullable = false)
    Marque marque;
}
