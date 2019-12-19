package snake.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.MusicPlayer;
import snake.games.MultiPlayerGame;
import snake.games.SinglePlayerGame;

public class MainMenu extends InputScreen {
    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public MainMenu(LauncherClass launcherClass) {
        super(launcherClass);
    }

    @Override
    public void show() {
        MusicPlayer.playMenu();
        super.show();
    }

    public List<Actor> createActors() {
        return new ArrayList<>(
                Arrays.asList(createPlayLabel1P(), createPlayLabel2P(), createSettingsLabel()));
    }

    /**
     * Creates play label with onclick to change to gamescreen.
     *
     * @return label with the functionality
     */
    private Label createPlayLabel1P() {
        return createLabelWithOnClick("Play 1P!",
                getLauncherClass().getHeight() / 1.75f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(
                                new GameScreen(new SinglePlayerGame(getLauncherClass(), 50)));
                    }
                });
    }

    /**
     * Creates play label with onclick to change to gamescreen.
     *
     * @return label with the functionality
     */
    private Label createPlayLabel2P() {
        return createLabelWithOnClick("Play 2P!", getLauncherClass().getHeight() / 1.97f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(
                                new GameScreen(new MultiPlayerGame(getLauncherClass(), 50)));
                    }
                });
    }

    /**
     * Creates settings label with onclick to change to settingsscreen.
     *
     * @return label with the functionality
     */
    private Label createSettingsLabel() {
        return createLabelWithOnClick("Settings!",
                getLauncherClass().getHeight() / 2.25f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(new SettingsScreen(getLauncherClass()));
                    }
                });
    }
}
