package snake.gui.authentication;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.EmailSender;
import snake.gui.InputScreen;
import snake.gui.LauncherClass;

public class ResetScreen extends AuthenticationScreen {
    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public ResetScreen(LauncherClass launcherClass) {
        super(launcherClass);
    }

    @Override
    public List<Actor> createActors() {
        TextField username = createUsernameField();

        return new ArrayList<>(Arrays.asList(username, createSendButton(username)));
    }

    /**
     * Create a send button which switches the screen.
     *
     * @param username username of the user
     * @return button with a listener.
     */
    private TextButton createSendButton(TextField username) {
        return createButton("Send",
                getLauncherClass().getWidth() / 2.35f, getLauncherClass().getHeight() / 1.6f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        EmailSender sender = new EmailSender(
                                getLauncherClass().dao, username.getText());
                        if (!sender.checkExistentUsername()) {
                            createDialog("User doesn't exist", "Error");
                        } else {
                            new Thread(sender).start();
                            getLauncherClass().setScreen(new LoginScreen(getLauncherClass()));
                        }
                    }
                });
    }
}
