import munit._
import Main.solveSudokuTailRec
import Main.printGrid

class SudokuSolverSpec extends munit.FunSuite {
  test("solveSudoku solves a Sudoku puzzle") {
    val inputGrid = List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
  )

    val expectedSolution = List(
        List(5, 3, 4, 6, 7, 8, 9, 1, 2),
        List(6, 7, 2, 1, 9, 5, 3, 4, 8),
        List(1, 9, 8, 3, 4, 2, 5, 6, 7),
        List(8, 5, 9, 7, 6, 1, 4, 2, 3),
        List(4, 2, 6, 8, 5, 3, 7, 9, 1),
        List(7, 1, 3, 9, 2, 4, 8, 5, 6),
        List(9, 6, 1, 5, 3, 7, 2, 8, 4),
        List(2, 8, 7, 4, 1, 9, 6, 3, 5),
        List(3, 4, 5, 2, 8, 6, 1, 7, 9)
      
    )

    val result = solveSudokuTailRec(inputGrid).getOrElse(List.empty[List[Int]])

    assertEquals(result, expectedSolution)
  }
}
