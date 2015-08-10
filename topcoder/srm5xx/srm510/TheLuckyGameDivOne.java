package topcoder.srm5xx.srm510;

import java.util.*;

/**
 * Created by hama_du on 15/08/07.
 */
public class TheLuckyGameDivOne {
    public int find(long a, long b, long jLen, long bLen) {
        numbers = new ArrayList<>();
        dfs(0, a, b);
        Collections.sort(numbers);

        int sz = numbers.size();
        long johnBest = 0;
        Set<Long> candidates = new HashSet<>();
        candidates.add(a);
        for (int i = 0; i < sz ; i++) {
            long x = numbers.get(i);
            candidates.add(x);
            candidates.add(x-1);
            candidates.add(x+1);
            candidates.add(x-(bLen-1));
        }
        for (long c : candidates) {
            long jfr = c;
            long jto = c + jLen - 1;
            if (a <= jfr && jto <= b) {
            } else {
                continue;
            }

            long brusBest = count(jfr, jfr + bLen - 1);
            for (int i = 0; i < sz ; i++) {
                long nu = numbers.get(i) + 1;
                if (jfr <= nu && nu + bLen - 1 <= jto) {
                    brusBest = Math.min(brusBest, count(nu, nu + bLen - 1));
                }
            }
            johnBest = Math.max(johnBest, brusBest);
        }
        return (int)johnBest;
    }

    private int count(long fr, long to) {
        return count(to) - count(fr-1);
    }

    private int count(long to) {
        int idx = Collections.binarySearch(numbers, to);
        if (idx < 0) {
            idx = - idx - 1;
        } else {
            idx++;
        }
        return idx;
    }

    public List<Long> numbers;

    public void dfs(long now, long A, long B) {
        if (now > B) {
            return;
        }
        if (now >= A) {
            numbers.add(now);
        }
        dfs(now*10+4, A, B);
        dfs(now*10+7, A, B);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        TheLuckyGameDivOne game = new TheLuckyGameDivOne();
        debug(game.find(1, 1000000, 6, 5));
    }
}
