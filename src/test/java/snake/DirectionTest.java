package snake;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.games.Game;

public class DirectionTest {
    private transient Game game;
    private transient Snake snake;

    @BeforeEach
    public void setUp() {
        this.game = mock(Game.class);
        this.snake = mock(Snake.class);
    }

    @Test
    public void testUp() {
        Direction direction = Direction.UP;

        direction.moveDirection(game, snake);

        verify(game, times(1)).moveUp(snake);
    }

    @Test
    public void testRight() {
        Direction direction = Direction.RIGHT;

        direction.moveDirection(game, snake);

        verify(game, times(1)).moveRight(snake);
    }

    @Test
    public void testDown() {
        Direction direction = Direction.DOWN;

        direction.moveDirection(game, snake);

        verify(game, times(1)).moveDown(snake);
    }

    @Test
    public void testLeft() {
        Direction direction = Direction.LEFT;

        direction.moveDirection(game, snake);

        verify(game, times(1)).moveLeft(snake);
    }
}
