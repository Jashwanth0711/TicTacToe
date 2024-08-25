import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tictactoe1 extends JFrame implements ActionListener {
    private static final int SIZE = 3;
    private JButton[][] buttons;
    private boolean xTurn = true;  
    private int xMoves = 0;  
    private int oMoves = 0;  
    private JButton selectedButton = null; 

    public tictactoe1() {
        buttons = new JButton[SIZE][SIZE];
        setLayout(new GridLayout(SIZE, SIZE));
        initializeButtons();
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeButtons() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        if (selectedButton != null) {
            if (buttonClicked.getText().equals("")) {
                buttonClicked.setText(xTurn ? "X" : "O");
                selectedButton.setText("");
                selectedButton.setBackground(Color.WHITE);
                selectedButton = null;

                if (checkForWin()) {
                    JOptionPane.showMessageDialog(this, (xTurn ? "X" : "O") + " wins!");
                    resetGame();
                    return;
                }

                xTurn = !xTurn;  
            }
            return;
        }

        if (buttonClicked.getText().equals("")) {
            if (xTurn && xMoves < 3) {
                buttonClicked.setText("X");
                xMoves++;
                if (checkForWin()) {
                    JOptionPane.showMessageDialog(this, "X wins!");
                    resetGame();
                    return;
                }
                xTurn = false;  // Switch turns after placing a mark
            } else if (!xTurn && oMoves < 3) {
                buttonClicked.setText("O");
                oMoves++;
                if (checkForWin()) {
                    JOptionPane.showMessageDialog(this, "O wins!");
                    resetGame();
                    return;
                }
                xTurn = true; 
            }
        } else if ((xTurn && buttonClicked.getText().equals("X")) || (!xTurn && buttonClicked.getText().equals("O"))) {
            if ((xTurn && xMoves == 3) || (!xTurn && oMoves == 3)) {
                selectedButton = buttonClicked;
                selectedButton.setBackground(Color.YELLOW);
            }
        }

        if (xMoves + oMoves == SIZE * SIZE) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }
    }

    private boolean checkForWin() {
        for (int row = 0; row < SIZE; row++) {
            if (checkLine(buttons[row][0], buttons[row][1], buttons[row][2])) {
                return true;
            }
        }
        for (int col = 0; col < SIZE; col++) {
            if (checkLine(buttons[0][col], buttons[1][col], buttons[2][col])) {
                return true;
            }
        }
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) {
            return true;
        }
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) {
            return true;
        }
        return false;
    }

    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        return b1.getText().equals(b2.getText()) &&
               b2.getText().equals(b3.getText()) &&
               !b1.getText().equals("");
    }

    private void resetGame() {
        xTurn = true;
        xMoves = 0;
        oMoves = 0;
        selectedButton = null;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        new tictactoe1();
    }
}