package controller;

import model.Game;
import model.State;

public class GameController {

    static public boolean isWin(int player, State state) {
        for (int i = 0; i < 4; i++) {
            if (state.getPiece(player, i) < 56) {
                return false;
            }
        }
        return true;
    }

    static public boolean isFinal(State state) {
        return isWin(0, state) || isWin(1, state);
    }

    static public float utility(State state) {
        return isWin(1, state) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    }

    static public boolean isWall(int player, State state, int index) {
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

    static public boolean inRoad(int player, int piece, int steps, State state) {
        return state.getPiece(player, piece) + steps < 57;
    }

    static public boolean checkHouse(int player, State state) {
        boolean hasPieceInHouse = false;
        for (int i = 0; i < 4; i++) {
            if (state.getPiece(player, i) == -1) {
                hasPieceInHouse = true;
                break;
            }
        }
        return hasPieceInHouse;
    }

    static public boolean canMove(int player, int piece, int steps, State state) {
        //System.out.println(player + " " + piece + " " + steps + " " + state.getPiece(player, piece));
        if (state.getPiece(player, piece) == -1) {
            return false;
        }

        for (int i = piece + 1; i <= piece + steps; i++) {
            if (isWall(1 - player, state, i)) {
                //System.out.println("Yes");
                return false;
            }
        }

        return inRoad(player, piece, steps, state);
    }

    static public void move(int player, int piece, int steps, State state) {
        int pos = state.getPos(player, piece);
        int goal = (pos + steps) % 52;

        state.setPiece(player, piece, state.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            state.exit(1 - player, goal);
        }
    }

    static public void enterToGame(int player, State state) {
        state.entry(player);
    }

}
