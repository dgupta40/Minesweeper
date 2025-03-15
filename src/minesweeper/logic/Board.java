package minesweeper.logic;

import java.util.Random;

/**
 * Represents the game board for Minesweeper.
 */
public class Board {
    private final int numRows;
    private final int numCols;
    private final int mineTotal;
    private final Cell[][] grid;

    public Board(int numRows, int numCols, int mineTotal) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.mineTotal = mineTotal;
        grid = new Cell[numRows][numCols];
        // Initialize grid with new cells
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = new Cell();
            }
        }
        distributeMines();
        computeNeighborCounts();
    }

    /**
     * Randomly distributes mines on the board.
     */
    private void distributeMines() {
        Random random = new Random();
        int placed = 0;
        while (placed < mineTotal) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            if (!grid[row][col].hasMine()) {
                grid[row][col].setMine(true);
                placed++;
            }
        }
    }

    /**
     * For each cell, calculates how many mines are in adjacent cells.
     */
    private void computeNeighborCounts() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!grid[row][col].hasMine()) {
                    int count = 0;
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = col - 1; j <= col + 1; j++) {
                            if (i >= 0 && i < numRows && j >= 0 && j < numCols && grid[i][j].hasMine()) {
                                count++;
                            }
                        }
                    }
                    grid[row][col].setNeighborMineCount(count);
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
