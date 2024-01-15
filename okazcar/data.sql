INSERT INTO utilisateur (nom,prenom,date_naiss,mail,contact,genre,mdp)
VALUES
('ANDRIAMANANTSOA','Roger','1989-05-12','roger@gmail.com','0321598752','Homme','roger');

INSERT INTO categorie (nom)
VALUES
('Mini-citadines'),
('Petites voitures'),
('Voitures compactes'),
('Grosses voitures'),
('Voitures de prestige'),
('Voitures de luxe');

INSERT INTO marque (nom)
VALUES
('Peugeot'),
('Citroën'),
('Renault'),
('Opel'),
('Volkswagen'),
('Mercedes'),
('Audi'),
('BMW');

INSERT INTO type (nom)
VALUES
('Transport'),
('Familiere');

INSERT INTO modele (nom, date_creation)
VALUES
    ('Toyota Camry', '2019-05-20'),
    ('Honda Accord', '2018-10-15'),
    ('Ford Fusion', '2020-02-08');

INSERT INTO devise (nom)
VALUES
    ('Euro'),
    ('Dollar'),
    ('Ariary'),
    ('Yen');

INSERT INTO voiture (nom,id_categorie,id_marque,id_type,id_modele,couleur,localisation,description,prix,id_devise)
VALUES
    ('Fer',2,1,2,2,'Bleu','Antananarivo','Petite voiture comfortable pour 5 personnes.',1000000,3);

INSERT INTO voiture_utilisateur (id_utilisateur,id_voiture)
VALUES
    (1,1);

INSERT INTO commission (id_voiture_utilisateur,commission)
VALUES
    (1,5);

INSERT INTO annonce (date_annonce,id_commission)
VALUES
    ('2023-05-15',1);