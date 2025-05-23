# Étape 1 : Utiliser une image Java
FROM eclipse-temurin:21-jdk

# Étape 2 : Créer un dossier de travail
WORKDIR /app

# Étape 3 : Copier le jar généré dans l'image
COPY target/RSM-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : Exposer le port utilisé par l'app
EXPOSE 9000

# Étape 5 : Lancer l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
