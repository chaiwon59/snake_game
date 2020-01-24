package snake.gui.gamescreens;

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
import snake.gui.InputScreen;
import snake.gui.StyleUtility;
import snake.squares.ColouredSquare;
import snake.squares.Square;


//suppress warning for enhanced for-loop
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class SinglePlayerGameScreen extends InputScreen implements Screen {
    transient Game game;
    transient Snake player1;

    static final transient float FRAMETIME = 1 / 6f;
    transient float currentFrameTime = FRAMETIME;

    transient Direction lastPressed;

    transient InputReader reader;

    /**
     * Initializes the snake.Gui.GameScreen.
     *
     * @param game current instance of the game.
     */
    public SinglePlayerGameScreen(Game game) {
        super(game.getLauncher());
        this.game = game;

        this.player1 = game.getPlayer1();
        this.reader = new InputReader();
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
        currentFrameTime -= delta;

        //Set the background
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setPaused();
        }

        if (!game.isPaused()) {
            game.decreaseTime(delta);
        }

        checkLastPressed();

        if (currentFrameTime < 0) {
            currentFrameTime = FRAMETIME;

            move();
        }

        //check whether the game is over (prevents overlapping death screens)
        if (game.getGameIsOver()) {
            return;
        }

        //Renders the squares of the board and snake
        renderSquares();
    }

    /**
     * Updates the lastPressed key.
     */
    public void checkLastPressed() {
        Direction newPressed = reader.checkQueue(game.getPlayer1(), reader.getKeys1());
        this.lastPressed = newPressed != null ? newPressed : lastPressed;
    }

    /**
     * Calls the method to move the player (override by subclass).
     */
    public void move() {
        for (int i = 0; i < player1.getPlayer().getNumberOfMoves(); i++) {
            move(player1, this.lastPressed);
        }
    }

    /**
     * Actually moves the player based on the last key pressed.
     *
     * @param player      player to be moved.
     * @param lastPressed direction associated with the key last pressed.
     */
    public void move(Snake player, Direction lastPressed) {
        Direction max = lastPressed != null ? lastPressed : player.getDirection();
        player.setDirection(max);

        max.moveDirection(game, player);
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

        addSecondSnake(actors);
        addFirstSnake(actors);

        if (game.isPaused()) {
            actors.add(new ColouredSquare(0, 0, getLauncherClass().getWidth(),
                    getLauncherClass().getHeight(), StyleUtility.getPausedColor()));
        }
        return actors;
    }

    private void addFirstSnake(List<Actor> actors) {
        Square head = game.getPlayer1Head();
        actors.add(new ColouredSquare(head.getXvalue() + head.getWidth() / 4,
                head.getYvalue() + head.getHeight() / 4,
                head.getWidth() / 2, head.getHeight() / 2, StyleUtility.getGreen()));
        actors.add(new ColouredSquare(0,
                getLauncherClass().getHeight(), getLauncherClass().getWidth(),
                50, StyleUtility.getLightGrey()));
    }

    private void addSecondSnake(List<Actor> actors) {
        if (game instanceof MultiPlayerGame) {
            MultiPlayerGame mg = (MultiPlayerGame) game;
            actors.addAll(mg.getPlayer2Body());

            Square head2 = ((MultiPlayerGame) game).getPlayer2Head();
            actors.add(new ColouredSquare(head2.getXvalue() + head2.getWidth() / 4,
                    head2.getYvalue() + head2.getHeight() / 4,
                    head2.getWidth() / 2, head2.getHeight() / 2, StyleUtility.getGreen()));
        }
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

        renderSnack(batch);
        renderPowerup(batch);

        for (Actor current : createSecondaryActors()) {
            current.draw(batch, 1);
        }
        batch.end();
    }

    private void renderSnack(SpriteBatch batch) {
        Square snackSquare = game.getSnack();
        if (snackSquare != null) {
            batch.draw(StyleUtility.getSnack(), snackSquare.getXvalue(),
                    snackSquare.getYvalue(), snackSquare.getWidth(), snackSquare.getHeight());
        }
    }

    private void renderPowerup(SpriteBatch batch) {
        Square powerUpSquare = game.getPowerUpSquare();
        if (game.getPowerUp() != null && powerUpSquare != null && !game.isActive()) {
            batch.draw(StyleUtility.getPowerUpTexture(game.getPowerUp()), powerUpSquare.getXvalue(),
                    powerUpSquare.getYvalue(), powerUpSquare.getWidth(), powerUpSquare.getHeight());
        }
    }
}
