import java.util.List;

public class Snake {
    //Square representing the head of the snake
    private Square head;

    //Square representing the tail of the snake
    private Square tail;

    //List of squares representing the body of the snake (includes head and tail)
    private List<Square> body;

    /**
     * Constructor of the snake class.
     * @param head the square representing the head of the snake
     * @param tail the square representing the tail of the snake
     * @param body a list of squares representing the snake (includes head and tail)
     */
    public Snake(Square head, Square tail, List<Square> body) {
        this.head = head;
        this.tail = tail;
        this.body = body;
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
}
