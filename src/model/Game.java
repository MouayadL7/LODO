package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Game {
    static public Map<Vector<Integer>, Float> map = new HashMap<>();

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


    static public void generate() {
        Vector<Integer> v = new Vector<>();
        for (int i = 1; i < 6; i++) {
            v.add(i);
            map.put(v, 1 / 6.0f);
            for (int j = 1; j < 6; j++) {
                v.add(j);
                map.put(v, 1 / 36.0f);
                for (int k = 1; k <= 6; k++) {
                    v.add(k);
                    map.put(v, 1 / 216.0f);
                    v.removeLast();
                }
                v.removeLast();
            }
            v.removeFirst();
        }
    }
}
