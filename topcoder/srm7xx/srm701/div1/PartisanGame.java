package topcoder.srm7xx.srm701.div1;

import java.io.*;
import java.util.*;

public class PartisanGame {
    public String getWinner(int n, int[] a, int[] b) {
        int MAX = 1000000;
        int[][] win = new int[2][MAX];

        int[][] moves = new int[][]{a, b};
        for (int i = 1 ; i < MAX ; i++) {
            for (int ab = 0 ; ab <= 1 ; ab++) {
                int tab = ab^1;
                boolean canMakeLose = false;
                for (int m : moves[ab]) {
                    if (i >= m) {
                        canMakeLose |= win[tab][i-m] == 0;
                    }
                }
                win[ab][i] = canMakeLose ? 1 : 0;
            }
        }
        if (n < MAX) {
            return win[0][n] == 1 ? "Alice" : "Bob";
        }

        int awins = 0;
        for (int i = 1 ; i < MAX ; i++) {
            awins += win[0][i];
        }

        boolean canWin = awins >= MAX - 10;
        for (int cur = 1 ; cur <= 14400 ; cur++) {
            boolean isOK = true;
            sch: for (int i = 0 ; i < cur; i++) {
                int zero = win[0][i];
                int one = win[1][i];
                for (int j = i ; j < MAX ; j += cur) {
                    if (win[0][j] != zero || win[1][j] != one) {
                        isOK = false;
                        break sch;
                    }
                }
            }
            if (isOK) {
                n %= cur;
                canWin = win[0][n] == 1;
                break;
            }
        }

        return canWin ? "Alice" : "Bob";
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
