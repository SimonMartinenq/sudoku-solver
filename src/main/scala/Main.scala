import zio._
import zio.stream._
import scala.io.Source

import java.io.{ByteArrayInputStream, IOException}
import java.nio.charset.StandardCharsets

import zio.Console
import ujson._

//Utilise le modèle par défaut fourni par ZIO pour exécuter l'application.
object Main extends ZIOAppDefault {

  // Function to solve the Sudoku
  //Fonction qui prend une grille de Sudoku (une liste de listes d'entiers) et renvoie une option de grille de Sudoku. 
  // Elle utilise une approche récursive pour résoudre le Sudoku.
  def solveSudokuTailRec(problem: List[List[Int]]): Option[List[List[Int]]] = {
    val size = 9

    // Fonction auxiliaire utilisée par solveSudokuTailRec. 
    //Elle prend les coordonnées d'une case dans la grille (ligne et colonne) ainsi que la grille elle-même.
    //Elle utilise la récursion pour explorer toutes les possibilités de valeurs à placer dans la case courante.
    def solveSudokuHelper( row: Int, col: Int, grid: List[List[Int]]): Option[List[List[Int]]] = {
      if (row == size)
        Some(grid) // Sudoku is resolved
      else if (col == size)
        solveSudokuHelper(row + 1, 0, grid) // Go to the next line
      else if (grid(row)(col) != 0)
        solveSudokuHelper(
          row,
          col + 1,
          grid
        ) // Go to the next column if case already set
      else {
        val usedValues =
          (1 to size).filter(value => isValidNumber(grid, value, row, col))

        // Fonction auxiliaire récursive utilisée pour itérer sur les valeurs possibles à placer dans la case courante. 
        //Elle explore récursivement les différentes valeurs jusqu'à ce qu'une solution soit trouvée ou qu'aucune solution ne soit possible.
        def loop(values: List[Int]): Option[List[List[Int]]] = values match {
          case Nil => None // No value is possible
          case value :: tail =>
            val updatedGrid = grid.updated(row, grid(row).updated(col, value))
            solveSudokuHelper(row, col + 1, updatedGrid) match {
              case Some(solution) =>
                Some(solution) // Solution found
              case None => loop(tail) // Try next value
            }
        }

        loop(usedValues.toList)
      }
    }
    if (problem.forall(_.forall(_ == 0))) {
        Some(List())
    } else {
        solveSudokuHelper(0, 0, problem)
    }
  }

  //Fonction qui vérifie si un nombre donné peut être placé dans une case spécifique de la grille sans violer les règles du Sudoku. 
  //Elle vérifie si le nombre n'apparaît pas déjà dans la ligne, la colonne et le sous-groupe 3x3 correspondant.
  def isValidNumber(
      grid: List[List[Int]],
      number: Int,
      row: Int,
      col: Int
  ): Boolean = {
    val size = 9

    // Check the line
    val rowHasNumber = grid(row).contains(number)

    // Check the column
    val colHasNumber = grid.exists(row => row(col) == number)

    // Check the 3x3 subgrid
    val subgridRow = row - row % 3
    val subgridCol = col - col % 3
    val subgridHasNumber =
      grid.slice(subgridRow, subgridRow + 3).exists { row =>
        row.slice(subgridCol, subgridCol + 3).contains(number)
      }

    !rowHasNumber && !colHasNumber && !subgridHasNumber
  }

  // Fonction qui imprime la grille de Sudoku sur la console. 
  // Elle itère sur chaque ligne et colonne de la grille et affiche les cellules vides comme des espaces et les cellules remplies avec leurs valeurs.
  def printGrid(grid: List[List[Int]]): Unit = {
    for (row <- grid.indices) {
      if (row % 3 == 0) {
        println("+-------+-------+-------+")
      }
      for (col <- grid(row).indices) {
        if (col % 3 == 0) {
          print("| ")
        }
        val cell = grid(row)(col)
        if (cell == 0) {
          print("  ")
        } else {
          print(cell + " ")
        }
      }
      println("|")
    }
    println("+-------+-------+-------+")
  }

  // Fonction qui lit un fichier JSON à partir d'un chemin de fichier donné. 
  // Elle utilise la bibliothèque Scala Source pour lire le contenu du fichier et la bibliothèque ujson pour analyser la chaîne JSON en une structure de données. 
  // La fonction renvoie une liste de listes d'entiers représentant la grille de Sudoku extraite du fichier.
  def readJsonFile(path: String): List[List[Int]] = {
    try {
      val jsonString = Source.fromFile(path).mkString
      val data = ujson.read(jsonString)
      data.arr.toList.map(_.arr.toList.map(_.num.toInt))
    } catch {
      case e: IOException =>
        println(s"Erreur lors de la lecture du fichier: ${e.getMessage}")
        List.empty
    }
  }

  // Lit un fichier texte contenant une grille de Sudoku. 
  // Elle utilise la bibliothèque Scala Source pour lire le contenu du fichier, puis divise chaque ligne en caractères individuels, filtre les caractères vides et les convertit en entiers. 
  // La fonction renvoie une liste de listes d'entiers représentant la grille de Sudoku extraite du fichier.
  def readTxtFile(filePath: String): List[List[Int]] = {
    try {
      val source = Source
        .fromFile(filePath)
        .getLines()
        .map { line =>
          line.split("").filter(_.nonEmpty).map(_.toInt).toList
        }
        .toList
      source
    } catch {
      case e: IOException =>
        println(s"Erreur lors de la lecture du fichier: ${e.getMessage}")
        List.empty
    }
  }

  //utilise ZIO pour interagir avec la console et effectuer les opérations d'entrée/sortie. 
  //Elle demande à l'utilisateur de choisir le type de fichier (txt ou json) et le chemin du fichier contenant le problème de Sudoku. 
  //Ensuite, elle lit le fichier approprié en utilisant les fonctions readFile ou readJsonFile, 
  //résout le Sudoku en utilisant solveSudokuTailRec, et affiche la grille de problème et la grille de solution en utilisant les fonctions printGrid.
  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- Console.printLine(
        "Veuillez choisir le type de fichier à utiliser (txt ou json):"
      )
      fileType <- Console.readLine
      _ <- Console.printLine(s"Vous avez choisi: $fileType")
      _ <- Console.printLine(
        "Entrez le chemin du fichier contenant le problème de Sudoku:"
      )
      path <- Console.readLine
      _ <- Console.printLine(s"Vous avez entré: $path")
      // Add your Sudoku solver logic here, utilizing ZIO and interacting with the ZIO Console
      _ <- ZIO.succeed {
        val problem = fileType match {
          case "txt"  => readTxtFile(path)
          case "json" => readJsonFile(path)
          case _ =>
            throw new IllegalArgumentException(
              "Le type de fichier doit être txt ou json"
            )
        }
        val solution = solveSudokuTailRec(problem)
        solution match {
          case Some(grid) =>
            println("Problème:")
            printGrid(problem)
            println("Solution:")
            printGrid(grid)
          case None =>
            println("Pas de solution trouvée.")
        }
      }
    } yield ()

}
