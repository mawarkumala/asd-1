/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #9
 * 1 - 5026221131 - Maulina Nur Laila
 * 2 - 5026221172 - Arya Putra Tsabitah Firjatulloh
 * 3 - 5026221179 - Kadek Mawar Kumala Dewi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;


public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();
    private boolean helpMode;

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel

                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Add right and bottom borders for grid separation
                if ((col + 1) % 3 == 0 && col < SudokuConstants.GRID_SIZE - 1) {
                    Border border = new MatteBorder(0, 0, 0, 2, Color.BLACK);
                    cells[row][col].setBorder(new CompoundBorder(cells[row][col].getBorder(), border));
                }
                if ((row + 1) % 3 == 0 && row < SudokuConstants.GRID_SIZE - 1) {
                    Border border = new MatteBorder(0, 0, 2, 0, Color.BLACK);
                    cells[row][col].setBorder(new CompoundBorder(cells[row][col].getBorder(), border));
                }

                // Add action listener to editable cells
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(new CellInputListener());
                }
            }
        }
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        super.setBorder(new LineBorder(Color.BLACK, 3));
    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle
        puzzle.newPuzzle(2);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }



    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    public void restartGame() {
    }

    public void setHelpMode(boolean helpMode) {
        this.helpMode = helpMode;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener, KeyListener {
        private boolean helpMode;

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();
            int numberIn = Integer.parseInt(sourceCell.getText());
            System.out.println(numberIn + " You Have Entered");
            handleCellInput(sourceCell);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Handle key events (e.g., backspace, delete)
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Handle key events (e.g., arrow keys)
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Handle key events (e.g., enter key)
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Cell sourceCell = (Cell) e.getSource();
                handleCellInput(sourceCell);
            }
        }

        public void setHelpMode(boolean helpMode) {
            this.helpMode = helpMode;
        }


        private void handleCellInput(Cell sourceCell) {
            // Retrieve the input from the cell
            String input = sourceCell.getText();

            // Check if the input is a single digit
            if (input.length() == 1 && Character.isDigit(input.charAt(0))) {
                int numberIn = Integer.parseInt(input);

                // Check if the input is a valid single digit (1-9)
                if (numberIn >= 1 && numberIn <= 9) {
                    // Update the cell status and repaint
                    if (numberIn == sourceCell.number) {
                        sourceCell.status = CellStatus.CORRECT_GUESS;
                    } else {
                        sourceCell.status = CellStatus.WRONG_GUESS;
                    }
                    sourceCell.paint();

                    // Check if the player has solved the puzzle after this move
                    if (isSolved()) {
                        JOptionPane.showMessageDialog(null, "Congratulations!");
                    }
                }
            }
        }
    }


}