package controller;

import model.Game;
import model.State;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private State state;

    public GameController(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isWin(int player, State state1) {
        for (int i = 0; i < 4; i++) {
            if (state1.getPiece(player, i) < 56) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinal(State state1) {
        return isWin(0, state1) || isWin(1, state1);
    }

    public int utility(State state) {
        return isWin(0, state) ? 1 : -1;
    }

    public boolean isWall(int player, State state1, int index) {
        boolean wall = false;
        for (int i = 0; i < 4; i++) {
            if (state1.getPos(player, i) == index) {
                if (wall) {
                    return true;
                }
                wall = true;
            }
        }
        return false;
    }

    public boolean inRoad(int player, int piece, int steps, State state1) {
        return state1.getPiece(player, piece) + steps >= 57;
    }

    public boolean checkHouse(int player, State state1) {
        boolean hasPieceInHouse = false;
        for (int i = 0; i < 4; i++) {
            if (state1.getPiece(player, i) == -1) {
                hasPieceInHouse = true;
                break;
            }
        }
        return hasPieceInHouse;
    }

    public boolean canMove(int player, int piece, int steps, State state1) {
        for (int i = piece + 1; i <= piece + steps; i++) {
            if (isWall(1 - player, state1, i)) {
                return false;
            }
        }

        return inRoad(player, piece, steps, state1);
    }

    public void move(int player, int piece, int steps, State state1) {
        int pos = state1.getPos(player, piece);
        int goal = (pos + steps) % 52;

        state1.setPiece(player, piece, state1.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            state1.exit(1 - player, goal);
        }
    }

    public void enterToGame(int player, State state1) {
        state1.entry(player);
    }

    public State generateNextState(int player, int piece, int steps, State state1) {
        State currentState = state1.deepCopy();
        int pos = currentState.getPos(player, piece);
        int goal = (pos + steps) % 52;

        currentState.setPiece(player, piece, currentState.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            currentState.exit(1 - player, goal);
        }

        return currentState;
    }

    public State enterToGame_NextState(int player, State state1) {
        State newState = state1.deepCopy();

        newState.entry(player);

        return newState;
    }

    public List<State> nextStates(int player, State state1, int steps) {
        List<State> stateList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (canMove(player, i, steps, state1)) {
                State nextState = generateNextState(player, i, steps, state1);
                stateList.add(nextState);
            }
        }

        if (steps == 6) {
            if (checkHouse(player, state1)) {
                State nextState = enterToGame_NextState(player, state1);
                stateList.add(nextState);
            }
        }
        return stateList;
    }
}
