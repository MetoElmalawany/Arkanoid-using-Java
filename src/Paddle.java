import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class Paddle extends Rectangle{
    int xVelocity;
    int INITIAL_SPEED = 10;
    int speed;
    private double width = 100;
    private final double NORMAL_WIDTH = 100;
    private final double WIDE_WIDTH = 150;
    private final double SHORT_WIDTH = 75;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> powerUpTask; // Stores the scheduled task

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.speed = INITIAL_SPEED;
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
        g.fillRect(x, y, (int)width, height);
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void activateSpeedPowerUp() {
        // If an old power-up is still running, cancel it
        if (powerUpTask != null && !powerUpTask.isDone()) {
            powerUpTask.cancel(true);
            System.out.println("Previous Power-Up Canceled.");
        }

        this.speed = 20;
        System.out.println("Power-Up Activated! Paddle Widened.");

        // Schedule the reset task and store its reference
        powerUpTask = scheduler.schedule(() -> {
            this.speed = INITIAL_SPEED;
            System.out.println("Power-Up Expired! Paddle Back to Normal.");
        }, 10, TimeUnit.SECONDS);
    }

    public void activateWidenPowerUp(int flag) {
        // If an old power-up is still running, cancel it
        if (powerUpTask != null && !powerUpTask.isDone()) {
            powerUpTask.cancel(true);
            System.out.println("Previous Power-Up Canceled.");
        }

        switch (flag) {
            case 1:
                width = WIDE_WIDTH;
                break;
            case 2:
                width = SHORT_WIDTH;
                break;
            default:
                break;
        }
        System.out.println("Power-Up Activated! Paddle Widened.");

        // Schedule the reset task and store its reference
        powerUpTask = scheduler.schedule(() -> {
            width = NORMAL_WIDTH;
            System.out.println("Power-Up Expired! Paddle Back to Normal.");
        }, 10, TimeUnit.SECONDS);
    }


    public void cancelPowerUp() {
        if (powerUpTask != null && !powerUpTask.isDone()) {
            powerUpTask.cancel(true);
            width = NORMAL_WIDTH;
            speed = INITIAL_SPEED;// Revert immediately
            System.out.println("Power-Up Manually Canceled! Paddle Back to Normal.");
        }
    }
}
