package app;

import algorithm.Bot;
import algorithm.User;
import controller.GameController;
import model.State;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        State state = new State();
        GameController gameController = new GameController(state);
        User user = new User(gameController);
        Bot bot = new Bot(gameController);
        GameView gameView = new GameView(user, bot, gameController);

    }
}