package model;

public class State {
    private int[][] pieces;

    public State() {
        this.pieces = new int[][] {
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}
        };
    }

    public int[][] getPieces() {
        return pieces;
    }

    public int getPiece(int player, int index) {
        return pieces[player][index];
    }

    public void setPiece(int player, int cell, int value) {
        pieces[player][cell] = value;
    }

    public int getPos(int player, int piece) {
        return Game.beginning(player) + getPiece(player, piece);
    }

    public State deepCopy() {
        State newState = new State();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                newState.setPiece(i, j, getPiece(i, j));
            }
        }

        return newState;
    }

    public void exit(int player, int index) {
        for (int i = 0; i < 4; i++) {
            if (getPos(player, i) == index) {
                setPiece(player, i, -1);
            }
        }
    }

    public void entry(int player) {
        for (int i = 0; i < 4; i++) {
            if (getPos(player, i) == -1) {
                //
            }
        }
    }
}
