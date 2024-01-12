package com.okazcar.okazcar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "categorie")
@Entity(name = "categorie")
@Getter
@Setter
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nom", length = 50, unique = true, nullable = false)
    private String nom;
}
