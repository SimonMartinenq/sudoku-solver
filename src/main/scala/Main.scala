import zio._
import zio.Console

object Main extends ZIOAppDefault {

  //My first Sudoku Grid
  val problem = Array(
    Array(5, 3, 0, 0, 7, 0, 0, 0, 0),
    Array(6, 0, 0, 1, 9, 5, 0, 0, 0),
    Array(0, 9, 8, 0, 0, 0, 0, 6, 0),
    Array(8, 0, 0, 0, 6, 0, 0, 0, 3),
    Array(4, 0, 0, 8, 0, 3, 0, 0, 1),
    Array(7, 0, 0, 0, 2, 0, 0, 0, 6),
    Array(0, 6, 0, 0, 0, 0, 2, 8, 0),
    Array(0, 0, 0, 4, 1, 9, 0, 0, 5),
    Array(0, 0, 0, 0, 8, 0, 0, 7, 9)
  )

  //Function to solve the Sudoku
  def solveSudokuTailRec( problem: Array[Array[Int]] ): Option[Array[Array[Int]]] = {
    val size = 9

    def solveSudokuHelper(row: Int, col: Int, grid: Array[Array[Int]] ): Option[Array[Array[Int]]] = {
      if (row == size)
        Some(grid) // Sudoku is resolve
      else if (col == size)
        solveSudokuHelper(row + 1, 0, grid) // Go to the next line
      else if (grid(row)(col) != 0)
        solveSudokuHelper( row, col + 1, grid ) // Go to the next column if case already set
      else {
        val usedValues = (1 to size).filter(value => isValidNumber(grid, value, row, col))

        def loop(values: List[Int]): Option[Array[Array[Int]]] = values match {
          case Nil => None // No value is possible
          case value :: tail =>
            val updatedGrid = grid.updated(row, grid(row).updated(col, value))
            solveSudokuHelper(row, col + 1, updatedGrid) match {
              case Some(solution) =>
                Some(solution) // Solution find
              case None => loop(tail) // Try next value
            }
        }

        loop(usedValues.toList)
      }
    }

    solveSudokuHelper(0, 0, problem)
  }

  def isValidNumber( grid: Array[Array[Int]], number: Int, row: Int, col: Int ): Boolean = {
    val size = 9

    // Check the line
    val rowHasNumber = (0 until size).exists(c => grid(row)(c) == number)

    // Check the column
    val colHasNumber = (0 until size).exists(r => grid(r)(col) == number)

    // Check the 3x3 subgrid
    val subgridRow = row - row % 3
    val subgridCol = col - col % 3
    val subgridHasNumber = (subgridRow until subgridRow + 3).exists { r =>
      (subgridCol until subgridCol + 3).exists(c => grid(r)(c) == number)
    }

    !rowHasNumber && !colHasNumber && !subgridHasNumber
  }

  //Function to print the Sudoku Grid
  def printGrid(grid: Array[Array[Int]]): Unit = {
    for (row <- grid) {
      for (cell <- row) {
        print(cell + "  ")
      }
      println()
    }
  }

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem:")
      path <- Console.readLine
      _ <- Console.printLine(s"You entered: $path")
      // Add your Sudoku solver logic here, utilizing ZIO and interacting with the ZIO Console
      _ <- ZIO.succeed {
        val solution = solveSudokuTailRec(problem)

        solution match {
          case Some(grid) =>
            println("Solution:")
            printGrid(grid)
          case None =>
            println("Pas de solution trouvée.")
        }
      }
    } yield ()
}
