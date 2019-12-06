package snake.squares;

public class BoardSquareTest extends ColouredSquareTest {
    @Override
    public Square createSquare(int x, int y, int width, int height) {
        return new SnakeSquare(x, y, width, height);
    }
}
