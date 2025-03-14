import java.awt.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Ball extends Rectangle{

    Random random;
    int xVelocity;
    int yVelocity;
    private final int INITIAL_SPEED = 8;
    private final int FAST_SPEED = 15;
    private int speed = INITIAL_SPEED;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> powerUpTask; // Stores the scheduled task

    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        setXDirection(speed);
        setYDirection(speed);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setXDirection(int randomXDirection) {
        xVelocity = (randomXDirection / INITIAL_SPEED) * speed;
    }
    public void setYDirection(int randomYDirection) {
        yVelocity = (randomYDirection / INITIAL_SPEED) * speed;
    }
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }

}