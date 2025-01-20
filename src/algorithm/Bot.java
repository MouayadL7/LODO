package algorithm;

import controller.GameController;
import model.State;

import java.util.List;

public class Bot {
    GameController gameController;

    public Bot(GameController gameController) {
        this.gameController = gameController;
    }

    public float MiniMax(int player, State state, int steps) {
        List<State> nextStates = gameController.nextStates(1, steps);

        float value = player == 0 ? 1 : -1;
        for (State state1 : nextStates)
    }
}
