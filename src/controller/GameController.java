package controller;

import model.Game;
import model.State;

public class GameController {
    private final State state;

    public GameController(State state, Game game) {
        this.state = state;
    }

    public boolean isWin(int player) {
        for (int i = 0; i < 4; i++) {
            if (state.getPiece(player, i) < 56) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinal() {
        return isWin(0) || isWin(1);
    }

    public boolean isWall(int player, int index) {
        boolean wall = false;
        for (int i = 0; i < 4; i++) {
            if (state.getPos(player, i) == index) {
                if (wall) {
                    return true;
                }
                wall = true;
            }
        }
        return false;
    }

    public boolean inRoad(int player, int piece, int steps) {
        return state.getPiece(player, piece) + steps >= 57;
    }

    public boolean canMove(int player, int piece, int steps) {
        for (int i = piece + 1; i <= piece + steps; i++) {
            if (isWall(1 - player, i)) {
                return false;
            }
        }

        return inRoad(player, piece, steps);
    }



    public State move(int player, int piece, int steps) {
        State currentState = state.deepCopy();
        int pos = state.getPos(player, piece);
        int goal = pos + steps;


    }
}
