import java.awt.*;
import javax.swing.*;

public class ArkanoidFrame extends JFrame{

    ArkanoidPanel panel;

    ArkanoidFrame(){
        panel = new ArkanoidPanel();
        this.add(panel);
        this.setTitle("دشمل");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}