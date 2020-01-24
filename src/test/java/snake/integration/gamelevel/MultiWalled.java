package snake.integration.gamelevel;

import snake.games.Game;
import snake.games.MultiPlayerGame;
import snake.games.levels.Level;
import snake.games.levels.WalledLevel;
import snake.gui.LauncherClass;

public class MultiWalled extends GameLevel {
    @Override
    public Game makeGame(LauncherClass launcher, int stepSize) {
        return new MultiPlayerGame(launcher, stepSize);
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
