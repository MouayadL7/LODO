package algorithm;

import controller.GameController;
import controller.Pair;
import model.Game;
import model.State;

import java.util.List;
import java.util.Vector;

public class Bot {
    GameController gameController;

    public Bot(GameController gameController) {
        this.gameController = gameController;
    }

    public int throwDice() {
        return Game.throwDice();
    }

    public void move(int steps) {
        Vector<Integer> v = new Vector<>();
        v.add(steps);

        Pair<State, Float> p = Minimax(1, gameController.getState(), v);
        gameController.setState(p.getFirst());
    }

    public Pair<State, Float> Minimax(int player, State state, Vector<Integer> steps) {
        float totalValue = 0.0f;
        for (int step: steps) {
            List<State> stateList = gameController.nextStates(1, state, step);

            float value = (player == 1) ? -2 : 2;
            State tempState = state.deepCopy();
            for (State state1 : stateList) {
                float ev = gameController.isFinal(state1) ? gameController.utility(state1) : expectedValue(state1, player);
                if (player == 1 && ev > value) {
                    value = ev;
                    tempState = state1;
                }
                else if (player == 0 && ev < value) {
                    value = ev;
                    tempState = state1;
                }
            }

            state = tempState;
            totalValue += value;
        }

        return new Pair<>(state, totalValue);
    }

    public float expectedValue(State state, int player) {
        float ev = 0.0f;
        for (Vector<Integer> key : Game.map.keySet()) {
            float p = Game.map.get(key);
            ev += p * Minimax(1 - player, state, key).getSecond();
        }

        return ev;
    }
}
