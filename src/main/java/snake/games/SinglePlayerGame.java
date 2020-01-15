package snake.games;

import java.util.List;

import snake.Dao;
import snake.MusicPlayer;
import snake.Snake;
import snake.gui.LauncherClass;
import snake.gui.deathscreens.SinglePlayerDeathScreen;
import snake.squares.Square;

public class SinglePlayerGame extends Game {
    transient Dao dao;

    public SinglePlayerGame(LauncherClass launcher, int stepSize) {
        super(launcher, stepSize);
        this.dao = launcher.dao;
    }

    @Override
    public void gameOver(Snake losingSnake) {
        this.gameIsOver = true;

        MusicPlayer.stopInGame();
        MusicPlayer.playDeathMusic();
        dao.updateScore(launcher.getUser().getUsername(), getScore1());
        launcher.setScreen(new SinglePlayerDeathScreen(getScore1(), stepSize, launcher));
    }

    @Override
    public List<Square> getForbiddenSquares() {
        return player1.getBody();
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
