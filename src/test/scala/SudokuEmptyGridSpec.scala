import munit._
import Main.solveSudokuTailRec
import Main.printGrid

class SudokuEmptyGridSpec extends munit.FunSuite {
  test("empty Grid input for Sudoku test") {
    val inputGrid = List(
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0), 
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0)
  )

    val expectedSolution = List()

    val result = solveSudokuTailRec(inputGrid).getOrElse(List.empty[List[Int]])
    assertEquals(result, expectedSolution)
  }
}
