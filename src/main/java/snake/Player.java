package snake;

public class Player {
    private transient int score;

    private transient int scoreIncrease;

    private transient int numberOfMoves = 1;

    public Player() {
        this.score = 0;
        this.scoreIncrease = 10;
    }

    public void increaseScore() {
        this.score += scoreIncrease;
    }

    public void setScoreIncrease(int scoreIncrease) {
        this.scoreIncrease = scoreIncrease;
    }

    public int getScoreIncrease() {
        return scoreIncrease;
    }

    public int getScore() {
        return this.score;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int newNumber) {
        this.numberOfMoves = newNumber;
    }
}
