package snake.squares;

public class ColouredSquareTest extends SquareTest {

    @Override
    public Square createSquare(int x, int y, int width, int height) {
        return new ColouredSquare(x, y, width, height, null);
    }
}
