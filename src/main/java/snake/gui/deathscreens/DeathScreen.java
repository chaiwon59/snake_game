package snake.gui.deathscreens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import snake.Dao;
import snake.gui.InputScreen;
import snake.gui.LauncherClass;
import snake.gui.MainMenu;

public abstract class DeathScreen extends InputScreen {
    transient int score;
    transient int stepSize;
    transient Dao dao;

    //pmd du anomaly
    transient float height;

    /**
     * Initializes the Deathscreen.
     *
     * @param score    final score of the user
     * @param stepSize stepSize to give to the new snake.Gui.GameScreen
     * @param launcherClass     current instance of the game
     */
    public DeathScreen(int score, int stepSize, LauncherClass launcherClass) {
        super(launcherClass);
        this.score = score;
        this.dao = launcherClass.dao;
        this.stepSize = stepSize;
    }

    public abstract List<Actor> createActors();

    /**
     * Creates button to go back to the main menu.
     * @return button with functionality
     */
    protected TextButton createMainMenuButton() {
        return createButton("Main Menu",
                getLauncherClass().getWidth() / 2.48f, getLauncherClass().getHeight() / 2.0f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(new MainMenu(getLauncherClass()));
                    }
                });
    }

    /**
     * Create button to start another game.
     * @return textbutton with a listener to change the screen on click.
     */
    protected TextButton createAgainButton(ClickListener listener) {
        return createButton("Play Again", getLauncherClass().getWidth() / 2.56f,
                getLauncherClass().getHeight() / 1.778f, listener);
    }

}
