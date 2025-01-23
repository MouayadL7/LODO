package algorithm;

import controller.GameController;
import controller.Pair;
import model.Game;
import model.State;

import java.util.List;
import java.util.Vector;

public class Bot {
    State state;

    public Bot(State state) {
        this.state = state;
    }

    public void move(int steps) {
        Vector<Integer> v = new Vector<>();
        v.add(steps);

        Pair<State, Float> p = Minimax(1, state, v, 0);
        state.set(p.getFirst());
    }

    public Pair<State, Float> Minimax(int player, State state, Vector<Integer> steps, int depth) {
        if (GameController.isFinal(state)) {
            return new Pair<>(state, GameController.utility(state));
        }

        if (depth == 2) {
            return new Pair<>(state, state.heuristic());
        }

        List<State> stateList = state.nextStates(1, steps);

        float value = (player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        State tempState = state.deepCopy();
        for (State state1 : stateList) {
            if (state.eq(state1)) {
                continue;
            }
            float ev = GameController.isFinal(state1) ? GameController.utility(state1) : expectedValue(state1, player, depth);
            if (player == 1 && ev > value) {
                value = ev;
                tempState = state1;
            }
            else if (player == 0 && ev < value) {
                value = ev;
                tempState = state1;
            }
        }

        return new Pair<>(tempState, value);
    }

    public float expectedValue(State state, int player, int depth) {
        final float[] ev = {0.0f};
        Game.map.forEach((key, value) -> {
            //System.out.println(value);
            ev[0] += value * Minimax(1 - player, state, key, depth + 1).getSecond();
        });

        return ev[0];
    }
}
