package topcoder.srm5xx.srm569;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/04.
 */
public class TheJediTest {
    public int minimumSupervisors(int[] students, int K) {
        int n = students.length;
        int best = Integer.MAX_VALUE;
        for (int direction = 0; direction < (1<<(n-1)); direction++) {
            int[] a = students.clone();
            int carried = 0;
            for (int i = 0; i < n-1 ; i++) {
                // between i and i+1
                if ((direction & (1<<i)) >= 1) {
                    // right
                    int move = Math.min(a[i] - carried, a[i] % K);
                    a[i] -= move;
                    a[i+1] += move;
                    carried = move;
                } else {
                    // left
                    int move = Math.min(a[i+1], (K - a[i] % K) % K);
                    a[i+1] -= move;
                    a[i] += move;
                    carried = 0;
                }

            }
            int needJedi = 0;
            for (int i = 0; i < n ; i++) {
                needJedi += (a[i] + K - 1) / K;
            }
            best = Math.min(best, needJedi);
        }
        return best;
    }

    public static void main(String[] args) {
        TheJediTest jediTest = new TheJediTest();
        debug(jediTest.minimumSupervisors(
                new int[]{29541663, 85732198, 69829763, 77760462, 32153432, 79240058, 13641353, 50236888, 83780217, 82884996, 5909451, 68242201, 64320036, 82420030, 16758585, 12089161, 92006984, 90761986},
                67854681));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
