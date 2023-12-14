/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #9
 * 1 - 5026221131 - Maulina Nur Laila
 * 2 - 5026221172 - Arya Putra Tsabitah Firjatulloh
 * 3 - 5026221179 - Kadek Mawar Kumala Dewi
 */

import java.util.Collections;
import java.util.Stack;
import java.util.List;

public class Puzzle {
    public static final int GRID_SIZE = 9;
    int[][] numbers = new int[GRID_SIZE][GRID_SIZE];
    boolean[][] isGiven = new boolean[GRID_SIZE][GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
                isGiven[row][col] = false;
            }
        }
        solveSudoku();
        setGuesses(cellsToGuess);
        randomNumbers();
    }

    private boolean randomNumbers() {
        Stack<Integer> numStack = new Stack<>();
        for (int i = 1; i <= SudokuConstants.GRID_SIZE; i++) {
            numStack.push(i);
        }

        Collections.shuffle(numStack);
        return fillSudoku(numStack);
    }

      private boolean fillPuzzle(Stack<Integer> numStack) {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                numbers[row][col] = 0;
            }
        }
        return fillPuzzleRec(0, 0, numStack);
    }
    
     private boolean fillPuzzleRec(int startRow, int startCol, Stack<Integer> numStack) {
        int row = startRow;
        int col = startCol;

        if (row == GRID_SIZE - 1 && col == GRID_SIZE) {
            return true;
        }
        if (col == GRID_SIZE) {
            row++;
            col = 0;
        }

        for (int num : numStack) {
            if (isNumberPlacementValid(row, col, num)) {
                numbers[row][col] = num;
                if (fillPuzzleRec(row, col + 1, numStack)) {
                    return true;
                }
                numbers[row][col] = 0;
            }
        }
        return false;
    }
    
    private boolean isNumberValidInRow(int row, int num) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if (numbers[row][col] == num) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isNumberValidInColumn(int col, int num) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (numbers[row][col] == num) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isNumberValidInSubgrid(int startRow, int startCol, int num) {
        for (int row = 0; row < SudokuConstants.SUBGRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.SUBGRID_SIZE; col++) {
                if (numbers[startRow + row][startCol + col] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isNumberPlacementValid(int row, int col, int num) {
        return isNumberValidInRow(row, num) && isNumberValidInColumn(col, num) && isNumberValidInSubgrid(row - row % SudokuConstants.SUBGRID_SIZE, col - col % SudokuConstants.SUBGRID_SIZE, num);
    }
    
    private void solutionSudoku() {
        solution();
    }

    private boolean solution() {
        Stack<Cell> cellStack = new Stack<>();
        int curRow = 0, curCol = 0, curValue = 1;

        while (cellStack.size() < GRID_SIZE * GRID_SIZE) {
            if (isGiven[curRow][curCol]) {
                cellStack.push(new Cell(curRow, curCol, numbers[curRow][curCol]));
                int[] next = getNextCell(curRow, curCol);
                curRow = next[0];
                curCol = next[1];
                continue;
            }

            boolean foundValidValue = false;
            while (curValue <= GRID_SIZE) {
                if (isValidPlacement(curRow, curCol, curValue)) {
                    foundValidValue = true;
                    break;
                }
                curValue++;
            }

            if (foundValidValue && curValue <= GRID_SIZE) {
                numbers[curRow][curCol] = curValue;
                cellStack.push(new Cell(curRow, curCol, curValue));
                int[] next = getNextCell(curRow, curCol);
                curRow = next[0];
                curCol = next[1];
                curValue = 1;
            } else {
                if (!cellStack.isEmpty()) {
                    Cell cell = cellStack.pop();
                    while (isGiven[cell.getRow()][cell.getCol()]) {
                        if (!cellStack.isEmpty()) {
                            cell = cellStack.pop();
                        } else {
                            System.out.println("Number of steps: " + time);
                            return false;
                        }
                    }
                    curRow = cell.getRow();
                    curCol = cell.getCol();
                    curValue = cell.getValue() + 1;
                    numbers[curRow][curCol] = 0;
                } else {
                    System.out.println("Number of steps: " + time);
                    return false;
                }
            }
        }
        return true;
    }

    private void setGuesses(int cellsToGuess) {
        int targetFilledCells = cellsToGuess + (GRID_SIZE * GRID_SIZE) / 2;
        Stack<Integer> indexes = new Stack<>();
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            indexes.push(i);
        }
        Collections.shuffle(indexes);

        int filledCells = 0;
        while (!indexes.isEmpty() && filledCells < targetFilledCells) {
            int idx = indexes.pop();
            int row = idx / GRID_SIZE;
            int col = idx % GRID_SIZE;
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
                filledCells++;
            }
        }
    }

    private boolean isValidPlacement(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[row][i] == num || numbers[i][col] == num) {
                return false;
            }
        }

        int subgridRowStart = row - row % SudokuConstants.SUBGRID_SIZE;
        int subgridColStart = col - col % SudokuConstants.SUBGRID_SIZE;
        for (int i = subgridRowStart; i < subgridRowStart + SudokuConstants.SUBGRID_SIZE; i++) {
            for (int j = subgridColStart; j < subgridColStart + SudokuConstants.SUBGRID_SIZE; j++) {
                if (numbers[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] getNextCell(int row, int col) {
        int[] nextCell = new int[2];
        col++;
        if (col == GRID_SIZE) {
            col = 0;
            row++;
        }
        nextCell[0] = row;
        nextCell[1] = col;
        return nextCell;
    }
}
