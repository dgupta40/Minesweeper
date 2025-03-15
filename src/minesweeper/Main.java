package minesweeper;

import javax.swing.SwingUtilities;
import minesweeper.gui.MinesweeperGUI;

/**
 * Main entry point for the Minesweeper game.
 */
public class Main {
    public static void main(String[] args) {
        // Launch the GUI on the Swing event dispatch thread.
        SwingUtilities.invokeLater(() -> {
            MinesweeperGUI gameWindow = new MinesweeperGUI();
            gameWindow.setVisible(true);
        });
    }
}
