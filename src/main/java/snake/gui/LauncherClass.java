package snake.gui;

import snake.User;

public abstract class LauncherClass extends com.badlogic.gdx.Game {

    private transient float width;
    private transient float height;
    private transient User user;

    /**
     * Constructor of the Game class.
     *
     * @param width  width of the window
     * @param height height of the window
     */
    public LauncherClass(float width, float height) {
        this.width = width;
        this.height = height;
        this.user = new User();
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

    protected abstract void setForegroundFps(int value);

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
