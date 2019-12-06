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

public class LoginScreen extends InputScreen {
    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param game current instance of the game
     */
    public LoginScreen(Game game) {
        super(game);
    }

    @Override
    public List<Actor> createActors() {
        TextField username = createUsernameField();
        TextField password = createPasswordField();
        TextButton submit = createSubmitButton(username, password);
        TextButton register = createRegisterButton();

        return new ArrayList<>(Arrays.asList(username, password, submit, register));
    }

    /**
     * Create a submit button which switches the screen if the combination is valid.
     * @param username username of the user
     * @param password password of the user
     * @return button with a listener.
     */
    private TextButton createSubmitButton(TextField username, TextField password) {
        return createButton("Log In", getGame().getWidth() / 3.33f, getGame().getWidth() / 1.778f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            if (new Dao().checkUsernamePassword(
                                    username.getText(), password.getText())) {
                                getGame().getUser().setUsername(username.getText());
                                getGame().setScreen(new MainMenu(getGame()));
                            }
                        } catch (SQLException e) {

                            Gdx.app.exit();
                        }
                    }
                });
    }

    /**
     * Creates a register button which changes the screen to the register screen.
     * @return textbutton with the functionality
     */
    private TextButton createRegisterButton() {
        return createButton("Register", getGame().getWidth() / 2.057f,
                getGame().getHeight() / 1.778f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getGame().setScreen(new RegisterScreen(getGame()));
                    }
                });
    }
}
