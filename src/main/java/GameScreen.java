import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class GameScreen implements Screen {
    private transient ShapeRenderer renderer;
    private transient Board board;
    private transient Game game;

    //0 is up, 1 is right, 2 is down, 3 is left
    private transient Direction direction = Direction.UP;
    private transient int stepSize;

    private enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    /**
     * Initializes the GameScreen.
     *
     * @param stepSize determines the amount of squares
     * @param game     instance of the game
     */
    public GameScreen(int stepSize, Game game) {
        this.stepSize = stepSize;
        this.renderer = new ShapeRenderer();
        this.board = new Board(stepSize, game);
        this.game = game;

        game.setForegroundFps(6);
    }

    /**
     * Renders the current screen.
     *
     * @param delta determines the opacity
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            board.setPaused();
        }
        //Move the snake
        move();

        //Set the background
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders the squares of the board and snake
        renderSquares();
    }

    /**
     * Renders the squares, snake and snack.
     */
    public void renderSquares() {
        //render the squares
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(0.6f, 0.6f, 0.6f, 1);

        List<Square> boardSquares = board.getSquares();
        for (int i = 0; i < boardSquares.size(); i++) {
            Square current = boardSquares.get(i);
            renderer.rect(current.getXvalue(), current.getYvalue(),
                    current.getWidth(), current.getHeight());
        }

        //render the snake
        renderer.setColor(0, 0, 0, 1);
        for (Square current : board.getSnakeBody()) {
            renderer.rect(current.getXvalue(), current.getYvalue(),
                    current.getWidth(), current.getHeight());
        }

        renderer.setColor(0, 1, 0, 1);
        Square head = board.getHead();
        renderer.rect(
                head.getXvalue() + head.getWidth() / 4, head.getYvalue() + head.getHeight() / 4,
                head.getWidth() / 2, head.getHeight() / 2);


        //render bar at the top of the screen in which the score is displayed
        renderer.setColor(0.2f, 0.2f, 0.2f, 1);
        renderer.rect(0, game.getHeight(), game.getWidth(), 50);

        if (board.isPaused()) {
            renderer.setColor(0.6f, 0.6f, 0.6f, 0.7f);
            renderer.rect(0, 0, game.getWidth(), game.getHeight());
        }
        renderer.end();

        SpriteBatch batch = StyleUtility.getBatch();

        //render the score
        batch.begin();
        //render the snack
        Square current = board.getSnack();
        batch.draw(StyleUtility.getSnack(), current.getXvalue(), current.getYvalue(),
                current.getWidth(), current.getHeight());


        BitmapFont font = StyleUtility.getFont();
        font.draw(batch, String.valueOf(board.getScore()),
                game.getWidth() / 2, game.getHeight() + 30);
        if (board.isPaused()) {
            font.getRegion().getTexture()
                    .setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(1.5f);
            font.draw(batch, "Paused, press escape to resume",
                    game.getWidth() / 16f, game.getHeight() / 2);
            font.getData().setScale(1);
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
                        board.moveRight();
                        break;
                    case DOWN:
                        board.moveDown();
                        break;
                    case LEFT:
                        board.moveLeft();
                        break;
                    default:
                        board.moveUp();
                        break;
                }
            }
        }
    }

    private boolean checkBeingPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && direction != Direction.DOWN) {
            board.moveUp();
            direction = Direction.UP;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && direction != Direction.UP) {
            board.moveDown();
            direction = Direction.DOWN;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && direction != Direction.LEFT) {
            board.moveRight();
            direction = Direction.RIGHT;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && direction != Direction.RIGHT) {
            board.moveLeft();
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    private boolean checkJustPressed() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && direction != Direction.DOWN) {
            board.moveUp();
            direction = Direction.UP;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && direction != Direction.UP) {
            board.moveDown();
            direction = Direction.DOWN;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) && direction != Direction.LEFT) {
            board.moveRight();
            direction = Direction.RIGHT;
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A) && direction != Direction.RIGHT) {
            board.moveLeft();
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
