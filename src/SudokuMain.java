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

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JLabel timerLabel = new JLabel("Timer: 0 seconds");
    String playerName;
    private Timer timer;
    private int seconds;

    // Constructor
    public SudokuMain() {
        JOptionPane.showMessageDialog(null, "Welcome! click OK to start game! Sudoko");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewGame);
        buttonPanel.add(timerLabel);
        cp.add(buttonPanel, BorderLayout.EAST);
        
        btnNewGame.addActionListener(e -> startNewGame());
        
        initializeTimer();
        startTimer();
        board.newGame();

        pack();   
        setLocationRelativeTo(null);
        setTitle("Sudoku");
        setVisible(true);
    }

    private void startNewGame() {
        restartTimer();
        board.newGame();
    }

    private void initializeTimer() {
        seconds = 0;
        timer = new Timer(1000, e -> {
            seconds++;
            updateTimerLabel();
        });
    }

    private void startTimer() {
        timer.start();
    }

    private void restartTimer() {
        timer.stop();
        seconds = 0;
        updateTimerLabel();
        timer.start();
    }

    private void updateTimerLabel() {
        timerLabel.setText("Timer: " + seconds + " seconds");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuMain(); 
            }
        });
    }
}
