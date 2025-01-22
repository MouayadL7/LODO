package model;

import controller.GameController;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        return (Game.beginning(player) + getPiece(player, piece)) % 52;
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
            if (getPiece(player, i) == -1) {
                setPiece(player, i, 0);
                break;
            }
        }
    }

    public State generateState(int player, int piece, int steps) {
        State currentState = deepCopy();
        int pos = currentState.getPos(player, piece);
        int goal = (pos + steps) % 52;

        currentState.setPiece(player, piece, currentState.getPiece(player, piece) + steps);

        if (!Game.isSave(goal)) {
            currentState.exit(1 - player, goal);
        }

        return currentState;
    }

    public List<State> generateNextStates(int player, int steps) {
        List<State> nextStates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (GameController.canMove(player, i, steps, this)) {
                State nextState = generateState(player, i, steps);
                nextStates.add(nextState);
            }
        }

        if (steps == 6) {
            if (GameController.checkHouse(player, this)) {
                State nextState = enterToGame_NextState(player);
                nextStates.add(nextState);
            }
        }

        return nextStates;
    }

    public State enterToGame_NextState(int player) {
        State newState = deepCopy();

        newState.entry(player);

        return newState;
    }

    public List<State> nextStates(int player, Vector<Integer> steps) {
        List<State> stateList = new ArrayList<>();
        State state = deepCopy();
        stateList.add(state);

        for (int i = 0; i < steps.size(); i++) {
            for (State state1 : stateList) {
                stateList = state1.generateNextStates(player, steps.get(i));
            }
        }

        return stateList;
    }

    public int stones(int player) {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (getPiece(player, i) == -1) {
                score -= 10;
            }
            else {
                score += getPiece(player, i);
            }
        }
        return score;
    }

    public int walls(int player, List<Integer>stones) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(60, 0);

        for (int i = 0; i < 4; i++) {
            int pos = getPos(player, i);
            if (map.containsKey(pos)) {
                map.put(pos, map.get(pos) + 1);
            }
            else {
                map.put(pos, 1);
            }
        }

        AtomicInteger score = new AtomicInteger();
        map.forEach((key, value) -> {
            if (value >= 2) {
                score.addAndGet((int) (stones.stream().filter(p -> p.compareTo(key) < 0).count() * 10));
            }
        });

        return score.get();
    }

    public int saveCells(int player) {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (Game.isSave(getPos(player, i)) || getPiece(player, i) > 51) {
                score += 10;
            }
        }

        return score;
    }

    public float killOpponent(int player) {
        float score = 0.0f;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int distance = getPos(1 - player, j) - getPos(player, i);
                if (Game.map2.containsKey(distance)) {
                    score += Game.map2.get(distance) * 10;
                }
            }
        }
        return score;
    }

    public float heuristic() {
        float score = 0;

        List<Integer> userStones = new ArrayList<>();
        List<Integer> botStones = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            userStones.add(getPiece(0, i));
            botStones.add(getPiece(1, i));
        }

        // Getting home
        score += Collections.frequency(botStones, 57) * 25;
        score -= Collections.frequency(userStones, 57) * 25;

        userStones.removeIf(piece -> piece == 57);
        botStones.removeIf(piece -> piece == 57);

        // Stones
        score += stones(1);
        score -= stones(0);

        // Walls
        score += walls(1, userStones);
        score -= walls(0, botStones);

        // Save cells
        score += saveCells(1);
        score -= saveCells(0);

        // kill opponent's stone
        score += killOpponent(1);
        score -= killOpponent(0);


    }
}
