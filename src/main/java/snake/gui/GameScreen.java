package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import snake.Direction;
import snake.MusicPlayer;
import snake.Snake;
import snake.games.Game;
import snake.games.MultiPlayerGame;
import snake.games.levels.Level;
import snake.games.levels.NoWallsLevel;
import snake.games.levels.WalledLevel;
import snake.squares.ColouredSquare;
import snake.squares.Square;


//suppress warning for enhanced for-loop
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class GameScreen extends InputScreen implements Screen {
    private transient Game game;

    private transient ArrayList<Integer> keys1;
    private transient ArrayList<Integer> keys2;

    /**
     * Initializes the snake.Gui.GameScreen.
     *
     * @param game current instance of the game.
     */
    public GameScreen(Game game) {
        super(game.getLauncher());
        this.game = game;

        getLauncherClass().setForegroundFps(6);

        this.keys1 = createKeys1();
        this.keys2 = createKeys2();
    }

    private ArrayList<Integer> createKeys1() {
        ArrayList<Integer> result = new ArrayList<>();

        result.add(Input.Keys.W);
        result.add(Input.Keys.S);
        result.add(Input.Keys.D);
        result.add(Input.Keys.A);

        return result;
    }

    private ArrayList<Integer> createKeys2() {
        ArrayList<Integer> result = new ArrayList<>();

        result.add(Input.Keys.UP);
        result.add(Input.Keys.DOWN);
        result.add(Input.Keys.RIGHT);
        result.add(Input.Keys.LEFT);

        return result;
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
            game.setPaused();
        }
        //Move the snake
        move(game.getPlayer1(), keys1);

        //check whether the game is over (prevents overlapping death screens)
        if (game.getGameIsOver()) {
            return;
        }

        if (game instanceof MultiPlayerGame) {
            move(((MultiPlayerGame) game).getPlayer2(), keys2);
            ((MultiPlayerGame) game).checkSnakeCollision();
        }

        //Renders the squares of the board and snake
        renderSquares();
    }

    /**
     * Creates a list of the primary actors.
     *
     * @return list of actors.
     */
    @Override
    public List<Actor> createActors() {
        List<Actor> actors = new ArrayList<>();
        actors.addAll(game.getSquares());
        actors.addAll(game.getPlayer1Body());

        if (game instanceof MultiPlayerGame) {
            MultiPlayerGame mg = (MultiPlayerGame) game;
            actors.addAll(mg.getPlayer2Body());

            Square head2 = ((MultiPlayerGame) game).getPlayer2Head();
            actors.add(new ColouredSquare(head2.getXvalue() + head2.getWidth() / 4,
                    head2.getYvalue() + head2.getHeight() / 4,
                    head2.getWidth() / 2, head2.getHeight() / 2, StyleUtility.getGreen()));
        }

        Square head = game.getPlayer1Head();
        actors.add(new ColouredSquare(head.getXvalue() + head.getWidth() / 4,
                head.getYvalue() + head.getHeight() / 4,
                head.getWidth() / 2, head.getHeight() / 2, StyleUtility.getGreen()));
        actors.add(new ColouredSquare(0,
                getLauncherClass().getHeight(), getLauncherClass().getWidth(),
                50, StyleUtility.getLightGrey()));

        if (game.isPaused()) {
            actors.add(new ColouredSquare(0, 0, getLauncherClass().getWidth(),
                    getLauncherClass().getHeight(), StyleUtility.getPausedColor()));
        }
        return actors;
    }

    /**
     * List of the secondary actors to be rendered.
     *
     * @return list of actors
     */
    private List<Actor> createSecondaryActors() {
        List<Actor> actors = new ArrayList<>();
        if (game instanceof MultiPlayerGame) {
            actors.add(createLabel(String.valueOf(game.getScore1()),
                    getLauncherClass().getWidth() / 3, getLauncherClass().getHeight() + 10));
            actors.add(createLabel(String.valueOf(((MultiPlayerGame) game).getScore2()),
                    getLauncherClass().getWidth() * 2 / 3, getLauncherClass().getHeight() + 10));
        } else {
            actors.add(createLabel(String.valueOf(game.getScore1()), null,
                    getLauncherClass().getHeight() + 10));
        }

        if (game.isPaused()) {
            actors.add(createLabel("Paused, press escape to resume",
                    null, getLauncherClass().getHeight() / 2));
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
        Square snackSquare = game.getSnack();
        batch.draw(StyleUtility.getSnack(), snackSquare.getXvalue(),
                snackSquare.getYvalue(), snackSquare.getWidth(), snackSquare.getHeight());

        for (Actor current : createSecondaryActors()) {
            current.draw(batch, 1);
        }
        batch.end();
    }

    /**
     * Moves the snake in the appropriate direction.
     *
     * @param snake snake which is to be moved
     * @param keys  keys corresponding to up, down, right, left in that order.
     */
    public void move(Snake snake, List<Integer> keys) {
        //Check the user input and move appropriately
        // and set the direction (still needs to be cleaned up).
        if (!checkBeingPressed(keys, snake.getDirection(), snake)) {
            if (!checkJustPressed(keys, snake.getDirection(), snake)) {
                //If there is no user-input keep moving in the previous direction.
                switch (snake.getDirection()) {
                    case RIGHT:
                        game.moveRight(snake);
                        break;
                    case DOWN:
                        game.moveDown(snake);
                        break;
                    case LEFT:
                        game.moveLeft(snake);
                        break;
                    default:
                        game.moveUp(snake);
                        break;
                }
            }
        }
    }

    /**
     * Checks whether the WASD-key is being pressed.
     *
     * @return boolean indicating whether a key is being pressed.
     */
    private boolean checkBeingPressed(List<Integer> keys, Direction direction, Snake snake) {
        if (Gdx.input.isKeyPressed(keys.get(0)) && direction != Direction.DOWN) {
            game.moveUp(snake);
            snake.setDirection(Direction.UP);
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get(1)) && direction != Direction.UP) {
            game.moveDown(snake);
            snake.setDirection(Direction.DOWN);
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get(2)) && direction != Direction.LEFT) {
            game.moveRight(snake);
            snake.setDirection(Direction.RIGHT);
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get(3)) && direction != Direction.RIGHT) {
            game.moveLeft(snake);
            snake.setDirection(Direction.LEFT);
            return true;
        }
        return false;
    }

    /**
     * Checks whether a key was pressed since the previous frame.
     *
     * @return boolean indicating whether a key was pressed.
     */
    private boolean checkJustPressed(List<Integer> keys, Direction direction, Snake snake) {
        if (Gdx.input.isKeyJustPressed(keys.get(0)) && direction != Direction.DOWN) {
            game.moveUp(snake);
            snake.setDirection(Direction.UP);
            return true;
        } else if (Gdx.input.isKeyJustPressed(keys.get(1)) && direction != Direction.UP) {
            game.moveDown(snake);
            snake.setDirection(Direction.DOWN);
            return true;
        } else if (Gdx.input.isKeyJustPressed(keys.get(2)) && direction != Direction.LEFT) {
            game.moveRight(snake);
            snake.setDirection(Direction.RIGHT);
            return true;
        } else if (Gdx.input.isKeyJustPressed(keys.get(3)) && direction != Direction.RIGHT) {
            game.moveLeft(snake);
            snake.setDirection(Direction.LEFT);
            return true;
        }
        return false;
    }
}
