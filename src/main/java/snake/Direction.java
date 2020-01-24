package snake;

import snake.games.Game;

public enum Direction {
    UP {
        public void moveDirection(Game game, Snake player) {
            game.moveUp(player);
        }
    },
    RIGHT {
        public void moveDirection(Game game, Snake player) {
            game.moveRight(player);
        }
    },
    DOWN {
        public void moveDirection(Game game, Snake player) {
            game.moveDown(player);
        }
    }, LEFT {
        public void moveDirection(Game game, Snake player) {
            game.moveLeft(player);
        }
    };

    public abstract void moveDirection(Game game, Snake player);
}
