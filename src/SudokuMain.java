/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #9
 * 1 - 5026221131 - Maulina Nur Laila
 * 2 - 5026221172 - Arya Putra Tsabitah Firjatulloh
 * 3 - 5026221179 - Kadek Mawar Kumala Dewi
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnRestart = new JButton("Restart"); // New button for restart
    JLabel timerLabel = new JLabel("Timer: 0 seconds");
    String playerName;

    // Timer variables
    private Timer timer;
    private int seconds;


    // Constructor
    public SudokuMain() {
        // Prompt the user to enter their name
        playerName = JOptionPane.showInputDialog(this, "Enter your name:");

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);

        // Add buttons and timer label to the south to re-start the game via board.newGame() and restart
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnRestart);
        buttonPanel.add(timerLabel);
        cp.add(buttonPanel, BorderLayout.EAST);


        // Add ActionListener for New Game button
        btnNewGame.addActionListener(e -> startNewGame());

        // Add ActionListener for Restart button
        btnRestart.addActionListener(e -> restartGame());

        // Initialize the game board and timer
        initializeTimer();
        startTimer();
        board.newGame();

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    // Method to start a new game
    private void startNewGame() {
        // Prompt the user to enter their name if not provided
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        }

        restartTimer();
        board.newGame();
    }

    // Method to restart the game
    private void restartGame() {
        restartTimer();
        board.newGame();
        JOptionPane.showMessageDialog(this, "Game Restarted!");
    }

    // Method to initialize the timer
    private void initializeTimer() {
        seconds = 0;
        timer = new Timer(1000, e -> {
            seconds++;
            updateTimerLabel();
        });
    }

    // Method to start the timer
    private void startTimer() {
        timer.start();
    }

    // Method to restart the timer
    private void restartTimer() {
        timer.stop();
        seconds = 0;
        updateTimerLabel();
        timer.start();
    }

    // Method to update the timer label
    private void updateTimerLabel() {
        timerLabel.setText("Timer: " + seconds + " seconds");
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuMain();  // Let the constructor do the job
            }
        });
    }
}