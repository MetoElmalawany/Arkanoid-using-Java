import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ArkanoidPanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 750;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BRICK_DIAMETER = 25;
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 100;
    static final int PADDLE_HEIGHT = 25;
    static final int STATS_HEIGHT = 50;
    static final int ROWS_OF_BRICKS = 4;
    static final int NUM_OF_BRICKS = (GAME_WIDTH/BRICK_DIAMETER) * ROWS_OF_BRICKS;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle;
    Stats stats;
    ArrayList<Brick> bricks;
    Ball ball;

    ArkanoidPanel(){
        newPaddles();
        newBall();
        newBricks();
        newStats();
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
        int rows = ROWS_OF_BRICKS, cols = GAME_WIDTH/brickWidth;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(BRICK_DIAMETER*col,
                        (BRICK_DIAMETER*row) + 50,
                        brickWidth, brickHeight));
            }
        }
    }
    public void newStats() {
        stats = new Stats(GAME_WIDTH,GAME_HEIGHT,STATS_HEIGHT);
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
        stats.draw(g);
        Toolkit.getDefaultToolkit().sync();

    }
    public void move() {
        paddle.move();
        int score = stats.getScore()/24;
        System.out.println(score);
        System.out.println(ball.getInitialSpeed());
        switch (score){
            case 1:
                ball.setInitialSpeed(9);
                break;
            case 2:
                ball.setInitialSpeed(11);
                break;
            case 3:
                ball.setInitialSpeed(12);
                break;
            default:
                break;
        }
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
        if(ball.y <= STATS_HEIGHT) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            int lives = stats.getLives();
            stats.setLives(--lives);
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
            paddle.x = GAME_WIDTH-PADDLE_WIDTH;
        }
        for (Brick brick: bricks){
            if (ball.intersects(brick) && !brick.isDestroyed()){
                ball.setYDirection(-ball.yVelocity);
                brick.setDestroyed(true);
                int score = stats.getScore();
                stats.setScore(++score);
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