package snake.gui.authentication;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import snake.Dao;
import snake.gui.InputScreen;
import snake.gui.LauncherClass;
import snake.gui.MainMenu;

public class UpdatePasswordScreen extends AuthenticationScreen {
    private transient Dao dao;

    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public UpdatePasswordScreen(LauncherClass launcherClass) {
        super(launcherClass);
        this.dao = launcherClass.dao;
    }

    @Override
    public List<Actor> createActors() {
        TextField password = createPasswordField();

        return new ArrayList<>(Arrays.asList(password, createUpdatePassword(password)));
    }

    /**
     * Creates a reset password button which changes the screen to the reset screen.
     * @return textbutton with the functionality
     */
    private TextButton createUpdatePassword(TextField password) {
        return createButton("Reset Password", getLauncherClass().getWidth() / 3f,
                getLauncherClass().getHeight() / 1.75f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dao.updatePassword(getLauncherClass().getUser().getUsername(),
                                password.getText(), false);
                        getLauncherClass().setScreen(new MainMenu(getLauncherClass()));
                    }
                });
    }
}
