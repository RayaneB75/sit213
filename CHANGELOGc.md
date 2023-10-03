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