import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private static final int SNAKESIZE = 9;
    //All of the board squares (excludes the snake)
    private transient List<Square> squares;

    //Snake class represented by a head, tail and list of the body
    private transient Snake snake;

    //Current square containing the snack
    private transient Square snack;
    private transient Square snackSquare;
    private transient int score;

    private transient int stepSize;
    private transient Game game;
    private transient boolean paused;

    private transient Random random;

    /**
     * Constructs the board.
     *
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     */
    public Board(int stepSize, Game game) {
        this.stepSize = stepSize;
        this.game = game;
        this.paused = false;
        this.random = new Random();

        List<Square> snakeList = new ArrayList<>();
        squares = createBoard(snakeList);
        snake = new Snake(snakeList.get(snakeList.size() - 1), snakeList.get(0), snakeList);
        score = 0;
    }

    /**
     * Creates all the squares present in the board and the snake.
     *
     * @param snake list of the squares of the snake
     * @return a list of all the board squares.
     */
    public final List<Square> createBoard(List<Square> snake) {
        List<Square> currSquares = new ArrayList<>();
        //Go through all of the columns
        for (int i = 0; i < game.getWidth(); i += stepSize) {
            //Go through all of the rows
            for (int j = 0; j < game.getHeight(); j += stepSize) {
                //Adds the first 9 squares to the snake after that they are added to the body
                if (snake.size() < SNAKESIZE) {
                    snake.add(new Square(i, j, stepSize - 2, stepSize - 2));
                } else {
                    currSquares.add(new Square(i, j, stepSize - 2, stepSize - 2));
                }
            }
        }
        return currSquares;
    }

    public void moveUp() {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() + stepSize);
    }

    public void moveDown() {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() - stepSize);
    }

    public void moveRight() {
        move(snake.getHead().getXvalue() + stepSize, snake.getHead().getYvalue());
    }

    public void moveLeft() {
        move(snake.getHead().getXvalue() - stepSize, snake.getHead().getYvalue());
    }

    /**
     * Moves the actual snake, works by removing the old tail and adding a new head.
     *
     * @param newX the new X value of the head.
     * @param newY the new Y value of the head.
     */
    public void move(float newX, float newY) {
        if (paused) {
            return;
        }
        //Check  whether there is a snack, if not create one
        if (snack == null) {
            createSnack(random.nextInt((int) (game.getWidth() - stepSize) / stepSize),
                    random.nextInt((int) (game.getWidth() - stepSize) / stepSize));
        }
        //check whether the snake is in bounds
        //      if not, kill the app (needs to be changed to deathscreen)
        if (newY > game.getHeight() - stepSize || newX > game.getWidth() - stepSize
                || newX < 0 || newY < 0) {
            game.setScreen(new DeathScreen(score, stepSize, game));
            return;
        }

        Square oldHead = snake.getHead();
        Square newHead = new Square(newX, newY, oldHead.getWidth(), oldHead.getHeight());

        //Check whether there is a collision with the snack
        //      if there is then don't remove the tail (snake grows).
        if (snackSquare.equals(newHead)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // post a Runnable to the rendering thread that processes the result
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            StyleUtility.getSnackSound().play();
                        }
                    });
                }
            }).start();
            score += 10;
            createSnack(random.nextInt((int) (game.getWidth() - stepSize) / stepSize),
                    random.nextInt((int) (game.getWidth() - stepSize) / stepSize));
        } else {
            //Remove the old tail from the snake body and set the new tail to the new front element.
            Square oldTail = snake.getTail();
            squares.add(new Square(
                    oldTail.getXvalue(), oldTail.getYvalue(), stepSize - 2, stepSize - 2));
            snake.getBody().remove(oldTail);
            snake.setTail(snake.getBody().get(0));
        }

        //If the new head is present in the body then there is a collision.
        if (snake.getBody().contains(newHead)) {
            game.setScreen(new DeathScreen(score, stepSize, game));
            return;
        }

        //Remove the newHead from squares and add it to the snake.
        squares.remove(newHead);
        snake.setHead(newHead);
        snake.getBody().add(newHead);
    }

    /**
     * Creates a random snack somewhere on the board (not inside of the snake).
     */
    public void createSnack(int random1, int random2) {
        //Set the value of the snack (add some offset to center it in the square)
        snack = new Square(random1 * stepSize + stepSize / 5, random2 * stepSize + stepSize / 5,
                3 * stepSize / 5, 3 * stepSize / 5);
        snackSquare = new Square(random1 * stepSize, random2 * stepSize,
                stepSize - 2, stepSize - 2);

        //Use the snackSquare for the equals method since the actual snack square uses some offset
        if (snake.getBody().contains(snackSquare)) {
            //If the snack is within the body of the snake, create a new square.
            createSnack(random.nextInt((int) (game.getWidth() - stepSize) / stepSize),
                    random.nextInt((int) (game.getWidth() - stepSize) / stepSize));
        }
    }

    /**
     * Getter for list of the squares excludes the snake.
     *
     * @return list of the current board
     */
    public List<Square> getSquares() {
        return squares;
    }

    /**
     * Getter for list of squares representing the snake.
     *
     * @return list of squares of the snake
     */
    public List<Square> getSnakeBody() {
        return snake.getBody();
    }

    /**
     * Getter for the head of the snake.
     *
     * @return Square containing the head of the snake
     */
    public Square getHead() {
        return snake.getHead();
    }

    /**
     * Getter for the current snack.
     *
     * @return Square containing the snack
     */
    public Square getSnack() {
        return snack;
    }

    /**
     * Getter for the current score.
     *
     * @return int containing the score of the user
     */
    public int getScore() {
        return score;
    }

    public int getInitSnakeSize() {
        return SNAKESIZE;
    }

    public void setPaused() {
        this.paused = !this.paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public float getWidth() {
        return game.getWidth();
    }

    public float getHeight() {
        return game.getHeight();
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Snake getSnake() {
        return this.snake;
    }
}
