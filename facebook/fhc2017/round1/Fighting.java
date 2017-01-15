package facebook.fhc2017.round1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Fighting {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int r = in.nextInt();
            int[][] zombies = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    zombies[i][j] = in.nextInt();
                }
            }
            out.println(String.format("Case #%d: %d", c, solve(zombies, r)));
        }
        out.flush();
    }

    private static long solve(int[][] zombines, int r) {
        int n = zombines.length;

        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();
        for (int i = 0; i < zombines.length ; i++) {
            xs.add(zombines[i][0]);
            ys.add(zombines[i][1]);
        }
        int[] x = new int[xs.size()];
        int[] y = new int[ys.size()];
        int xn = 0;
        int yn = 0;
        for (int xi : xs) {
            x[xn++] = xi;
        }
        for (int yi : ys) {
            y[yn++] = yi;
        }
        int max = 0;
        long[] ptn = new long[xn*yn];
        for (int i = 0; i < xn ; i++) {
            for (int j = 0; j < yn ; j++) {
                int X = x[i];
                int Y = y[j];
                for (int k = 0; k < zombines.length; k++) {
                    if (X <= zombines[k][0] && zombines[k][0] <= X+r && Y <= zombines[k][1] && zombines[k][1] <= Y+r) {
                        ptn[i*yn+j] |= 1L<<k;
                    }
                }
            }
        }

        int nn = ptn.length;
        for (int i = 0; i < nn ; i++) {
            max = Math.max(max, Long.bitCount(ptn[i]));
        }
        for (int i = 0 ; i < nn ; i++) {
            for (int j = i+1 ; j < nn ; j++) {
                int x1 = x[i/yn];
                int y1 = y[i%yn];
                int x2 = x[j/yn];
                int y2 = y[j%yn];
                long canSlay = ptn[i] | ptn[j];
                max = Math.max(max, Long.bitCount(canSlay));
            }
        }
        return max;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
