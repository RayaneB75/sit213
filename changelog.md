
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
