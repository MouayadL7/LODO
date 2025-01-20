package algorithm;

import controller.GameController;
import model.Game;

public class User {
    GameController gameController;

    public User(GameController gameController) {
        this.gameController = gameController;
    }

    public int throwDice() {
        return Game.throwDice();
    }

    public void movePiece(int piece, int steps) {
        gameController.move(0, piece, steps);
    }

    public void enterPiece() {
        gameController.enterToGame(0);
    }
}
