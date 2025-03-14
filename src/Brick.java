import java.awt.*;

public class Brick extends Rectangle{
    private boolean destroyed;

    Brick(int x, int y, int BRICK_WIDTH, int BRICK_HEIGHT){
        super(x,y,BRICK_WIDTH,BRICK_HEIGHT);
        this.destroyed = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK); // Border color
        g.drawRect(x, y, width, height);
    }
    public void setDestroyed(boolean d){
        this.destroyed = d;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

}