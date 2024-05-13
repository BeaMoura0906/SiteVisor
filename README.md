# SiteVisor

SiteVisor est un logiciel de gestion de chantiers de construction conçu pour faciliter l'organisation et le suivi des projets. Développé en Java et utilisant MySQL pour la gestion des données, il intègre la bibliothèque JavaFX pour une interface utilisateur graphique intuitive.

## Fonctionnalités principales

- Recherche et gestion des chantiers via une barre de recherche.
- Organisation des tâches en catégories et sous-catégories pour une planification optimisée.
- Impression en PDF de plannings de chantier pour un suivi efficace des tâches à réaliser.
- Association et visualisation de documents relatifs au chantier, tels que des PDF ou des images.

## Prérequis

- JDK 19 ou plus récent.
- Maven pour la gestion des dépendances.
- IntelliJ IDEA ou tout autre IDE compatible avec JavaFX et Maven.
- MySQL pour la gestion de base de données.

## Installation
Pour installer SiteVisor, suivez ces étapes :

1. Clonez le dépôt Git :
```bash
git clone https://github.com/BeaMoura0906/SiteVisor.git
```
2. Configurez votre base de données en utilisant le fichier SQL `site_visor.sql` dans le répertoire `src/main/sql/`.
3. Ouvrez le projet l'IDE.

## Configuration
Avant d'exécuter l'application, assurez-vous que toutes les dépendances sont correctement installées :

Le projet utilise Maven pour gérer les dépendances. Pour installer les dépendances, exécutez :
```bash
mvn clean install
```
dans le terminal de l'IDE ou utilisez la fonctionnalité intégrée de l'IDE pour recharger le projet Maven. Assurez-vous que Maven a correctement téléchargé et configuré ces dépendances avant de tenter d'exécuter l'application.

Construisez le projet avec Maven en exécutant :
```bash
mvn clean compile
```
Pour lancer l'application, exécutez le fichier `App.java` dans le package `com.example.SiteVisor` du répertoire `src/main/java`.

## Tests

Pour exécuter les tests unitaires, localisez le package `com.example.SiteVisor` dans le répertoire `src/test/java`, et utilisez l'IDE pour configurer et exécuter les tests. Dans les configurations de l'IDE, assurez-vous d'inclure les options VM suivantes pour JavaFX :
```bash
-ea --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
```
Exécutez les tests en utilisant la fonctionnalité de test intégrée de l'IDE.

Pour les tests fonctionnels, consultez le fichier `FunctionalTests.pdf` dans le répertoire `src/test/functional`.

## Documentation

Vous trouverez la documentation relative à ce projet dans le répertoire `docs` :
- Cahier des charges (document intitulé `requirements_document.pdf`)
- Diagrammes de cas d'utilisation, de séquences et de classes (UML) dans le répertoire `diagrams`
- JavaDocs dans le répertoire `javadocs`

Pour accéder à la documentation des classes Java, ouvrez le fichier `index.html` du répertoire `javadocs` à l'aide votre navigateur.

## Utilisation

- Lancez l'application en exécutant le fichier `App.java`.

- Consultez la liste des chantiers et utilisez la barre de recherche pour filtrer les chantiers visualisés dans la liste.

![HomePage](/screens/HomePage.png)

- Editez des chantiers (ajout, modification, suppression)

![AddSitePage](/screens/AddSitePage.png)

- Pop up d'erreur de saisie

![ErrorPopUp](/screens/ErrorPopUp.png)

- Visualiser de planning chantier avec une organisation des tâches en catégorie et sous-catégorie

![SitePlanningPage](/screens/SitePlanningPage.png)

- Editez des tâches, des sous-catégories et des catégories (ajout, modification, suppression)

![EditTasksPage_empty](/screens/EditTasksPage_empty.png)

- Associez des documents à un chantier et visualisez les. 

![DocPage](/screens/DocPage.png)

Pour plus de détails, n'hésitez pas à consulter les captures d'éccrans dans le répertoire `screens`.

## Changelog
Pour voir l'historique des modifications :

Consultez les commits Git pour suivre l'évolution du développement depuis le début.

## Auteur

Beatriz Moura | BeaMoura0906
