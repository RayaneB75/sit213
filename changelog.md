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
* Ajout d'un script pour tracer la TEB en fonction du SNR par bit (``java -cp bin/ visualisations.TebFctSnr``)

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
