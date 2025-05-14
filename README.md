# Gestion des Réservations de Salles et Matériel Pédagogique (RSM)

## Table des matières

- [Gestion des Réservations de Salles et Matériel Pédagogique (RSM)](#gestion-des-réservations-de-salles-et-matériel-pédagogique-rsm)
  - [Table des matières](#table-des-matières)
  - [Contexte du projet](#contexte-du-projet)
  - [Présentation du projet](#présentation-du-projet)
  - [Fonctionnalités principales](#fonctionnalités-principales)
  - [Technologies utilisées](#technologies-utilisées)
  - [Installation et démarrage](#installation-et-démarrage)
  - [Contact](#contact)
  - [Remarques](#remarques)

---

## Contexte du projet

Ce projet a été réalisé dans le cadre d'un projet tutoré lors de l'unité d'enseignement **"Conception des systèmes d'informations"** à l'École Nationale Supérieure Polytechnique  de Yaoundé. Il vise à développer une application web permettant de gérer la réservation des salles de cours ainsi que du matériel pédagogique (ordinateurs portables et vidéoprojecteurs) au sein d’un établissement scolaire.

...

## Présentation du projet

L’application permet aux enseignants de réserver des salles et du matériel pédagogique sous réserve de disponibilité. Le planning des salles est consultable par tous (enseignants et étudiants), tandis que le récapitulatif horaire individuel est accessible uniquement aux enseignants. De plus, un enseignant responsable pour chaque formation peut éditer le récapitulatif horaire global de sa formation.

---

## Fonctionnalités principales

- Authentification et gestion des rôles (enseignant, étudiant, responsable, administrateur).
- Réservation des salles et matériels par les enseignants.
- Consultation du planning des salles par tous.
- Consultation du récapitulatif horaire individuel par les enseignants.
- Édition du récapitulatif global par le responsable de formation.
- Gestion administrative des utilisateurs, salles et matériels.

---

## Technologies utilisées

- Backend : Java Spring Boot (REST API)
- Base de données : PostgreSQL
- Sécurité : Spring Security avec JWT
- Frontend (à prévoir) : Angular / React / Vue.js
- Gestion des dépendances : Maven
- Outils : Git, Docker (optionnel)

---

## Installation et démarrage

1. Cloner le dépôt :

git clone https://github.com/Delmat237/RSM.git

text
1. Configurer PostgreSQL avec une base `RSM_DB` et un utilisateur `springuser` (voir script SQL dans `/docs`).
3. Modifier `src/main/resources/application.properties` avec vos paramètres de connexion PostgreSQL.
4. Lancer l’application avec Maven :

mvn spring-boot:run

text
5. L’API sera accessible sur `http://localhost:8080/api`.

---

## Contact

- **Nom** : Leonel  Delmat   
- **Email** : azangueleonel9@gmail.com  
- **WhatsApp** : +237 657 450 314  
- **GitHub** : [Delmat237](https://github.com/Delmat237)  
- **LinkedIn** : [leonel-azangue](https://www.linkedin.com/in/leonel-azangue)

---

## Remarques

Ce projet est en cours de développement et peut être amélioré avec des fonctionnalités supplémentaires, notamment la gestion des notifications, l’interface utilisateur, et la documentation complète.

---

Merci de votre intérêt pour ce projet !
