package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import snake.MusicPlayer;
import snake.levels.Level;
import snake.levels.NoWallsLevel;
import snake.levels.WalledLevel;
import snake.squares.ColouredSquare;
import snake.squares.Square;


//suppress warning for enhanced for-loop
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class GameScreen extends InputScreen implements Screen {
    private transient Level level;

    private transient Direction direction = Direction.UP;
    private transient int stepSize;

    private enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    /**
     * Initializes the snake.Gui.GameScreen.
     *
     * @param stepSize determines the amount of squares
     * @param game     instance of the getGame()
     */
    public GameScreen(int stepSize, Game game) {
        super(game);
        this.stepSize = stepSize;
        this.level = game.getUser().isNoWalls()
                ? new NoWallsLevel(game, stepSize) : new WalledLevel(game, stepSize);

        getGame().setForegroundFps(6);
    }

    /**
     * Turns off the menu music and starts playing the ingame music.
     */
    @Override
    public void show() {
        MusicPlayer.stopMenu();
        MusicPlayer.playInGame();
    }

    /**
     * Renders the current screen.
     *
     * @param delta determines the opacity
     */
    @Override
    public void render(float delta) {
        //Set the background
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            level.setPaused();
        }
        //Move the snake
        move();

        //Renders the squares of the board and snake
        renderSquares();
    }

    /**
     * Creates a list of the primary actors.
     * @return list of actors.
     */
    @Override
    public List<Actor> createActors() {
        List<Actor> actors = new ArrayList<>();
        actors.addAll(level.getSquares());
        actors.addAll(level.getSnakeBody());

        Square head = level.getHead();
        actors.add(new ColouredSquare(head.getXvalue() + head.getWidth() / 4,
                head.getYvalue() + head.getHeight() / 4,
                head.getWidth() / 2, head.getHeight() / 2, StyleUtility.getGreen()));
        actors.add(new ColouredSquare(0, getGame().getHeight(), getGame().getWidth(),
                50, StyleUtility.getLightGrey()));

        if (level.isPaused()) {
            actors.add(new ColouredSquare(0, 0, getGame().getWidth(),
                    getGame().getHeight(), StyleUtility.getPausedColor()));
        }
        return actors;
    }

    /**
     * List of the secondary actors to be rendered.
     * @return list of actors
     */
    private List<Actor> createSecondaryActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(createLabel(String.valueOf(level.getScore()), getGame().getHeight() + 10));
        if (level.isPaused()) {
            actors.add(createLabel("Paused, press escape to resume", getGame().getHeight() / 2));
        }
        return actors;
    }

    /**
     * Renders the squares, snake and snack.
     */
    public void renderSquares() {
        SpriteBatch batch = StyleUtility.getBatch();
        batch.begin();
        for (Actor current : createActors()) {
            current.draw(batch, 1);
        }
        batch.end();
        //render the score

        batch.begin();
        Square snackSquare = level.getSnack();
        batch.draw(StyleUtility.getSnack(), snackSquare.getXvalue(),
                snackSquare.getYvalue(), snackSquare.getWidth(), snackSquare.getHeight());

        for (Actor current : createSecondaryActors()) {
            current.draw(batch, 1);
        }
        batch.end();
    }

    /**
     * Method which moves the snake based on the user input.
     */
    public void move() {
        //Check the user input and move appropriately
        // and set the direction (still needs to be cleaned up).
        if (!checkBeingPressed()) {
            if (!checkJustPressed()) {
                //If there is no user-input keep moving in the previous direction.
                switch (direction) {
                    case RIGHT:
                        level.moveRight();
                        break;
                    case DOWN:
                        level.moveDown();
                        break;
                    case LEFT:
                        level.moveLeft();
                        break;
                    default:
                        level.moveUp();
                        break;
                }
            }
        }
    }

    /**
     * Checks whether the WASD-key is being pressed.
     * @return boolean indicating whether a key is being pressed.
     */
    private boolean checkBeingPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && direction != Direction.DOWN) {
            level.moveUp();
            direction = Direction.UP;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && direction != Direction.UP) {
            level.moveDown();
            direction = Direction.DOWN;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && direction != Direction.LEFT) {
            level.moveRight();
            direction = Direction.RIGHT;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && direction != Direction.RIGHT) {
            level.moveLeft();
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    /**
     * Checks whether a key was pressed since the previous frame.
     * @return boolean indicating whether a key was pressed.
     */
    private boolean checkJustPressed() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && direction != Direction.DOWN) {
            level.moveUp();
            direction = Direction.UP;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && direction != Direction.UP) {
            level.moveDown();
            direction = Direction.DOWN;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) && direction != Direction.LEFT) {
            level.moveRight();
            direction = Direction.RIGHT;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A) && direction != Direction.RIGHT) {
            level.moveLeft();
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }
}
