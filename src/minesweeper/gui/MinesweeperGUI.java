package minesweeper.gui;

import minesweeper.logic.Board;
import minesweeper.logic.Cell;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main GUI window for Minesweeper.
 */
public class MinesweeperGUI extends JFrame {
    private Board board;
    private CellButton[][] cellButtons;
    private JPanel boardPanel;
    private final int boardRows = 9;
    private final int boardCols = 9;
    private final int totalMines = 10;
    private boolean isGameOver;

    public MinesweeperGUI() {
        setupGUI();
        startNewGame();
    }

    /**
     * Configures the main game window.
     */
    private void setupGUI() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Create menu bar with Restart option.
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> startNewGame());
        gameMenu.add(restartItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Create board panel.
        boardPanel = new JPanel(new GridLayout(boardRows, boardCols));
        cellButtons = new CellButton[boardRows][boardCols];
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                CellButton button = new CellButton(row, col);
                button.setPreferredSize(new Dimension(50, 50));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isGameOver)
                            return;
                        CellButton cb = (CellButton) e.getComponent();
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            revealCell(cb.getRow(), cb.getCol());
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(cb.getRow(), cb.getCol());
                        }
                    }
                });
                cellButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        add(boardPanel);
    }

    /**
     * Initializes a new game.
     */
    private void startNewGame() {
        board = new Board(boardRows, boardCols, totalMines);
        isGameOver = false;
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                cellButtons[row][col].setBoard(board);
                cellButtons[row][col].resetButton();
            }
        }
        boardPanel.repaint();
    }

    /**
     * Reveals the cell at the given row and column.
     */
    private void revealCell(int row, int col) {
        if (row < 0 || row >= boardRows || col < 0 || col >= boardCols) {
            return;
        }
        Cell cell = board.getCell(row, col);
        CellButton btn = cellButtons[row][col];
        if (cell.isRevealed() || cell.isFlagged())
            return;
        cell.setRevealed(true);
        btn.repaint();

        if (cell.hasMine()) {
            isGameOver = true;
            showGameOverDialog(false);
            return;
        }

        // If no neighboring mines, reveal adjacent cells recursively.
        if (cell.getNeighborMineCount() == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i == row && j == col)
                        continue;
                    if (i >= 0 && i < boardRows && j >= 0 && j < boardCols) {
                        revealCell(i, j);
                    }
                }
            }
        }

        if (checkWinCondition()) {
            isGameOver = true;
            showGameOverDialog(true);
        }
    }

    /**
     * Toggles the flagged state for the cell at the given row and column.
     */
    private void toggleFlag(int row, int col) {
        if (isGameOver)
            return;
        Cell cell = board.getCell(row, col);
        if (cell.isRevealed())
            return;
        cell.setFlagged(!cell.isFlagged());
        cellButtons[row][col].repaint();
    }

    /**
     * Checks whether all non-mine cells have been revealed.
     */
    private boolean checkWinCondition() {
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.hasMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Displays a dialog indicating win or loss, and reveals all mines.
     */
    private void showGameOverDialog(boolean win) {
        String message = win ? "Congratulations! You win!" : "Game Over! You hit a mine.";
        JOptionPane.showMessageDialog(this, message);
        // Reveal all mines for visual confirmation.
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                Cell cell = board.getCell(row, col);
                if (cell.hasMine()) {
                    cell.setRevealed(true);
                    cellButtons[row][col].repaint();
                }
            }
        }
    }
}
