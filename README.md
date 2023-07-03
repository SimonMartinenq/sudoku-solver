# Résolveur de Sudoku

Ce programme est un résolveur de Sudoku écrit en Scala utilisant ZIO. Il est capable de résoudre des grilles de Sudoku fournies sous forme de fichiers texte ou de fichiers JSON.

## Configuration de l'environnement de développement
### Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre système :

    [Scala (version 2.13.0 ou supérieure)](https://docs.scala-lang.org/fr/tour/tour-of-scala.html) 
    [sbt (version 1.5.0 ou supérieure)](https://www.scala-sbt.org/) 
    [VS Code](https://code.visualstudio.com)
    [Metals (extension VS Code)](https://scalameta.org/metals/docs/editors/vscode/) 

### Configuration de Metals dans VS Code

    Ouvrez VS Code et installez l'extension "Metals" à partir du marché des extensions.
    Une fois l'extension installée, redémarrez VS Code.
    Ouvrez le dossier contenant le code source du programme Sudoku.
    Lorsque vous ouvrez le dossier, Metals détecte automatiquement qu'il s'agit d'un projet Scala et effectue les étapes de configuration nécessaires.
    Attendez que Metals termine le processus d'indexation du projet et la résolution des dépendances.

Vous devriez maintenant pouvoir utiliser toutes les fonctionnalités de Metals pour développer le programme Sudoku, y compris l'autocomplétion, la navigation dans le code et le débogage.

## Exécution du programme

Pour exécuter le programme, suivez les étapes suivantes :

1. Option 1
- Ouvrez un terminal dans VS Code en utilisant la combinaison de touches Ctrl+` (ou en allant dans le menu "Terminal" > "Nouveau terminal").
- Dans le terminal, exécutez la commande suivante pour compiler le programme :
  ```bash
    sbt compile
  ```
- Une fois la compilation terminée, exécutez la commande suivante pour exécuter le programme :
  ```bash
    sbt run
  ```
1. Option 2

2. Appuyer sur run au niveau de la déclaration du Main

3. Le programme vous demandera de choisir le type de fichier à utiliser (txt ou json) et de spécifier le chemin du fichier contenant le problème de Sudoku.

4. Suivez les instructions à l'écran et le programme résoudra le Sudoku en affichant la grille de problème et la grille de solution.

N'oubliez pas de remplacer mon_chemin_de_fichier par le chemin réel du fichier contenant le problème de Sudoku que vous souhaitez résoudre.

## Contribution

Les contributions sont les bienvenues ! Si vous souhaitez apporter des améliorations à ce programme Sudoku, n'hésitez pas à créer une demande d'extraction avec vos modifications.
Licence
