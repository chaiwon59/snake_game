import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    /**
     * Launcher method for the game, sets the width and height of the window.
     *
     * @param arg all of the arguments passed by the user
     */
    public static void main(String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //set the height and width of the window
        config.height = 850;
        config.width = 800;

        //alow for forceExit
        config.forceExit = true;

        //load the application
        new LwjglApplication(new Game(config.width, config.height - 50) {
            @Override
            protected void setForegroundFps(int value) {
                config.foregroundFPS = value;
            }
        }, config);
    }
}
