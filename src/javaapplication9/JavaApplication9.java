package javaapplication9;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JavaApplication9 {

    private static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    private static JButton[][] buttons = new JButton[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(3, 3)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.CYAN, getWidth(), getHeight(), Color.PINK);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusPainted(false);
                int row = i;
                int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleMove(row, col, frame);
                    }
                });
                panel.add(buttons[i][j]);
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void handleMove(int row, int col, JFrame frame) {
        if (board[row][col] != ' ') {
            JOptionPane.showMessageDialog(null, "Invalid move. Cell already occupied.");
            return;
        }

        board[row][col] = currentPlayer;
        buttons[row][col].setText(String.valueOf(currentPlayer));

        if (checkWin(currentPlayer)) {
            showEndScreen("Player " + currentPlayer + " wins!", frame);
            return;
        } else if (isBoardFull()) {
            showEndScreen("It's a draw!", frame);
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private static void showEndScreen(String message, JFrame mainFrame) {
        JFrame endFrame = new JFrame("Game Over");
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setSize(300, 200);

        JPanel endPanel = new JPanel();
        endPanel.setLayout(new BorderLayout());

        JLabel endLabel = new JLabel(message, SwingConstants.CENTER);
        endLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endPanel.add(endLabel, BorderLayout.CENTER);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endFrame.dispose();
                mainFrame.dispose();
                resetBoard();
                main(null);
            }
        });

        endPanel.add(newGameButton, BorderLayout.SOUTH);
        endFrame.add(endPanel);
        endFrame.setVisible(true);
    }

    private static void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = 'X';
    }

    private static boolean checkWin(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
