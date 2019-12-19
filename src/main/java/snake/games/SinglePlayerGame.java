package snake.games;

import snake.Dao;
import snake.MusicPlayer;
import snake.Snake;
import snake.gui.LauncherClass;
import snake.gui.deathscreens.SinglePlayerDeathScreen;

public class SinglePlayerGame extends Game {
    transient Dao dao;

    public SinglePlayerGame(LauncherClass launcher, int stepSize) {
        super(launcher, stepSize);
        this.dao = new Dao();
    }

    @Override
    public void updateScore(Snake snake) {
        score1 += 10;
    }

    @Override
    public void gameOver(Snake losingSnake) {
        this.gameIsOver = true;

        MusicPlayer.stopInGame();
        MusicPlayer.playDeathMusic();
        dao.updateScore(launcher.getUser().getUsername(), score1);
        launcher.setScreen(new SinglePlayerDeathScreen(score1, stepSize, launcher));
    }

    @Override
    public void createSnack() {
        builder.createSnack(player1.getBody());
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
