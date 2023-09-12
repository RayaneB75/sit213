# TP2 Transmission non bruitée analogique

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
│   |   ├── DestinationFianleTest       # classe de test de DestinationFinale
│   ├── sources                      
│   |   ├── SourceFixe                  # classe de sources fixes (émetteurs)
│   |   ├── SourceAleatoire             # classe de sources aléatoires (émetteurs)
│   |   ├── SourceFixeTest              # classe de test de SourceFixe
│   |   ├── SourceAleatoireTest         # classe de test de SourceAleatoire
│   ├── information                     # classes utilitaires pour manipuler les informations
│   |── simulateur                      # classes principale pour simuler les échanges
│   ├── transmetteurs                   # classes utilitaires pour manipuler les transmetteurs (parfait ou non)
|   |── visulations                     # classes utilitaires pour visualiser les informations (sondes)
|   |── test                            # classes de tests JUnit
|   |   |── AllTests                    # classe de lancement de tous les tests
|__ doc                                 # documentation (générée par javadoc)
```