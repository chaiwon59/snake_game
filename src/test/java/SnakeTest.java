import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Square newHead = new Square(2, 2, 1, 1);
        snake.setHead(newHead);

        assertEquals(newHead, snake.getHead());
    }

    @Test
    public void testSetTail() {
        assertEquals(tail, snake.getTail());

        Square newTail = new Square(2, 2, 1, 1);
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
}
