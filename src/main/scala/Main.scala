package sudoku

import zio._
import zio.Console._
import zio.stream._
import scala.io.Source

import java.io.{ByteArrayInputStream, IOException}
import java.nio.charset.StandardCharsets

object Main extends ZIOAppDefault {

  def readFile(filePath: String): List[List[Int]] = {
  val source = Source.fromFile(filePath).getLines().map { line =>
    line.split(" ").filter(_.nonEmpty).map(_.toInt).toList}.toList
  source

}

  def run: ZIO[Any, Throwable, Unit] =
    val gridFolderPath = "src/grid/"
    
    val result = for {
      _ <- Console.print("Enter the name to the txt file containing the Sudoku problem:")
      fileName <- Console.readLine
      _ <-  Console.printLine(s"You entered: $fileName")
     } yield {
      println(readFile(gridFolderPath + fileName + ".txt"))
     }
     result
  }