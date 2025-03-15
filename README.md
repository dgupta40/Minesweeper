# Minesweeper Board Game in Java

 This repository has a complete source code of a Minesweeper board game written in Java. The  project was done for the CS 220 course which mainly focused on applied data structures and object oriented  design. The game has a graphical user interface (GUI) which is built using Swing, Java2D  for custom cell rendering and the whole code is structured modularly, based on projects like Boggle,  Pong and DrawShapes.


## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [ Code Explanation](#detailed-code-explanation)
  - [Game Logic](#game-logic)
  - [Graphical User Interface](#graphical-user-interface)
- [How to Compile and Run](#how-to-compile-and-run)

## Overview

Minesweeper is a grid-based puzzle game where the player’s goal is to reveal all cells that do not contain mines. If the player reveals a cell that contains a mine, the game is over. Cells that do not contain a mine display a number indicating how many neighboring cells contain mines.

- **Data structures:** Using a 2D array for the game board and applying recursive algorithms for revealing adjacent cells.

## Project Structure


- **Main.java:**  
  Entry point of the application. It initializes and displays the Minesweeper GUI.

- **minesweeper/logic/Cell.java:**  
  Defines the `Cell` class representing each individual cell on the board. It stores the cell’s state (mine, revealed, flagged) and the number of adjacent mines.

- **minesweeper/logic/Board.java:**  
  Manages the game board by:
  - Creating a 2D array of `Cell` objects.
  - Randomly placing mines.
  - Calculating the number of adjacent mines for each cell.
  
- **minesweeper/gui/MinesweeperGUI.java:**  
  Implements the main GUI window using Swing. This class:
  - Sets up a menu bar with a "Restart" option.
  - Lays out the game board using a grid of custom buttons (`CellButton`).
  - Handles mouse events to reveal cells or toggle flags.
  - Checks win/loss conditions and displays messages accordingly.

- **minesweeper/gui/CellButton.java:**  
  A custom subclass of `JButton` that overrides `paintComponent` to draw:
  - A bomb symbol if a mine is revealed.
  - A flag if the cell is flagged.
  - A diamond for safe cells.
  - The number of adjacent mines (if greater than zero) centered within the cell.

## Code Explanation



### Game Logic

- **Cell.java:**  
  Each cell contains:
  - A boolean flag (`hasMine`) indicating if it is a mine.
  - A boolean flag (`revealed`) indicating if it has been revealed.
  - A boolean flag (`flagged`) indicating if it is flagged by the player.
  - An integer (`neighborMineCount`) representing the count of adjacent mines.
  
  These properties are encapsulated with getters and setters, allowing the Board class to manage the game state effectively.

- **Board.java:**  
  The `Board` class is responsible for initializing the game:
  - **Grid Initialization:**  
    A 2D array of `Cell` objects is created, representing the game grid.
  - **Mine Distribution:**  
    Mines are randomly distributed across the board using the `distributeMines()` method.
  - **Neighbor Count Calculation:**  
    The method `computeNeighborCounts()` calculates the number of mines surrounding each cell (that is not a mine), which is then stored in `neighborMineCount`.

### Graphical User Interface

- **MinesweeperGUI.java:**  
  This class sets up the game window:
  - **Window Setup:**  
    Creates a JFrame with a specified size, centers the window, and sets the close operation.
  - **Menu Bar:**  
    A menu bar with a "Restart" option is added, allowing the player to reset the game.
  - **Grid Layout:**  
    The board is displayed using a `GridLayout` with custom `CellButton` components corresponding to each cell.
  - **Event Handling:**  
    Mouse listeners are added to each button:
    - **Left-click:** Reveals the cell. If the cell contains a mine, the game ends; otherwise, it reveals the number of adjacent mines or recursively reveals neighboring cells.
    - **Right-click:** Toggles a flag on the cell.
  - **Game State:**  
    The GUI checks for win conditions after each move and uses dialog boxes (via `JOptionPane`) to inform the player of a win or game over.

- **CellButton.java:**  
  This component customizes the appearance of each cell:
  - **Custom Painting:**  
    The `paintComponent` method is overridden to draw the cell based on its state:
    - **Unrevealed Cells:**  
      Display a gray background or a flag if flagged.
    - **Revealed Cells:**  
      If the cell is a mine, a bomb is drawn; otherwise, a diamond is drawn, and if there are adjacent mines, the count is drawn in the center.
  - **Graphics Rendering:**  
    Java2D is used with anti-aliasing for smoother visual effects, providing a unique and polished look to the game.

## How to Compile and Run

1. **Compile the Code:**  
   Open a terminal in the project’s root directory and run:
   ```bash
   javac -d bin src/minesweeper/Main.java src/minesweeper/logic/Cell.java src/minesweeper/logic/Board.java src/minesweeper/gui/MinesweeperGUI.java src/minesweeper/gui/CellButton.java
