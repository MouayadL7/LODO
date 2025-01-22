package algorithm;

import controller.GameController;
import controller.Pair;
import model.Game;
import model.State;

import java.util.ArrayList;
import java.util.List;

public class User {
    State state;

    public User(State state) {
        this.state = state;
    }

    public List<Integer> pieceCanMove(int steps) {
        List<Integer> pieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (GameController.canMove(0, i, steps, state)) {
                pieces.add(i);
            }
        }

        return pieces;
    }

    public void movePiece(int piece, int steps) {
        GameController.move(0, piece, steps, state);
    }

    public void enterPiece() {
        GameController.enterToGame(0, state);
    }
}
