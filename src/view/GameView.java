package view;

import algorithm.Bot;
import algorithm.User;
import controller.GameController;
import model.Game;
import model.State;

import java.util.List;
import java.util.Scanner;

public class GameView {
    User user;
    Bot bot;
    State state;

    public GameView(User user, Bot bot, State state) {
        this.user = user;
        this.bot = bot;
        this.state = state;
    }

    public int throwDice() {
        return Game.throwDice();
    }

    public void displayGrid() {
        //
    }

    public boolean showChoiceOptions(int steps) {
        if (GameController.checkHouse(0, state)) {
            return true;
        }
        else {
            showPieceOptions(steps);
            return false;
        }
    }

    public void showPieceOptions(int steps) {
        List<Integer> pieces = user.pieceCanMove(steps);
        for (int i = 0; i < pieces.size(); i++) {
            //
        }
    }

    public void play() {
        int player = 0;
        Scanner scanner  = new Scanner(System.in);
        while (!GameController.isFinal(state)) {
            displayGrid();
            if (player == 0) {
                // throw dice
                int steps = throwDice();
                System.out.println("steps = " + steps);

                // show options, enter choice
                if (showChoiceOptions(steps)) {
                    int choice = scanner.nextInt();

                    if (choice == 0) {
                        user.enterPiece();
                    }
                    else {
                        showPieceOptions(steps);

                        int piece = scanner.nextInt();
                        user.movePiece(piece, steps);
                    }
                }
                else {
                    int piece = scanner.nextInt();
                    user.movePiece(piece, steps);
                }
            }
            else {
                int steps = throwDice();
                bot.move(steps);
            }
        }
    }
}
