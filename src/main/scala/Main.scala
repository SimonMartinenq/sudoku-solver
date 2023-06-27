package sudoku

import zio._
import zio.Console._
import zio.nio.file.Files
import zio.nio.core.file.Path
import zio.connect.file._
import zio.stream._
import scala.io.Source

import java.io.{ByteArrayInputStream, IOException}
import java.nio.charset.StandardCharsets

object Main extends ZIOAppDefault {

  def readFile(filePath: String): String = {
  val source = Source.fromFile(filePath)
  val content = source.mkString
  source.close()
  content
}


  def run: ZIO[Any, Throwable, Unit] =
    val gridFolderPath = "src/grid/"
    //val fileContent = readFile(filePath)
    
    val result = for {
      _ <- Console.print("Enter the path to the txt file containing the Sudoku problem:")
      fileName <- Console.readLine
      _ <-  Console.printLine(s"You entered: $fileName")
     } yield {
      println(readFile(gridFolderPath + fileName + ".txt"))
     }
     result
    }