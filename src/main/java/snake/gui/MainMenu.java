package snake.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.MusicPlayer;

public class MainMenu extends InputScreen {
    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param game current instance of the game
     */
    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show() {
        MusicPlayer.playMenu();
        super.show();
    }

    public List<Actor> createActors() {
        return new ArrayList<>(Arrays.asList(createPlayLabel(), createSettingsLabel()));
    }

    /**
     * Creates play label with onclick to change to gamescreen.
     *
     * @return label with the functionality
     */
    private Label createPlayLabel() {
        return createLabelWithOnClick("Play!", getGame().getHeight() / 1.75f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setScreen(new GameScreen(50, getGame()));
            }
        });
    }

    /**
     * Creates settings label with onclick to change to settingsscreen.
     *
     * @return label with the functionality
     */
    private Label createSettingsLabel() {
        return createLabelWithOnClick("Settings!", getGame().getHeight() / 2f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setScreen(new SettingsScreen(getGame()));
            }
        });
    }
}
