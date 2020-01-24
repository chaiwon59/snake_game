package snake.gui.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.List;
import snake.Direction;
import snake.Snake;

public class InputReader {
    private transient ArrayList<Integer> keys1;
    private transient ArrayList<Integer> keys2;

    public InputReader() {
        this.keys1 = createKeys1();
        this.keys2 = createKeys2();
    }

    /**
     * Creates the input keys for player1.
     *
     * @return arraylist containing these keys.
     */
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
     * Adds the appropriate value to the hashmap.
     *
     * @param snake snake which is to be moved
     * @param keys  keys corresponding to up, down, right, left in that order.
     */
    public Direction checkQueue(Snake snake, List<Integer> keys) {
        Direction beingPressed = checkBeingPressed(keys, snake.getDirection());

        return beingPressed == null ? checkJustPressed(keys, snake.getDirection()) : beingPressed;
    }

    /**
     * Checks whether the WASD-key is being pressed.
     *
     * @return Direction which is being pressed
     */
    private Direction checkBeingPressed(List<Integer> keys, Direction direction) {
        if (Gdx.input.isKeyPressed(keys.get(0)) && direction != Direction.DOWN) {
            return Direction.UP;
        } else if (Gdx.input.isKeyPressed(keys.get(1)) && direction != Direction.UP) {
            return Direction.DOWN;
        } else if (Gdx.input.isKeyPressed(keys.get(2)) && direction != Direction.LEFT) {
            return Direction.RIGHT;
        } else if (Gdx.input.isKeyPressed(keys.get(3)) && direction != Direction.RIGHT) {
            return Direction.LEFT;
        }
        return null;
    }

    /**
     * Checks whether a key was pressed since the previous frame.
     *
     * @return Direction which was pressed
     */
    private Direction checkJustPressed(List<Integer> keys, Direction direction) {
        if (Gdx.input.isKeyJustPressed(keys.get(0)) && direction != Direction.DOWN) {
            return Direction.UP;
        } else if (Gdx.input.isKeyJustPressed(keys.get(1)) && direction != Direction.UP) {
            return Direction.DOWN;
        } else if (Gdx.input.isKeyJustPressed(keys.get(2)) && direction != Direction.LEFT) {
            return Direction.RIGHT;
        } else if (Gdx.input.isKeyJustPressed(keys.get(3)) && direction != Direction.RIGHT) {
            return Direction.LEFT;
        }
        return null;
    }

    public ArrayList<Integer> getKeys1() {
        return this.keys1;
    }

    public ArrayList<Integer> getKeys2() {
        return this.keys2;
    }
}
