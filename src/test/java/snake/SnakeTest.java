package snake;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public class SnakeTest {
    private transient Square head;
    private transient Square tail;
    private transient List<Square> body;
    private transient Snake snake;

    /**
     * Sets up the environment.
     */
    @BeforeEach
    public void setUp() {
        head = new Square(0, 0, 0, 0);
        tail = new Square(1, 1, 0, 0);
        body = new ArrayList<>();
        body.add(head);
        body.add(tail);

        this.snake = new Snake(head, tail, body);
    }

    @Test
    public void testConstructor() {
        assertEquals(head, snake.getHead());
        assertEquals(tail, snake.getTail());
        assertEquals(body, snake.getBody());
    }

    @Test
    public void testSetHead() {
        assertEquals(head, snake.getHead());

        Square newHead = new ColouredSquare(2, 2, 1, 1, null);
        snake.setHead(newHead);

        assertEquals(newHead, snake.getHead());
    }

    @Test
    public void testSetTail() {
        assertEquals(tail, snake.getTail());

        Square newTail = new ColouredSquare(2, 2, 1, 1, null);
        snake.setTail(newTail);

        assertEquals(newTail, snake.getTail());
    }

    @Test
    public void testSetBody() {
        assertEquals(body, snake.getBody());

        List<Square> newBody = new ArrayList<>();
        snake.setBody(newBody);

        assertEquals(newBody, snake.getBody());
    }

    @Test
    public void testCreateSnake() {
        snake.createSnake(50, 48);

        assertEquals(9, snake.getBody().size());
        assertEquals(snake.getHead(), snake.getBody().get(8));
        assertEquals(snake.getTail(), snake.getBody().get(0));
    }

    @Test
    public void testMoveSnake() {
        Square prevHead = snake.getHead();

        snake.moveSnake(1, 0);

        assertEquals(new Square(1, 0, 0, 0), snake.getHead());
        assertEquals(prevHead, snake.getTail());
    }

    @Test
    public void testMoveSnakeAteSnack() {
        Square prevHead = snake.getHead();
        final Square prevTail = snake.getTail();

        snake.moveSnake(1, 0);

        assertEquals(new Square(1, 0, 0, 0), snake.getHead());
        assertEquals(prevHead, snake.getTail());

        snake.ateSnack();

        assertEquals(new Square(1, 0, 0, 0), snake.getHead());
        assertEquals(prevTail, snake.getTail());
    }
}
