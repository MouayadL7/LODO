package algorithm;

import controller.GameController;
import controller.Pair;
import model.Game;

import java.util.ArrayList;
import java.util.List;

public class User {
    GameController gameController;

    public User(GameController gameController) {
        this.gameController = gameController;
    }

    public int throwDice() {
        return Game.throwDice();
    }

    public List<Integer> canPiece(int steps) {
        List<Integer> pieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (gameController.canMove(0, i, steps, gameController.getState())) {
                pieces.add(i);
            }
        }

        return pieces;
    }

    public void movePiece(int piece, int steps) {
        gameController.move(0, piece, steps, gameController.getState());
    }

    public void enterPiece() {
        gameController.enterToGame(0, gameController.getState());
    }
}
