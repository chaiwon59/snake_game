import java.util.Objects;

public class Square {
    private transient float xvalue;
    private transient float yvalue;
    private transient float width;
    private transient float height;

    /**
     * Constructor of the Square class.
     *
     * @param xvalue x coordinate of the square
     * @param yvalue y coordinate of the square
     * @param width  width of the window
     * @param height height of the window
     */
    public Square(float xvalue, float yvalue, float width, float height) {
        this.xvalue = xvalue;
        this.yvalue = yvalue;
        this.width = width;
        this.height = height;
    }


    public float getXvalue() {
        return xvalue;
    }

    public float getYvalue() {
        return yvalue;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Square square = (Square) o;
        return Float.compare(square.xvalue, xvalue) == 0
                && Float.compare(square.yvalue, yvalue) == 0;
    }

    @Override
    public String toString() {
        return "x: " + xvalue + " y: " + yvalue + " width: " + width + " height: " + height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xvalue, yvalue, width, height);
    }
}
