package snake.integration.gamelevel;

import snake.games.Game;
import snake.games.MultiPlayerGame;
import snake.games.levels.Level;
import snake.games.levels.NoWallsLevel;
import snake.gui.LauncherClass;

public class MultiNoWalls extends GameLevel {
    @Override
    public Game makeGame(LauncherClass launcher, int stepSize) {
        return new MultiPlayerGame(launcher, stepSize);
    }

    @Override
    public Level makeLevel(Game game, LauncherClass launcher, int stepSize) {
        return new NoWallsLevel(game, launcher, stepSize);
    }

    @Override
    public boolean isTypeLevel() {
        return true;
    }
}
