![Éxécution](https://github.com/RayaneB75/sit213/actions/workflows/executable.yml/badge.svg)

![Livrable](https://github.com/RayaneB75/sit213/actions/workflows/livrable.yml/badge.svg)

![Validité du livrable](https://github.com/RayaneB75/sit213/actions/workflows/verif_livrable.yml/badge.svg) 

# TP5 : Codeur (ajout de bits de redondance)

## Objectifs
* Comprendre le concept de codeur détecteur/correcteur d'erreurs
* Implémenter un codeur détecteur/correcteur d'erreurs
* Modifier le simulateur pour prendre en compte le codeur
* Tracer la TEB en fonction du SNR par bit

## Fichiers mis à jour

* Ajout d'une classe **CodeurCanal** pour générer les bits de redondance.
* Amélioration de la documentation.
* Script ./runTests mis à jour pour lancer les tests de la classe **CodeurCanal**.
* Ajout d'un script pour tracer la TEB en fonction du SNR par bit (``java -cp bin/ visualisations.TebFctSnrCodage``)

## Arborescence du projet mise à jour

```
.
├── bin
├── src
|   |── codages
|   |   |── CodeurCanal                 # classe de codeur canal
|   |   |── DecodeurCanal               # classe de decodeur canal
|   |   |── Codeur                      # classe abstraite pour les codeurs
│   ├── destinations
│   ├── information
|   |── simulateur
|   |── tests
|   |── transmetteurs
|   |── visualisations
|   |   |── TebFctSnrCodage            # classe pour tracer la TEB en fonction du SNR par bit avec codage
```

# TP4 : Transmission bruitée (trajets indirects)

## Objectifs

* Comprendre le concept de canal ajoutant des trajets indirects
* Modifier le codeur pour s'adapter à la nouvelle taille des trames
* Modifier le décodeur pour s'adapter à la nouvelle taille des trames
* Modifier le simulateur pour prendre en compte les trajets indirects

## Fichiers mis à jour
* Ajout d'une classe **TransmetteurMultiTrajets** pour générer un signal bruité.
* Amélioration de la documentation.
* Script ./runTests mis à jour pour lancer les tests de la classe **TransmetteurMultiTrajets**.
* Ajout d'un script pour tracer la TEB en fonction du SNR par bit (``java -cp bin/ visualisations.TebFctSnrTi``)

## Arborescence du projet mise à jour

```
.
├── bin                                 # fichiers compilés (doit être vide au départ)
├── src
│   ├── destinations                    # classes de destinations finales (récepteurs)
│   ├── information                     # classes utilitaires pour manipuler les informations
|   |── codeurs                         # classes utilitaires pour manipuler les codeurs
|   |   |── CodeurRZ                    # classe de codeur RZ
|   |   |── CodeurNRZ                   # classe de codeur NRZ
|   |   |── CodeurNRZT                  # classe de codeur NRZT
|   |   |── Decodeur                    # classe de décodeur simple
|   |   |── DecodeurNRZ                 # classe de décodeur NRZ
│   ├── sources                         # classes de sources (émetteurs)
│   |── simulateur                      # classes principale pour simuler les échanges
|   |── tests                           # classes de tests JUnit
│   ├── transmetteurs                   
|   |   |── TransmetteurMultiTrajets    # classes d'un transmetteur avec trajets indirects
|__ doc                                 # documentation (générée par javadoc)
```


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
