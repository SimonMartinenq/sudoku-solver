# Sudoku Solver

This program is a Sudoku solver written in Scala using ZIO. It is able to solve Sudoku puzzles provided as text files or JSON files.

## Setting up the development environment
### Prerequisites

Before you begin, make sure you have the following installed on your system:

    [Scala (version 2.13.0 ou supérieure)](https://docs.scala-lang.org/fr/tour/tour-of-scala.html) 
    [sbt (version 1.5.0 ou supérieure)](https://www.scala-sbt.org/) 
    [VS Code](https://code.visualstudio.com)
    [Metals (extension VS Code)](https://scalameta.org/metals/docs/editors/vscode/) 

### Configuring Metals in VS Code

    Open VS Code and install the "Metals" extension from the Extensions Market.
    Once the extension is installed, restart VS Code.
    Open the folder containing the Sudoku program source code.
    When you open the folder, Metals automatically detects that it is a Scala project and performs the necessary configuration steps.
    Wait for Metals to complete the process of indexing the project and resolving dependencies.

You should now be able to use all the features of Metals to develop the Sudoku program, including autocompletion, code navigation, and debugging.

## Exécution du programme

Pour exécuter le programme, suivez les étapes suivantes :

1. Option 1
- Open a terminal in VS Code using the Ctrl+` key combination (or by going to the "Terminal" > "New Terminal" menu).
- In the terminal, run the following command to compile the program:
  ```bash
    sbt compile
  ```
- Once the compilation is complete, run the following command to run the program :
  ```bash
    sbt run
  ```
1. Option 2

2. Press run at the Main declaration

3. The program will ask you to choose the type of file to use (txt or json) and specify the path to the file containing the Sudoku problem.

4. Follow the on-screen instructions and the program will solve the Sudoku by displaying the problem grid and the solution grid.

Remember to replace my_file_path with the actual path of the file containing the Sudoku problem you want to solve.

## Contribution

Contributions are welcome! If you want to make improvements to this Sudoku program, feel free to create a pull request with your changes.
Licence
