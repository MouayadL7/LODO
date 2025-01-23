package app;

import algorithm.Bot;
import algorithm.User;
import controller.GameController;
import model.Game;
import model.State;
import view.GameView;

import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Game.generate();
        State state = new State();

        User user = new User(state);
        Bot bot = new Bot(state);

        GameView gameView = new GameView(user, bot, state);
        gameView.play();


    }
}