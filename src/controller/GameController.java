package controller;

import model.Game;
import model.State;

import java.util.ArrayList;
import java.util.List;

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

    public boolean checkHouse(int player) {
        boolean hasPieceInHouse = false;
        for (int i = 0; i < 4; i++) {
            if (state.getPiece(player, i) == -1) {
                hasPieceInHouse = true;
                break;
            }
        }
        return hasPieceInHouse;
    }

    public boolean canMove(int player, int piece, int steps) {
        for (int i = piece + 1; i <= piece + steps; i++) {
            if (isWall(1 - player, i)) {
                return false;
            }
        }

        return inRoad(player, piece, steps);
    }

    public void move(int player, int piece, int steps) {
        int pos = state.getPos(player, piece);
        int goal = (pos + steps) % 52;

        state.setPiece(player, piece, state.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            state.exit(1 - player, goal);
        }
    }

    public void enterToGame(int player) {
        state.entry(player);
    }

    public State generateNextState(int player, int piece, int steps) {
        State currentState = state.deepCopy();
        int pos = currentState.getPos(player, piece);
        int goal = (pos + steps) % 52;

        currentState.setPiece(player, piece, state.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            currentState.exit(1 - player, goal);
        }

        return currentState;
    }

    public State enterToGame_NextState(int player) {
        State newState = state.deepCopy();

        newState.entry(player);

        return newState;
    }

    public List<State> nextStates(int player, State state1, int steps) {
        List<State> stateList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (canMove(player, i, steps)) {
                State nextState = generateNextState(player, i, steps);
                stateList.add(nextState);
            }
        }

        if (steps == 6) {
            if (checkHouse(player)) {
                State nextState = enterToGame_NextState(player);
                stateList.add(nextState);
            }
        }
        return stateList;
    }
}
