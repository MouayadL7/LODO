package model;

import java.util.Random;

public class Game {
    static public int beginning(int player) {
        return (12 * player) + player + 1;
    }

    static public boolean isSave(int index) {
        for (int i = 0; i < 4; i++) {
            if (index == beginning(i) || index == beginning(i) - 5) {
                return true;
            }
        }
        return false;
    }

    static public int throwDice() {
        Random random = new Random();

        return random.nextInt(6) + 1;
    }
}
