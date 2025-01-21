package view;

import algorithm.Bot;
import algorithm.User;
import controller.GameController;

import java.util.List;
import java.util.Scanner;

public class GameView {
    User user;
    Bot bot;
    GameController gameController;

    public GameView(User user, Bot bot, GameController gameController) {
        this.user = user;
        this.bot = bot;
        this.gameController = gameController;
    }

    public void displayGrid() {
        //
    }

    public boolean showChoiceOptions(int steps) {
        if (gameController.checkHouse(0, gameController.getState())) {
            return true;
        }
        else {
            showPieceOptions(steps);
            return false;
        }
    }

    public void showPieceOptions(int steps) {
        List<Integer> pieces = user.canPiece(steps);
        for (int i = 0; i < pieces.size(); i++) {
            //
        }
    }

    public void play() {
        int player = 0;
        Scanner scanner  = new Scanner(System.in);
        while (!gameController.isFinal(gameController.getState())) {
            displayGrid();
            if (player == 0) {
                // throw dice
                int steps = user.throwDice();
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
                int steps = bot.throwDice();
                bot.move(steps);
            }
        }
    }
}
