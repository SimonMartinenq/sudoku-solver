import munit._
import Main.solveSudokuTailRec
import Main.printGrid

class SudokuPartiallyFieldGrid extends munit.FunSuite {
  test("test Partially Field Grid only 2 values") {
    val inputGrid = List(
        List(5, 3, 0, 0, 0, 0, 0, 0, 0),
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

  test("test Partially Field Grid only 16 values") {
    val inputGrid = List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0),
        List(8, 0, 0, 0, 0, 0, 0, 0, 0), 
        List(4, 0, 0, 0, 0, 0, 0, 0, 0),
        List(7, 0, 0, 0, 2, 0, 0, 0, 0),
        List(0, 6, 0, 0, 0, 0, 0, 0, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
  )

    val expectedSolution = List()

    val result = solveSudokuTailRec(inputGrid).getOrElse(List.empty[List[Int]])
    assertEquals(result, expectedSolution)

  }

}
