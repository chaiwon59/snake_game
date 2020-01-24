package snake.gui.authentication;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import snake.gui.InputScreen;
import snake.gui.LauncherClass;

public abstract class AuthenticationScreen extends InputScreen {
    public AuthenticationScreen(LauncherClass launcher) {
        super(launcher);
    }

    /**
     * Creates the username input field.
     *
     * @return textfield of the username
     */
    public TextField createUsernameField() {
        TextField username = new TextField("", getSkin());
        username.setWidth(300);
        username.setHeight(25f);
        username.setPosition(getLauncherClass().getWidth() / 3.33f,
                getLauncherClass().getHeight() / 1.455f);
        username.setMessageText("Username");
        return username;
    }

    /**
     * Creates the password input field.
     *
     * @return textfield of the password
     */
    public TextField createPasswordField() {
        TextField password = new TextField("", getSkin());
        password.setPasswordMode(true);
        password.setHeight(25f);
        password.setPasswordCharacter('*');
        password.setWidth(300);
        password.setPosition(getLauncherClass().getWidth() / 3.33f,
                getLauncherClass().getHeight() / 1.6f);
        password.setMessageText("Password");
        return password;
    }
}
