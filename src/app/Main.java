package app;

import algorithm.Bot;
import algorithm.User;
import controller.GameController;
import model.State;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        State state = new State();
        User user = new User(state);
        Bot bot = new Bot(state);
        GameView gameView = new GameView(user, bot);

    }
}