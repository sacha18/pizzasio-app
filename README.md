# PizzaSIO Mobile
![image](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white) ![image](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![image](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)

## Projet d'application mobile pour le BTS SIO option SLAM 2224 du CIPECMA.

### Description
L'application mobile PizzaSIO est conçue pour le système d'exploitation Android. Elle utilise les endpoints fournis par l'application web PizzaSIO en tant que backend pour récupérer les informations sur les pizzas disponibles, gérer le panier d'achats et passer des commandes.

### Fonctionnalités Principales
- Gestion des comptes utilisateurs : création, modification et suppression de comptes.
- Catalogue de pizzas : navigation à travers les pizzas disponibles et ajout au panier.
- Panier : gestion du panier d'achats et passage de commandes.
- Historique des commandes : visualisation de l'historique des commandes passées.

### Contraintes Techniques
- Langage de programmation : Kotlin.
- Utilisation d'une API pour l'accès aux données.
- Utilisation de Retrofit pour les requêtes HTTP.
- Utilisation de Room pour la gestion de la base de données locale.
- Utilisation d'Android Studio comme environnement de développement.

### Conventional Commits
Assurez-vous que vos commits suivent la spécification des commits conventionnels, car une action GitHub vérifiera si la spécification est respectée.

Le commit conventionnel est un format de message de commit standardisé qui favorise la cohérence et simplifie le processus de génération de journaux des modifications, de versionnage et de navigation dans l'historique du projet. Il suit un format structuré composé d'un type, d'une portée facultative, d'une description et de corps et pieds facultatifs.

Voici une liste de types de commit courants :

- `feat`: Une nouvelle fonctionnalité.
- `fix`: Une correction de bogue.
- `docs`: Modifications de la documentation.
- `style`: Modifications de style de code (formatage, indentation, etc.).
- `refactor`: Refactorisation du code sans changer son comportement.
- `perf`: Améliorations de performance.
- `test`: Ajout, modification ou correction de tests.
- `build`: Modifications affectant le système de construction ou les dépendances externes.
- `ci`: Modifications des fichiers de configuration et des scripts CI/CD.
- `chore`: Autres modifications qui ne modifient pas les fichiers source ou de test.

Exemples de messages de commit conventionnels :

```
feat: Ajouter une nouvelle fonctionnalité de connexion
```

```
fix: Correction d'une faute de frappe dans le titre de la page d'accueil
```

```
docs: Mettre à jour README avec de nouvelles instructions de configuration du projet
```

```
style: Reformater le code pour une meilleure lisibilité
```

```
refactor: Simplifier la logique dans la fonction de recherche
```

Rappelez-vous que l'objectif du commit conventionnel est de rendre les messages de commit plus informatifs et plus faciles à comprendre, alors essayez de garder vos descriptions claires et concises.
