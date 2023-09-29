
# TP3 : Transmission bruitée (bruit gaussien)

## Objectifs

* Comprendre le concept de canal à bruit blanc additif gaussien
* Calculer la TEB en fonction du SNR par bit
* Générer un bruit gaussien qui suit une loi gaussienne
* Tracer la TEB en fonction du SNR par bit

## Fichiers mis à jour
* Ajout d'une classe **TransmetteurBruite** pour générer un signal bruité.
* Amélioration de la documentation.
* Ajout de tests pour la classe **TransmetteurBruite**.
* Ajout d'un script pour tracer la TEB en fonction du SNR par bit.



## Arborescence du projet mise à jour

```
.
├── bin                                 # fichiers compilés (doit être vide au départ)
├── src
│   ├── destinations                    # classes de destinations finales (récepteurs)
│   ├── information                     # classes utilitaires pour manipuler les informations
|   |── modulateurs                     
│   ├── sources                         # classes de sources (émetteurs)
│   |── simulateur                      # classes principale pour simuler les échanges
│   ├── transmetteurs                   # classes utilitaires pour manipuler les transmetteurs (parfait ou non)
|   |   |── TransmetteurBruite          # classe du Transmetteur bruite 
|   |── visulations                     # classes utilitaires pour visualiser les informations (sondes)
|   |── tests                           # classes de tests JUnit
|__ doc                                 # documentation (générée par javadoc)
```
# TP2 : Transmission non bruitée analogique

## Scripts mis à jour

- `simulateur` : script de lancement du simulateur (avec les différents paramètres possibles)
    - Ajout de l'option `codage` pour l'utilisation d'un codage de ligne
    - Ajout des paramètres `amplitude` et `form` pour les modulateurs
- .github/workflows/livrable.yml : script "Github Actions" (CI/CD) de génération de l'archive et du rapport à déposer sur Moodle
- .github/workflows/tests.yml : script "Github Actions" (CI/CD) de lancement des tests JUnit

## Arborescence du projet mise à jour

```
.
├── bin                                 # fichiers compilés (doit être vide au départ)
├── src
│   ├── destinations                    # classes de destinations finales (récepteurs)
│   ├── information                     # classes utilitaires pour manipuler les informations
|   |── modulateurs                     
|   |   |── Modulateur                  # classe de abstraite pour les modulateurs et demodulateurs
|   |   |── ModulateurRZ                # classe de modulateur RZ
|   |   |── ModulateurNRZ               # classe de modulateur NRZ
|   |   |── ModulateurNRZT              # classe de modulateur NRZT
|   |   |── DemodulateurRZ              # classe de demodulateur RZ
|   |   |── DemodulateurNRZ             # classe de demodulateur NRZ
|   |   |── DemodulateurNRZT            # classe de demodulateur NRZT
│   ├── sources                         # classes de sources (émetteurs)
│   |── simulateur                      # classes principale pour simuler les échanges
│   ├── transmetteurs                   # classes utilitaires pour manipuler les transmetteurs (parfait ou non)
|   |── visulations                     # classes utilitaires pour visualiser les informations (sondes)
|   |── tests                           # classes de tests JUnit
|__ doc                                 # documentation (générée par javadoc)
```

# TP1 : Transmission "back-to-back" - Mise en place de l'infrastructure

## Contenu de l'archive livrée

- `README.md` : ce fichier
- `compile` : script de compilation (intégrant le jar de JUnit et le jar de Hamcrest)
- `simulateur` : script de lancement du simulateur (avec les différents paramètres possibles)
- `runTests` : script de lancement des tests JUnit
- `genDoc` : script de génération de la documentation (javadoc)
- `genDeliverable` : script de génération de l'archive à déposer sur Moodle
- `cleanAll` : script de nettoyage de l'archive (suppression des fichiers générés)

Les fichiers sources sont dans le répertoire `src` et les fichiers compilés dans le répertoire `bin`.

## Arborescence du projet

```
.
├── bin                                 # fichiers compilés (doit être vide au départ)
├── src
│   ├── destinations
│   |   ├── DestinationFinale           # classe de destinations finales (récepteurs)
│   ├── sources                      
│   |   ├── SourceFixe                  # classe de sources fixes (émetteurs)
│   |   ├── SourceAleatoire             # classe de sources aléatoires (émetteurs)
│   ├── information                     # classes utilitaires pour manipuler les informations
│   |── simulateur                      # classes principale pour simuler les échanges
│   ├── transmetteurs                   # classes utilitaires pour manipuler les transmetteurs (parfait ou non)
|   |── visulations                     # classes utilitaires pour visualiser les informations (sondes)
|   |── test                            # classes de tests JUnit
|   |   |── AllTests                    # classe de lancement de tous les tests
|   |   |── DestinationFinaleTest       # classe de test de Sonde
|   |   |── SourceFixeTest              # classe de test de SourceFixe
|   |   |── SourceAleatoireTest         # classe de test de SourceAleatoire
|   |   |── TransmetteurParfaitTest     # classe de test de TransmetteurParfait
|__ doc                                 # documentation (générée par javadoc)
```
