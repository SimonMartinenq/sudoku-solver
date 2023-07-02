import munit._
import Main.solveSudokuTailRec
import Main.printGrid

class SudokuNoSolutionSpec extends munit.FunSuite {
  test("No solution test for a Sudoku Grid") {
    val inputGrid = List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(5, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
  )

    val expectedSolution = List()

    val result = solveSudokuTailRec(inputGrid).getOrElse(List.empty[List[Int]])
    assertEquals(result, expectedSolution)
  }
}
