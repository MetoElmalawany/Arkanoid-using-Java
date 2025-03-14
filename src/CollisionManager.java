import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CollisionManager {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 750;
    static final int BALL_DIAMETER = 20;
    static final int STATS_HEIGHT = 50;
    static final int BRICK_DIAMETER = 25;


    private ArkanoidFrame frame;
    private ArkanoidPanel panel;


    public CollisionManager(ArkanoidPanel panel, ArkanoidFrame frame, Paddle paddle, Stats stats,ArrayList<PowerUp> powerUps,ArrayList<Brick> bricks, Ball ball ) {
        this.panel = panel;
        this.frame = frame;
    }


    public void checkCollision() {
        //bounce ball off top & bottom window edges
        if(panel.ball.x <= 0) {
            panel.ball.setXDirection(-panel.ball.xVelocity);
        }
        if(panel.ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            panel.ball.setXDirection(-panel.ball.xVelocity);
        }
        if(panel.ball.intersects(panel.stats) || panel.ball.y <= STATS_HEIGHT) {
            panel.ball.setYDirection(-panel.ball.yVelocity);
        }
        if(panel.ball.y >= GAME_HEIGHT - BALL_DIAMETER) {;

            int trials = panel.stats.getTrials();
            panel.stats.setTrials(--trials);
            if (trials <= 0) {
                // Game Over
                panel.gameOver = true;
                frame.showGameOver(panel.stats.getScore()); // Show Game Over screen with final score
                return; // Stop further updates
            }
            panel.newPaddles();
            panel.newBall();

        }

        //bounce ball off panel.paddles
        if(panel.ball.intersects(panel.paddle) && panel.ball.y + BALL_DIAMETER - panel.ball.getSpeed() <= panel.paddle.y) {
            panel.ball.setYDirection(-panel.ball.yVelocity);
        }
        else if (panel.ball.intersects(panel.paddle) && (panel.ball.y + BALL_DIAMETER) - panel.ball.getSpeed() > panel.paddle.y) {
            panel.ball.setYDirection(-panel.ball.yVelocity);
            panel.ball.setXDirection(-panel.ball.xVelocity);
        }
        for (PowerUp powerUp : panel.powerUps) {
            if (powerUp.y >= GAME_HEIGHT) {
                powerUp.setDestroyed(true);
            }
            if (powerUp.intersects(panel.paddle) && !powerUp.isDestroyed()){
                powerUp.setDestroyed(true);
            }
        }

        //stops paddles at window edges
        if(panel.paddle.x<=0) {
            panel.paddle.x=0;
        }
        if(panel.paddle.x >= (GAME_WIDTH-panel.paddle.getWidth())) {
            panel.paddle.x = (int) (GAME_WIDTH-panel.paddle.getWidth());
        }
        for (Brick brick: panel.bricks){
            if (panel.ball.intersects(brick) && !brick.isDestroyed()){
                if (panel.ball.y >= brick.y + BRICK_DIAMETER/2) {
                    panel.ball.setYDirection(-panel.ball.yVelocity);
                }
                else if (panel.ball.y < brick.y + BRICK_DIAMETER/2){
                    panel.ball.setYDirection(-panel.ball.yVelocity);
                    panel.ball.setXDirection(-panel.ball.xVelocity);
                }
                brick.setDestroyed(true);
                int score = panel.stats.getScore();
                panel.stats.setScore(++score);
                if (panel.stats.getScore() % 10 == 0 && panel.stats.getScore() != 0){
                    panel.newPowerUp(brick.x,brick.y);
                }
                break;

            }
        }

    }
}
