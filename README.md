# 📚 Gestion des Réservations de Salles et Matériel Pédagogique (RSM)

## 🧭 Table des matières
- [📚 Gestion des Réservations de Salles et Matériel Pédagogique (RSM)](#-gestion-des-réservations-de-salles-et-matériel-pédagogique-rsm)
  - [🧭 Table des matières](#-table-des-matières)
  - [📘 Contexte du projet](#-contexte-du-projet)
  - [🧩 Présentation du projet](#-présentation-du-projet)
  - [🚀 Fonctionnalités principales](#-fonctionnalités-principales)
  - [🛠️ Technologies utilisées](#️-technologies-utilisées)
  - [🧪 Installation et démarrage](#-installation-et-démarrage)
    - [Prérequis :](#prérequis-)
    - [Étapes :](#étapes-)
- [1. Cloner le dépôt](#1-cloner-le-dépôt)
- [2. Créer une base de données PostgreSQL](#2-créer-une-base-de-données-postgresql)
- [Nom : rsm\_db](#nom--rsm_db)
- [Utilisateur : springuser](#utilisateur--springuser)
- [Mot de passe : azaleodel](#mot-de-passe--azaleodel)
- [3. Lancer l’application](#3-lancer-lapplication)

---

## 📘 Contexte du projet

Ce projet a été réalisé dans le cadre d'un projet tutoré dans l’unité d’enseignement **"Conception des systèmes d'information"** à l’École Nationale Supérieure Polytechnique de Yaoundé.

Il vise à développer une application web centralisée pour gérer :
- La réservation des **salles de cours**,
- La réservation du **matériel pédagogique** (ordinateurs portables, vidéoprojecteurs),
- Le suivi des horaires individuels et par formation.

---

## 🧩 Présentation du projet

L’application permet :
- Aux **enseignants** d’effectuer des réservations,
- À **tous les utilisateurs** (enseignants et étudiants) de consulter le **planning des salles**,
- Aux **enseignants** de visualiser leur propre récapitulatif horaire,
- Aux **responsables de formation** d’éditer le **récapitulatif global** de leur formation.

---

## 🚀 Fonctionnalités principales

- 🔐 Authentification JWT et gestion des rôles (`ENSEIGNANT`, `ETUDIANT`, `RESPONSABLE`, `ADMIN`)
- 🗓 Réservation de salles et matériels pédagogiques
- 📅 Consultation du planning général (accessible à tous)
- 📊 Récapitulatif horaire individuel (enseignants uniquement)
- 🧾 Récapitulatif global d’une formation (responsable uniquement)
- ⚙️ Gestion administrative : utilisateurs, salles, matériels

---

## 🛠️ Technologies utilisées

| Composant | Technologie |
|----------|-------------|
| Backend | Java 23, Spring Boot |
| API Docs | SpringDoc OpenAPI / Swagger |
| Base de données | PostgreSQL |
| Authentification | Spring Security + JWT |
| Build tool | Maven |
| Déploiement | Render (en option) |
| Autres | Lombok, Docker (optionnel) |

---

## 🧪 Installation et démarrage

### Prérequis :
- JDK 21+
- PostgreSQL 14+
- Maven 3.8+

### Étapes :

```bash
# 1. Cloner le dépôt
git clone https://github.com/Delmat237/RSM.git
cd RSM

# 2. Créer une base de données PostgreSQL
# Nom : rsm_db
# Utilisateur : springuser
# Mot de passe : azaleodel

🔐 Configurer les propriétés :

Dans src/main/resources/application-dev.properties :

spring.datasource.url=jdbc:postgresql://localhost:5432/rsm_db
spring.datasource.username=springuser
spring.datasource.password=azaleodel
spring.profiles.active=dev

# 3. Lancer l’application
mvn spring-boot:run -Dspring-boot.run.profiles=dev


✅ Accès à l’API : http://localhost:9000/api
✅ Swagger : http://localhost:9000/swagger-ui.html
📬 Contact

    Nom : Leonel Delmat

    📧 Email : azangueleonel9@gmail.com

    📱 WhatsApp : +237 657 450 314

    GitHub : Delmat237

    LinkedIn : Leonel Azangue

💡 Remarques

Ce projet est en développement actif. Prochaines améliorations :

    🔔 Notifications par e-mail

    🖼 Interface utilisateur avec React ou Vue

    📄 Export PDF des plannings

    🧪 Tests unitaires et intégration continue

    Merci pour votre intérêt. Contributions et retours sont les bienvenus !