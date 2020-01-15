package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.Dao;
import snake.User;

public class LoginScreen extends InputScreen {
    private transient Dao dao;

    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public LoginScreen(LauncherClass launcherClass) {
        super(launcherClass);
        this.dao = launcherClass.dao;
    }

    @Override
    public List<Actor> createActors() {
        TextField username = createUsernameField();
        TextField password = createPasswordField();
        TextButton submit = createSubmitButton(username, password);
        TextButton register = createRegisterButton();


        return new ArrayList<>(
                Arrays.asList(username, password, submit, register, createResetButton()));
    }

    /**
     * Create a submit button which switches the screen if the combination is valid.
     *
     * @param username username of the user
     * @param password password of the user
     * @return button with a listener.
     */
    private TextButton createSubmitButton(TextField username, TextField password) {
        return createButton("Log In",
                getLauncherClass().getWidth() / 3.33f, getLauncherClass().getWidth() / 1.778f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            if (dao.checkUsernamePassword(
                                    username.getText(), password.getText())) {
                                getLauncherClass().user = new User();
                                getLauncherClass().getUser().setUsername(username.getText());

                                if (dao.isReset(username.getText())) {
                                    getLauncherClass().setScreen(
                                            new UpdatePasswordScreen(getLauncherClass()));
                                } else {
                                    getLauncherClass().setScreen(new MainMenu(getLauncherClass()));
                                }
                            } else {
                                createDialog("Username and Password don't match", "Error");
                            }
                        } catch (SQLException e) {

                            Gdx.app.exit();
                        }
                    }
                });
    }

    /**
     * Creates a register button which changes the screen to the register screen.
     *
     * @return textbutton with the functionality
     */
    private TextButton createRegisterButton() {
        return createButton("Register", getLauncherClass().getWidth() / 2.057f,
                getLauncherClass().getHeight() / 1.778f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(new RegisterScreen(getLauncherClass()));
                    }
                });
    }

    /**
     * Creates a reset password button which changes the screen to the reset screen.
     *
     * @return textbutton with the functionality
     */
    private TextButton createResetButton() {
        return createButton("Reset Password", getLauncherClass().getWidth() / 3f,
                getLauncherClass().getHeight() / 2.0f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getLauncherClass().setScreen(new ResetScreen(getLauncherClass()));
                    }
                });
    }
}
