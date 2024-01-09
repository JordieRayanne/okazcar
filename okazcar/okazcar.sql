CREATE DATABASE voiture;

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naiss DATE,
    mail VARCHAR(100) UNIQUE NOT NULL,
    contact VARCHAR(20),
    genre VARCHAR(10),
    mdp VARCHAR(255) NOT NULL
);

CREATE TABLE categorie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE marque (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE modele (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    date_creation DATE
);

CREATE TABLE devise (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE voiture (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    id_categorie INTEGER REFERENCES categorie(id),
    id_marque INTEGER REFERENCES marque(id),
    id_modele INTEGER REFERENCES modele(id),
    couleur VARCHAR(20),
    localisation VARCHAR(100),
    date_demande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    prix NUMERIC,
    id_devise INTEGER REFERENCES devise(id) 
);

CREATE TABLE voiture_utilisateur (
    id SERIAL PRIMARY KEY,
    id_utilisateur INT REFERENCES utilisateur(id),
    id_voiture INT REFERENCES voiture(id),
    etat INT DEFAULT 0
);

CREATE TABLE commission (
    id SERIAL PRIMARY KEY,
    id_voiture_utilisateur INT REFERENCES voiture_utilisateur(id),
    commission DECIMAL
);

CREATE TABLE annonce (
    id SERIAL PRIMARY KEY,
    date_annonce TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_commission INT REFERENCES commission(id),
    status INT DEFAULT 0
);

CREATE TABLE client_voiture (
    id SERIAL PRIMARY KEY,
    id_utilisateur INT REFERENCES utilisateur(id),
    id_annonce INT REFERENCES annonce(id),
    date_contact TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    favori INT DEFAULT 0
);