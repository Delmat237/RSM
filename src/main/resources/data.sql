-- Insert un ADMIN
INSERT INTO enseignant (id, nom, prenom, email, matricule, mot_de_passe, role)
VALUES (1, 'Admin', 'Système', 'admin@rsm.com', 'ADM001', '$2a$10$V.uMdcMpKmZhLKPsl/ENjO7DUc9F9jGSJBGnSm3guPMN5iV3WvVZu', 'ROLE_ADMIN');
-- Mot de passe = admin123

-- Insert un ENSEIGNANT
INSERT INTO enseignant (id, nom, prenom, email, matricule, mot_de_passe, role)
VALUES (2, 'Jean', 'Michel', 'enseignant@rsm.com', 'ENS001', '$2a$10$0JjTkklz7fMbpE0KPHPhGe7cELO3L6vS9B1tdxlScEXhYj9MuJuA2', 'ROLE_ENSEIGNANT');
-- Mot de passe = enseignant123

-- Insert une salle
INSERT INTO salle (id, nom, capacite, disponibilite)
VALUES (1, 'Salle A', 30, true);

-- Insert un matériel
INSERT INTO materiel (id, nom, type, disponibilite)
VALUES (1, 'Vidéoprojecteur Epson', 'VIDEOPROJECTEUR', true);

-- Insert un responsable (hérite de enseignant)
INSERT INTO enseignant (id, nom, prenom, email, matricule, mot_de_passe, role)
VALUES (3, 'Claire', 'Responsable', 'responsable@rsm.com', 'RES001', '$2a$10$ZCmHwvmzqtLQdYbcMYcdC.OFkGv0tzxMN8BEM1Yt2iG8glJzR33Mi', 'ROLE_RESPONSABLE_FORMATION');
-- Mot de passe = responsable123

-- Insert une formation liée au responsable
INSERT INTO formation (id, nom, responsable_id)
VALUES (1, 'Informatique', 3);

-- src/main/resources/data.sql

INSERT INTO enseignant (id, nom, prenom, email, matricule, mot_de_passe, role)
VALUES (1, 'Delmat', 'Tchameni', 'enseignant@rsm.com', 'EN1234',
        '$2a$10$6ZcKHm91RQ.qHWeToK9bu.4G3OTRW7M7hZoUZ7eXYpQEvb7MREUnW', 'ENSEIGNANT');
