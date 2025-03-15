package minesweeper.gui;

import java.awt.*;
import javax.swing.*;
import minesweeper.logic.Board;
import minesweeper.logic.Cell;

/**
 * Custom button that represents a Minesweeper cell.
 */
public class CellButton extends JButton {
    private final int cellRow;
    private final int cellCol;
    private Board board;

    public CellButton(int row, int col) {
        this.cellRow = row;
        this.cellCol = col;
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    public int getRow() {
        return cellRow;
    }

    public int getCol() {
        return cellCol;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Resets the button to its default state.
     */
    public void resetButton() {
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLACK);
        setEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null)
            return;
        Cell cell = board.getCell(cellRow, cellCol);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!cell.isRevealed()) {
            // If cell is not revealed, show flag if flagged or a plain background.
            if (cell.isFlagged()) {
                drawFlag(g2);
            } else {
                g2.setColor(Color.GRAY);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        } else {
            // Revealed cell: if mine, draw bomb; otherwise, draw diamond and number if
            // applicable.
            if (cell.hasMine()) {
                drawBomb(g2);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                drawDiamond(g2);
                if (cell.getNeighborMineCount() > 0) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    String countStr = Integer.toString(cell.getNeighborMineCount());
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(countStr);
                    int textHeight = fm.getAscent();
                    g2.drawString(countStr, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
                }
            }
        }
    }

    /**
     * Draws a diamond shape to represent a safe cell.
     */
    private void drawDiamond(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        int[] xPoints = { w / 2, w - 5, w / 2, 5 };
        int[] yPoints = { 5, h / 2, h - 5, h / 2 };
        g2.setColor(new Color(173, 216, 230)); // Personal touch: light blue diamond.
        g2.fillPolygon(xPoints, yPoints, 4);
    }

    /**
     * Draws a bomb symbol for a mine.
     */
    private void drawBomb(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        int diameter = Math.min(w, h) - 10;
        g2.setColor(Color.BLACK);
        g2.fillOval((w - diameter) / 2, (h - diameter) / 2, diameter, diameter);
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        // Draw an "X" over the bomb.
        g2.drawLine((w - diameter) / 2, (h - diameter) / 2, (w + diameter) / 2, (h + diameter) / 2);
        g2.drawLine((w + diameter) / 2, (h - diameter) / 2, (w - diameter) / 2, (h + diameter) / 2);
    }

    /**
     * Draws a flag for flagged cells.
     */
    private void drawFlag(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(w / 2 - 2, 5, 4, h - 10);
        g2.setColor(Color.RED);
        int[] xPoints = { w / 2 + 2, w / 2 + 20, w / 2 + 2 };
        int[] yPoints = { 5, h / 2, h / 2 + 15 };
        g2.fillPolygon(xPoints, yPoints, 3);
    }
}
