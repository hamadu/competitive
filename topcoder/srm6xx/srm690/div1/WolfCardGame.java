package topcoder.srm6xx.srm690.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/09.
 */
public class WolfCardGame {

    public int[] createAnswer(int N, int K) {
        for (int m : new int[]{2, 3, 5, 7}) {
            if (N % m != 0) {
                return generate(m, N, K);
            }
        }
        return new int[]{};
    }

    private int[] generate(int m, int N, int K) {
        int[] ni = new int[K];
        for (int i = 0; i < K ; i++) {
            ni[i] = (i+1)*m;
        }
        if (ni[K-1] > 100) {
            for (int foe = 1 ; foe <= 100 ; foe++) {
                ni[K-1] = foe;
                if (!canMake(ni, 0, 0, N)) {
                    break;
                }
            }
        }
        return ni;
    }

    private boolean canMake(int[] ni, int idx, int now, int N) {
        if (idx == ni.length) {
            return now == N;
        }
        boolean ok = false;
        ok |= canMake(ni, idx+1, now+ni[idx], N);
        ok |= canMake(ni, idx+1, now-ni[idx], N);
        ok |= canMake(ni, idx+1, now, N);
        return ok;
    }

    public static void main(String[] args) {
        WolfCardGame game = new WolfCardGame();
        debug(game.createAnswer(1, 9));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
