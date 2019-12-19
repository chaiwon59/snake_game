package snake.games;

import java.util.ArrayList;
import java.util.List;
import snake.MusicPlayer;
import snake.Snake;
import snake.gui.LauncherClass;
import snake.gui.deathscreens.MultiPlayerDeathScreen;
import snake.squares.Square;

public class MultiPlayerGame extends Game {
    transient Snake player2;
    transient int score2;

    /**
     * Creates instance of a MultiPlayerGame.
     * @param launcher instance of the launcher class
     * @param stepSize size of the steps when creating squares
     */
    public MultiPlayerGame(LauncherClass launcher, int stepSize) {
        super(launcher, stepSize);

        this.player2 = new Snake(stepSize, squareSize, (int) launcher.getWidth() - stepSize);
        this.score2 = 0;
    }

    @Override
    public void updateScore(Snake snake) {
        if (snake.equals(player1)) {
            score1 += 10;
        }
        if (snake.equals(player2)) {
            score2 += 10;
        }
    }

    /**
     * Checks whether there is a collision between the players.
     */
    public void checkSnakeCollision() {
        if (player1.getBody().contains(player2.getHead())) {
            gameOver(player2);
            return;
        } else if (player2.getBody().contains(player1.getHead())) {
            gameOver(player1);
            return;
        }
    }

    @Override
    public void createSnack() {
        List<Square> bodies = new ArrayList<>(player1.getBody());
        bodies.addAll(player2.getBody());

        builder.createSnack(bodies);
    }

    @Override
    public void gameOver(Snake losingSnake) {
        this.gameIsOver = true;

        MusicPlayer.stopInGame();
        MusicPlayer.playDeathMusic();

        launcher.setScreen(new MultiPlayerDeathScreen(score2, stepSize, launcher,
                losingSnake.equals(player1) ? "Player 2 Won!" : "Player 1 Won!"));
    }

    public Square getPlayer2Head() {
        return player2.getHead();
    }

    public List<Square> getPlayer2Body() {
        return player2.getBody();
    }

    public Snake getPlayer2() {
        return this.player2;
    }

    public int getScore2() {
        return score2;
    }
}
