import java.awt.*;
import java.util.Random;

public class PowerUp extends Rectangle{
    private boolean collected;
    private int powerUp;
    Random random;
    int yVelocity = 2;
    int initialSpeed = 2;

    public enum PowerUpType {
        EXTRA_LIFE(1), WIDEN_PADDLE(2), FAST_BALL(3), SHORT_PADDLE(4), EXTRA_ROW(5);

        private final int id; // Custom field


        PowerUpType(int id) { this.id = id; } // Constructor
        public int getId() { return id; } // Getter

    }
    public static PowerUpType fromId(int id) {
        for (PowerUpType p : PowerUpType.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid ID: " + id);
    }

    PowerUp(int x, int y, int POWERUP_WIDTH, int POWERUP_HEIGHT,int type){
        super(x,y,POWERUP_WIDTH,POWERUP_HEIGHT);
        this.collected = false;
        this.powerUp = type;
    }

    public int getPowerUp() {
        return powerUp;
    }

    public void draw(Graphics g) {
        switch (this.powerUp){
            case 1:
                g.setColor(Color.RED);
                break;
            case 2:
                g.setColor(Color.BLUE);
                break;
            case 3:
                g.setColor(Color.YELLOW);
                break;
            case 4:
                g.setColor(Color.CYAN);
                break;
            case 5:
                g.setColor(Color.gray);
                break;
            default:
                break;
        }
        g.fillOval(x, y, width, height);

    }
    public void move() {
        y += yVelocity;
    }
    public void setDestroyed(boolean c){
        this.collected = c;
    }
    public boolean isDestroyed() {
        return collected;
    }
}