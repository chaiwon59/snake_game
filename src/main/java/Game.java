public abstract class Game extends com.badlogic.gdx.Game {

    private transient float width;
    private transient float height;
    private transient String username;

    public Game(float width, float height) {
        this.width = width;
        this.height = height;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
