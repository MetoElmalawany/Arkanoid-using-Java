import java.awt.*;
import javax.swing.*;

public class ArkanoidFrame extends JFrame {

    private ArkanoidPanel panel;
    private MenuUI menuUI;
    private GameOverScreen gameOverScreen;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    ArkanoidFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuUI = new MenuUI(this);
        panel = new ArkanoidPanel(this); // Pass the frame reference to ArkanoidPanel
        gameOverScreen = new GameOverScreen(this, 0); // Initialize with a default score

        mainPanel.add(menuUI, "Menu");
        mainPanel.add(panel, "Game");
        mainPanel.add(gameOverScreen, "GameOver");

        this.add(mainPanel);
        this.setTitle("Arkanoid");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        showMenu();
    }

    public void startGame() {
        cardLayout.show(mainPanel, "Game");
        panel.startGame();
        panel.requestFocusInWindow();
    }

    public void showMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void showGameOver(int finalScore) {
        gameOverScreen = new GameOverScreen(this, finalScore); // Update the score
        mainPanel.add(gameOverScreen, "GameOver"); // Re-add the updated screen
        cardLayout.show(mainPanel, "GameOver");
    }

}