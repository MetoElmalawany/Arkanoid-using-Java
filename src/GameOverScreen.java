import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOverScreen extends JPanel {

    private JLabel gameOverLabel;
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton menuButton;
    private ArkanoidFrame frame;

    public GameOverScreen(ArkanoidFrame framee, int finalScore) {
        this.frame = framee;
        setLayout(new GridLayout(4, 1));
        setBackground(Color.black);

        // Game Over Label
        gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.red);
        add(gameOverLabel);

        // Final Score Label
        scoreLabel = new JLabel("Final Score: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        scoreLabel.setForeground(Color.white);
        add(scoreLabel);

        // Restart Button
        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.startGame();
            }
        });
        add(restartButton);

        // Return to Menu Button
        menuButton = new JButton("Return to Menu");
        menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.showMenu();
            }
        });
        add(menuButton);
    }
}