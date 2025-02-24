import java.awt.*;
import java.awt.event.KeyEvent;

public class Stats extends Rectangle {
    int GAME_WIDTH;
    int GAME_HEIGHT;
    int STATS_HEIGHT;
    private int score;
    private int lives;
    Stats(int GAME_WIDTH, int GAME_HEIGHT, int STATS_HEIGHT){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.STATS_HEIGHT = STATS_HEIGHT;
        this.score = 0;
        this.lives = 3;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, GAME_WIDTH, STATS_HEIGHT);

        g.setColor(Color.black);
        g.drawRect(0, 0, GAME_WIDTH, STATS_HEIGHT);
        g.setFont(new Font("Consolas",Font.PLAIN,40));


        g.drawString(String.valueOf("Score: ") + String.valueOf(this.score), 10, 40);
        g.drawString(String.valueOf("Lives: ") + String.valueOf(this.lives), 420, 40);

    }
}