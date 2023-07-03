import zio._
import zio.stream._
import scala.io.Source

import java.io.{ByteArrayInputStream, IOException}
import java.nio.charset.StandardCharsets

import zio.Console
import ujson._

//Use the default template provided by ZIO to perform the application.
object Main extends ZIOAppDefault {

  //Function to solve the Sudoku
  //Function that takes a Sudoku grid (a list of integer lists) and returns a Sudoku grid option.
  //It uses a recursive approach to solving Sudoku.
  def solveSudokuTailRec(problem: List[List[Int]]): Option[List[List[Int]]] = {
    val size = 9

    //Auxiliary function used by solveSudokuTailRec.
    //It takes the coordinates of a box in the grid (row and column) as well as the grid itself.
    //It uses recursion to explore all possible values ​​to place in the current box.
    def solveSudokuHelper( row: Int, col: Int, grid: List[List[Int]]): Option[List[List[Int]]] = {
      if (row == size)
        Some(grid) // Sudoku is resolved
      else if (col == size)
        solveSudokuHelper(row + 1, 0, grid) // Go to the next line
      else if (grid(row)(col) != 0)
        solveSudokuHelper(row, col + 1, grid ) // Go to the next column if case already set
      else {
        val usedValues =
          (1 to size).filter(value => isValidNumber(grid, value, row, col))

        //Recursive auxiliary function used to iterate over the possible values ​​to place in the current box.
        //It recursively explores the different values ​​until a solution is found or no solution is possible.
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
    //A Sudoku Grid must have at least 17 values to be solve
    if (problem.flatMap(_.filter(_ != 0)).count(_ => true) < 17) {
        None
    } else {
        solveSudokuHelper(0, 0, problem)
    }
  }

  //Function that checks if a given number can be placed in a specific square of the grid without violating Sudoku rules.
  //It checks if the number does not already appear in the corresponding row, column and 3x3 subgroup.
  def isValidNumber( grid: List[List[Int]], number: Int, row: Int, col: Int ): Boolean = {
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

  // Function that prints the Sudoku grid to the console.
  // Iterates over each row and column of the grid and displays empty cells as spaces and filled cells as their values.
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

  // Function that reads a JSON file from a given file path.
  // It uses the Scala Source library to read the contents of the file and the ujson library to parse the JSON string into a data structure.
  // The function returns a list of lists of integers representing the Sudoku grid extracted from the file.
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

  // Read a text file containing a Sudoku grid.
  // It uses the Scala Source library to read the contents of the file, then splits each line into individual characters, filters out empty characters, and converts them to integers.
  // The function returns a list of lists of integers representing the Sudoku grid extracted from the file.
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

  //Use ZIO to interact with the console and perform I/O operations.
  //It asks the user to choose the file type (txt or json) and the path of the file containing the Sudoku problem.
  //Then it reads the appropriate file using the readFile or readJsonFile functions,
  // solve the Sudoku using solveSudokuTailRec, and display the problem grid and the solution grid using the printGrid functions.
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
