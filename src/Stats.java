import java.awt.*;
import java.awt.event.KeyEvent;

public class Stats extends Rectangle {
    int GAME_WIDTH;
    int GAME_HEIGHT;
    int STATS_HEIGHT;
    private int score;
    private int trials;
    Stats(int GAME_WIDTH, int GAME_HEIGHT, int STATS_HEIGHT){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.STATS_HEIGHT = STATS_HEIGHT;
        this.score = 0;
        this.trials = 3;
    }

    public void setTrials(int trials) {
        this.trials = trials;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTrials() {
        return trials;
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
        g.drawString(String.valueOf("Trials: ") + String.valueOf(this.trials), 400, 40);

    }
}