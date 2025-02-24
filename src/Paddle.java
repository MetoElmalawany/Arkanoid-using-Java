import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
    int xVelocity;
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setXDirection(speed);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setXDirection(-speed);
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setXDirection(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setXDirection(0);
        }
    }
    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }
    public void move() {
        x = x + xVelocity;
    }
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
    }
}