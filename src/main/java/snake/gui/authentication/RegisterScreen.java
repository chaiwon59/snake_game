package snake.gui.authentication;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.gui.InputScreen;
import snake.gui.LauncherClass;

public class RegisterScreen extends AuthenticationScreen {
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
        TextField email = createEmailField();
        TextButton register = createRegisterButton(username, password, email);

        return new ArrayList<>(Arrays.asList(email, username, password, register));
    }

    /**
     * Creates the email text field.
     * @return email text field.
     */
    public TextField createEmailField() {
        TextField email = new TextField("", getSkin());
        email.setWidth(300);
        email.setHeight(25f);
        email.setPosition(getLauncherClass().getWidth() / 3.33f,
                getLauncherClass().getHeight() / 1.34f);

        email.setMessageText("Email");
        return email;
    }

    /**
     * Creates register button which tries to insert the user into the database.
     *      If this succeeds then the user is added to the database and the screen is switched
     * @param username username of the user
     * @param password password of the user
     * @return Button with the given functionality.
     */
    private TextButton createRegisterButton(
            TextField username, TextField password, TextField email) {
        return createButton("Register", getLauncherClass().getWidth() / 2.55f,
                getLauncherClass().getHeight() / 1.778f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (getLauncherClass().dao.insertUser(
                                username.getText(), password.getText(), email.getText())) {
                            getLauncherClass().setScreen(new LoginScreen(getLauncherClass()));
                        } else {
                            createDialog("Username already exists", "Error");
                        }
                    }
                });
    }
}
