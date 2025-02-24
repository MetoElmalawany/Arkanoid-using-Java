import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ArkanoidPanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 700;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BRICK_DIAMETER = 25;
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 100;
    static final int PADDLE_HEIGHT = 25;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle;
    ArrayList<Brick> bricks;
    Ball ball;

    ArkanoidPanel(){
        newPaddles();
        newBall();
        newBricks();
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH/2),GAME_HEIGHT-PADDLE_HEIGHT - 20,BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddles() {
        paddle = new Paddle((GAME_WIDTH/2) - (PADDLE_WIDTH/2),GAME_HEIGHT-PADDLE_HEIGHT,PADDLE_WIDTH,PADDLE_HEIGHT);
    }
    void newBricks() {
        bricks = new ArrayList<>();
        int brickWidth = BRICK_DIAMETER, brickHeight = BRICK_DIAMETER;
        int rows = 4, cols = GAME_WIDTH/brickWidth;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(BRICK_DIAMETER*col,
                        BRICK_DIAMETER*row,
                        brickWidth, brickHeight));
            }
        }
    }
    public void paint(Graphics g) {
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g) {
        paddle.draw(g);
        ball.draw(g);
        for ( Brick brick : bricks){
            if (!brick.isDestroyed()) {
                brick.draw(g);
            }
        }

        Toolkit.getDefaultToolkit().sync();

    }
    public void move() {
        paddle.move();
        ball.move();
    }
    public void checkCollision() {

        //bounce ball off top & bottom window edges
        if(ball.x <= 0) {
            ball.setXDirection(-ball.xVelocity);
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            ball.setXDirection(-ball.xVelocity);
        }
        if(ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            newPaddles();
            newBall();
        }
        //bounce ball off paddles
        if(ball.intersects(paddle)) {
            ball.setYDirection(-ball.yVelocity);
        }
        //stops paddles at window edges
        if(paddle.x<=0) {
            paddle.x=0;
        }
        if(paddle.x >= (GAME_WIDTH-PADDLE_WIDTH)) {
            paddle.x= GAME_WIDTH-PADDLE_WIDTH;
        }
        for (Brick brick: bricks){
            if (ball.intersects(brick) && !brick.isDestroyed()){
                ball.setYDirection(-ball.yVelocity);
                brick.setDestroyed(true);
                break;
            }
        }
    }
    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks =60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >=1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
    }
}