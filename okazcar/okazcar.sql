CREATE DATABASE okazcar;

CREATE TABLE IF NOT EXISTS public.utilisateur
(
    utilisateur_id character varying(150) COLLATE pg_catalog."default" NOT NULL,
    email character varying(80) COLLATE pg_catalog."default" NOT NULL,
    image_url character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    platform character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    birthday DATE COLLATE pg_catalog."default",
    CONSTRAINT utilisateur_pkey PRIMARY KEY (utilisateur_id),
    CONSTRAINT uk_rma38wvnqfaf66vvmi57c71lo UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.utilisateurs_role
(
    utilisateur_id_utilisateurs_role character varying(150) COLLATE pg_catalog."default" NOT NULL,
    role_id_users_role integer NOT NULL,
    CONSTRAINT utilisateurs_role_pkey PRIMARY KEY (utilisateur_id_utilisateurs_role, role_id_users_role)
    );

ALTER TABLE IF EXISTS public.utilisateurs_role
    ADD CONSTRAINT fkha1whppwx5dpnsnypbf7pvqip FOREIGN KEY (role_id_users_role)
    REFERENCES public.role (role_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.utilisateurs_role
    ADD CONSTRAINT fkkji51o9hjts8snlu2t2m7a4r FOREIGN KEY (utilisateur_id_utilisateurs_role)
    REFERENCES public.utilisateur (utilisateur_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

CREATE TABLE categorie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE marque (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

CREATE TABLE type (
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
    id_type INTEGER REFERENCES type(id),
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
