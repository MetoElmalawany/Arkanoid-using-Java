import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuUI extends JPanel {

    private JButton startButton;
    private JButton instructionsButton;
    private JButton exitButton;
    private ArkanoidFrame frame;

    public MenuUI(ArkanoidFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(3, 1));

        startButton = new JButton("Start Game");
        instructionsButton = new JButton("Instructions");
        exitButton = new JButton("Exit");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.GREEN);
        instructionsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionsButton.setBackground(Color.black);
        instructionsButton.setForeground(Color.GREEN);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.GREEN);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.startGame();
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(startButton);
        add(instructionsButton);
        add(exitButton);
        setBackground(Color.black);
        setPreferredSize(new Dimension(600, 750));
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(this,
                "Use the left and right arrow keys to move the paddle.\n" +
                        "Break all the bricks to win the game.\n" +
                        "Collect power-ups for special abilities.\n" +
                "Collect the good powerUps (blue for wide paddle, red for extra life)\n" +
                "Avoid the bad powerUps (gray for extra row of bricks, yellow for speed up paddle, cyan for shorten paddle)\n",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }
}