package topcoder.srm5xx.srm567;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/09/03.
 */
public class TheSquareRootDilemma {
    public int countPairs(int N, int M) {
        int total = 0;
        Set<Long> done = new HashSet<>();
        long[] yaku = new long[2500];
        for (long l = 1 ; l <= 77777 ; l++) {
            int yi = 0;
            for (int x = 1 ; x * x <= l ; x++) {
                if (l % x == 0) {
                    yaku[yi++] = x;
                    yaku[yi++] = l/x;
                }
            }
            long L = l * l;
            done.clear();
            for (int i = 0; i < yi ; i++) {
                for (int j = i ; j < yi; j++) {
                    long A = yaku[i] * yaku[j];
                    long B = L / A;
                    if (!done.contains(A) && 1 <= A && A <= N && 1 <= B && B <= M) {
                        total++;
                        done.add(A);
                    }
                }
            }
        }
        return total;
    }
}
