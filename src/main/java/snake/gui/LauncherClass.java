package snake.gui;

import snake.Dao;
import snake.User;

public abstract class LauncherClass extends com.badlogic.gdx.Game {

    private transient float width;
    private transient float height;
    public transient Dao dao;
    public transient User user;

    /**
     * Constructor of the Game class.
     *
     * @param width  width of the window
     * @param height height of the window
     */
    public LauncherClass(float width, float height) {
        this.width = width;
        this.height = height;
        this.dao = new Dao();
    }

    /**
     * Sets the Screen to the main screen.
     */
    @Override
    public void create() {
        this.setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void dispose() {
    }

    public abstract void setForegroundFps(int value);

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public User getUser() {
        return this.user;
    }
}
