package snake;

import java.util.ArrayList;
import java.util.List;

import snake.squares.SnakeSquare;
import snake.squares.Square;

public class Snake {
    //snake.squares.Square representing the head of the snake
    private Square head;

    //snake.squares.Square representing the tail of the snake
    private Square tail;

    private transient Square prevTail;

    //List of squares representing the body of the snake (includes head and tail)
    private List<Square> body;

    private static final int SNAKESIZE = 9;

    private transient int startInt;

    private transient Direction direction = Direction.UP;

    /**
     * Creates a snake with the given attributes.
     * @param stepSize distance between squares
     * @param size size of the squares
     */
    public Snake(int stepSize, float size, int startInt) {
        this.startInt = startInt;
        createSnake(stepSize, size, startInt);
    }

    /**
     * Creates snake with the given attributes.
     * @param head head of the snake
     * @param tail tail of the snake
     * @param body body of the snake (includes head and tail)
     */
    public Snake(Square head, Square tail, List<Square> body) {
        this.head = head;
        this.tail = tail;
        this.body = body;
    }

    /**
     * Method which creates the snake.
     * @param stepSize distance between squares
     * @param size size of the square
     */
    public void createSnake(int stepSize, float size, int startInt) {
        List<Square> snake = new ArrayList<>();
        for (int i = 0; i < SNAKESIZE; i++) {
            snake.add(new SnakeSquare(startInt, i * stepSize, size, size));
        }
        this.head = snake.get(snake.size() - 1);
        this.tail = snake.get(0);
        this.body = snake;
    }

    /**
     * Moves the snake to the given x and y coordinate.
     * @param newX new x-coordinate of the head
     * @param newY new y-coordinate of the head
     * @return boolean indicating whether the snake died or not
     */
    public boolean moveSnake(float newX, float newY) {
        Square oldHead = head;
        final Square newHead = new SnakeSquare(newX, newY,
                oldHead.getWidth(), oldHead.getHeight());

        prevTail = tail;
        body.remove(tail);
        tail = body.get(0);


        //If the new head is present in the body then there is a collision.
        if (body.contains(newHead)) {
            return false;
        }

        //Remove the newHead from squares and add it to the snake.
        head = newHead;
        body.add(newHead);
        return true;
    }

    /**
     * Indicates that a snack was eaten last move.
     *      Adds the previous tail back to the list and sets it as the current tail
     */
    public void ateSnack() {
        body.add(0, prevTail);
        tail = prevTail;
    }

    public Square getHead() {
        return head;
    }

    public void setHead(Square head) {
        this.head = head;
    }

    public Square getTail() {
        return tail;
    }

    public void setTail(Square tail) {
        this.tail = tail;
    }

    public List<Square> getBody() {
        return body;
    }

    public void setBody(List<Square> body) {
        this.body = body;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
