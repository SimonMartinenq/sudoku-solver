import zio._
import zio.stream._
import scala.io.Source

import java.io.{ByteArrayInputStream, IOException}
import java.nio.charset.StandardCharsets

import zio.Console
import ujson._

object Main extends ZIOAppDefault {

  // Function to solve the Sudoku
  def solveSudokuTailRec(problem: List[List[Int]]): Option[List[List[Int]]] = {
    val size = 9

    def solveSudokuHelper(
        row: Int,
        col: Int,
        grid: List[List[Int]]
    ): Option[List[List[Int]]] = {
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

    solveSudokuHelper(0, 0, problem)
  }

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

  // Function to print the Sudoku Grid
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
  // json to List(List(Int))
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

  def readFile(filePath: String): List[List[Int]] = {
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
          case "txt"  => readFile(path)
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
