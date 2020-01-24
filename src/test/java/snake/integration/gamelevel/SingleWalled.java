package snake.integration.gamelevel;

import snake.games.Game;
import snake.games.SinglePlayerGame;
import snake.games.levels.Level;
import snake.games.levels.WalledLevel;
import snake.gui.LauncherClass;

public class SingleWalled extends GameLevel {
    @Override
    public Game makeGame(LauncherClass launcher, int stepSize) {
        return new SinglePlayerGame(launcher, stepSize);
    }

    @Override
    public Level makeLevel(Game game, LauncherClass launcher, int stepSize) {
        return new WalledLevel(game, launcher, stepSize);
    }

    @Override
    public boolean isTypeLevel() {
        return false;
    }
}
