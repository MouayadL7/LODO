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
        System.out.println("Heuristic = " + state.heuristic());
        for (int i = 0; i < 4; i++) {
            System.out.println(state.getPiece(0, i) + " " + state.getPiece(1, i));
        }
    }

    public int showChoiceOptions(int steps) {
        if (steps == 6 && GameController.checkHouse(0, state)) {
            System.out.println("Enter 0 for enter new piece to game");
            System.out.println("Enter 1 for select piece to move it");
            return 0;
        }
        else if (!user.pieceCanMove(steps).isEmpty()) {
            showPieceOptions(steps);
            return 1;
        }
        else {
            return 2;
        }
    }

    public void showPieceOptions(int steps) {
        List<Integer> pieces = user.pieceCanMove(steps);
        for (int i = 0; i < pieces.size(); i++) {
            System.out.println("Enter " + pieces.get(i));
        }
    }

    public void play() {
        int player = 0;
        Scanner scanner = new Scanner(System.in);
        while (!GameController.isFinal(state)) {
            System.out.println("Player = " + player);
            displayGrid();
            // throw dice
            int steps = throwDice();
            System.out.println("steps = " + steps);
            if (player == 0) {
                // show options, enter choice
                int counter = 0;
                int x = showChoiceOptions(steps);
                if (x == 0) {
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
                else if (x == 1) {
                    int piece = scanner.nextInt();
                    user.movePiece(piece, steps);
                }
            }
            else {
                bot.move(steps);
            }

            player = 1 - player;
        }
    }
}
