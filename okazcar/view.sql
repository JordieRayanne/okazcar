CREATE VIEW v_annonce AS
SELECT 
    annonce.id AS id_annonce,
    annonce.date_annonce AS date_annonce,
    annonce.status AS status,
    commission.id AS id_commission,
    commission.commission AS commission,
    voiture_utilisateur.id AS id_voiture_utilisateur,
    voiture_utilisateur.etat AS voiture_utilisateur_etat,
    voiture.id AS id_voiture,
    voiture.nom AS voiture,
    voiture.couleur AS couleur,
    voiture.localisation AS localisation,
    voiture.date_demande AS date_demande,
    voiture.description AS description,
    voiture.prix AS prix,
    devise.id AS id_devise,
    modele.id AS id_modele,
    modele.date_creation AS date_creation,
    type.id AS id_type,
    type.nom AS type,
    marque.id AS id_marque,
    marque.nom AS marque,
    categorie.id AS id_categorie,
    categorie.nom AS categorie,
    utilisateur.id AS id_utilisateur,
    utilisateur.nom AS vendeur_nom,
    utilisateur.prenom AS vendeur_prenom,
    utilisateur.date_naiss AS date_naissance,
    utilisateur.mail AS vendeur_mail,
    utilisateur.contact AS contact,
    utilisateur.genre AS genre
FROM 
    annonce
JOIN 
    commission ON commission.id = annonce.id
JOIN 
    voiture_utilisateur ON voiture_utilisateur.id = commission.id_voiture_utilisateur
JOIN
    voiture ON voiture.id = voiture_utilisateur.id_voiture
JOIN
    devise ON devise.id = voiture.id_devise
JOIN
    modele ON modele.id = voiture.id_modele
JOIN 
    type ON type.id = voiture.id_type
JOIN 
    marque ON marque.id = voiture.id_marque
JOIN 
    categorie ON categorie.id = voiture.id_categorie
JOIN 
    utilisateur ON utilisateur.id = voiture_utilisateur.id_utilisateur

SELECT * FROM v_annonce
WHERE categorie LIKE '%%'
  AND marque LIKE '%%'
  AND type LIKE '%%'
  AND date_creation::text LIKE '%%'
  AND localisation LIKE '%%'
  AND prix::text LIKE '%%';