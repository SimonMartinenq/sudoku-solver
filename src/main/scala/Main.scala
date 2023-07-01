package sudoku

import zio._
import zio.Console
import ujson._
import scala.io.Source

object Main extends ZIOAppDefault {

  //My first Sudoku Grid
  // val problem = List(
  //   List(5, 3, 0, 0, 7, 0, 0, 0, 0),
  //   List(6, 0, 0, 1, 9, 5, 0, 0, 0),
  //   List(0, 9, 8, 0, 0, 0, 0, 6, 0),
  //   List(8, 0, 0, 0, 6, 0, 0, 0, 3),
  //   List(4, 0, 0, 8, 0, 3, 0, 0, 1),
  //   List(7, 0, 0, 0, 2, 0, 0, 0, 6),
  //   List(0, 6, 0, 0, 0, 0, 2, 8, 0),
  //   List(0, 0, 0, 4, 1, 9, 0, 0, 5),
  //   List(0, 0, 0, 0, 8, 0, 0, 7, 9)
  // )

  //Function to solve the Sudoku
  def solveSudokuTailRec(problem: List[List[Int]]): Option[List[List[Int]]] = {
    val size = 9

    def solveSudokuHelper(row: Int, col: Int, grid: List[List[Int]]): Option[List[List[Int]]] = {
      if (row == size)
        Some(grid) // Sudoku is resolved
      else if (col == size)
        solveSudokuHelper(row + 1, 0, grid) // Go to the next line
      else if (grid(row)(col) != 0)
        solveSudokuHelper(row, col + 1, grid) // Go to the next column if case already set
      else {
        val usedValues = (1 to size).filter(value => isValidNumber(grid, value, row, col))

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

  def isValidNumber(grid: List[List[Int]], number: Int, row: Int, col: Int): Boolean = {
    val size = 9

    // Check the line
    val rowHasNumber = grid(row).contains(number)

    // Check the column
    val colHasNumber = grid.exists(row => row(col) == number)

    // Check the 3x3 subgrid
    val subgridRow = row - row % 3
    val subgridCol = col - col % 3
    val subgridHasNumber = grid.slice(subgridRow, subgridRow + 3).exists { row =>
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
  //json to List(List(Int))
  def readJsonFile(path: String): List[List[Int]] = {
    val jsonString = Source.fromFile(path).mkString
    val data = ujson.read(jsonString)
    data.arr.toList.map(_.arr.toList.map(_.num.toInt))
  }

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem:")
      path <- Console.readLine
      _ <-  Console.printLine(s"You entered: $path")
      // Add your Sudoku solver logic here, utilizing ZIO and interacting with the ZIO Console
      _ <- ZIO.succeed {
        val problem = readJsonFile(path)
        val solution = solveSudokuTailRec(problem)
        solution match {
          case Some(grid) =>
            println("Problem:")
            printGrid(problem)
            println("Solution:")
            printGrid(grid)
          case None =>
            println("Pas de solution trouvée.")
        }
      }
    } yield ()
}