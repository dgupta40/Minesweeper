package minesweeper.logic;

/**
 * Represents a single cell on the Minesweeper board.
 */
public class Cell {
    private boolean hasMine;
    private boolean revealed;
    private boolean flagged;
    private int neighborMineCount;

    public Cell() {
        this.hasMine = false;
        this.revealed = false;
        this.flagged = false;
        this.neighborMineCount = 0;
    }

    // Getters and setters

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean mine) {
        this.hasMine = mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getNeighborMineCount() {
        return neighborMineCount;
    }

    public void setNeighborMineCount(int count) {
        this.neighborMineCount = count;
    }
}
