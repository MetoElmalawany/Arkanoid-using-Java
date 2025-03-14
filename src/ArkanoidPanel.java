import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
    int ROWS_OF_BRICKS = 4;
    private final ArkanoidFrame frame;

    boolean gameOver = false;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle;
    Stats stats;
    ArrayList<PowerUp> powerUps;
    ArrayList<Brick> bricks;
    Ball ball;
    CollisionManager collisionManager;

    ArkanoidPanel(ArkanoidFrame frame){
        this.frame = frame; // Initialize the frame reference
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
    }

    public void startGame() {
        gameOver = false;
        newPaddles();
        newBall();
        newBricks(0);
        newStats();
        powerUps = new ArrayList<>();
        newCollisionManager();
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH/2),GAME_HEIGHT - PADDLE_HEIGHT - BALL_DIAMETER,BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddles() {
        paddle = new Paddle((GAME_WIDTH/2) - (PADDLE_WIDTH/2),GAME_HEIGHT-PADDLE_HEIGHT,PADDLE_WIDTH,PADDLE_HEIGHT);
    }
    void newBricks(int start) {
        if (bricks == null) {
            bricks = new ArrayList<>();
        }
        int brickWidth = BRICK_DIAMETER, brickHeight = BRICK_DIAMETER;
        int rows = ROWS_OF_BRICKS, cols = GAME_WIDTH/brickWidth;
        for (int row = start; row < rows; row++) {
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

    public void newPowerUp(int x, int y) {
        int index = random.nextInt(6);
        powerUps.add(new PowerUp(x,y,BRICK_DIAMETER-5,BRICK_DIAMETER-5,index));
    }
    public void newCollisionManager(){
        collisionManager = new CollisionManager(this,frame,paddle,stats,powerUps,bricks,ball);
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
        for (PowerUp powerUp : powerUps) {
            if (powerUp != null && !powerUp.isDestroyed()){
                powerUp.draw(g);
            }
        }

        Toolkit.getDefaultToolkit().sync();

    }

    public void move() {
        paddle.move();
        int score = stats.getScore()/24;
        switch (score){
            case 1:
                ball.setSpeed(9);
                break;
            case 2:
                ball.setSpeed(10);
                break;
            case 3:
                ball.setSpeed(11);
                break;
            default:
                break;
        }
        ball.move();
        for (PowerUp powerUp : powerUps) {
            if (powerUp != null && !powerUp.isDestroyed()){
                powerUp.move();
            }
        }
    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks =60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(!gameOver) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >= 1 ) {
                move();
                collisionManager.checkCollision();
                delta--;
            }
            repaint();
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