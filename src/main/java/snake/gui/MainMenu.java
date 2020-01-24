package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.MusicPlayer;
import snake.games.MultiPlayerGame;
import snake.games.SinglePlayerGame;
import snake.gui.authentication.LoginScreen;
import snake.gui.gamescreens.MultiPlayerGameScreen;
import snake.gui.gamescreens.SinglePlayerGameScreen;

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

    /**
     * Creates the actors for the stage.
     * @return list of the actors.
     */
    public List<Actor> createActors() {
        return new ArrayList<>(
                Arrays.asList(createPlayLabel1P(), createPlayLabel2P(), createSettingsLabel(),
                        createDeleteAccountLabel(), createExitLabel()));
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
                                new SinglePlayerGameScreen(
                                        new SinglePlayerGame(getLauncherClass(), 50)));
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
                                new MultiPlayerGameScreen(
                                        new MultiPlayerGame(getLauncherClass(), 50)));
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
                        MusicPlayer.stopMenu();
                    }
                });
    }

    private Label createDeleteAccountLabel() {
        return createLabelWithOnClick("Delete account",
                getLauncherClass().getHeight() / 2.55f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        createDialog("Are you sure?", "");
                    }
                });
    }

    private Label createExitLabel() {
        return createLabelWithOnClick("Exit",
                getLauncherClass().getHeight() / 2.95f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.exit();
                    }
                });
    }

    /**
     * Creates a new dialog with the given error message.
     *
     * @param text text of the error message
     */
    @Override
    public void createDialog(String text, String title) {
        Dialog dialog = new Dialog(title, getSkin(), "dialog") {
            @Override
            public void result(Object obj) {
                if ((boolean) obj) {
                    LauncherClass launcher = getLauncherClass();
                    launcher.dao.deleteUser(launcher.getUser().getUsername());
                    launcher.setScreen(new LoginScreen(launcher));
                    MusicPlayer.stopMenu();
                }
            }
        };
        dialog.padTop(35);

        Label label = new Label("\n" + text + "\n",
                StyleUtility.getSkin().get("optional", Label.LabelStyle.class));
        dialog.text(label);
        dialog.button("Confirm", true);
        dialog.button("Cancel", false);

        dialog.show(getStage());
    }
}
