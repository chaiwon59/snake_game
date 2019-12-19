package snake.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import snake.Dao;

public class RegisterScreen extends InputScreen {
    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public RegisterScreen(LauncherClass launcherClass) {
        super(launcherClass);
    }

    @Override
    public List<Actor> createActors() {
        TextField username = createUsernameField();
        TextField password = createPasswordField();
        TextButton register = createRegisterButton(username, password);

        return new ArrayList<>(Arrays.asList(username, password, register));
    }

    /**
     * Creates register button which tries to insert the user into the database.
     *      If this succeeds then the user is added to the database and the screen is switched
     * @param username username of the user
     * @param password password of the user
     * @return Button with the given functionality.
     */
    private TextButton createRegisterButton(TextField username, TextField password) {
        return createButton("Register", getLauncherClass().getWidth() / 2.55f,
                getLauncherClass().getHeight() / 1.778f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (new Dao().insertUser(username.getText(), password.getText())) {
                            getLauncherClass().setScreen(new LoginScreen(getLauncherClass()));
                        }
                    }
                });
    }
}
