package topcoder.srm6xx.srm671;

import java.util.*;

/**
 * Created by hama_du on 15/10/15.
 */
public class BearDarts {

    static class BIT2 {
        long N;
        long M;
        long[][] data;
        BIT2(int n, int m) {
            N = n;
            M = m;
            data = new long[n+1][m+1];
        }

        long sum(int i, int j) {
            long s = 0;
            while (i > 0) {
                int ij = j;
                while (ij > 0) {
                    s += data[i][ij];
                    ij -= ij & (-ij);
                }
                i -= i & (-i);
            }
            return s;
        }

        long range(int x0, int x1, int y0, int y1) {
            if (x0 > x1 || y0 > y1) {
                return 0;
            }
            return sum(x1, y1) - sum(x0-1, y1) - sum(x1, y0-1) + sum(x0-1, y0-1);
        }

        void add(int i, int j, long x) {
            while (i <= N) {
                int ij = j;
                while (ij <= M) {
                    data[i][ij] += x;
                    ij += ij & (-ij);
                }
                i += i & (-i);
            }
        }
    }

    public long count(int[] w) {
        int n = w.length;
        Map<Long,List<int[]>> mulMap = new HashMap<>();
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n; j++) {
                long m = (1L * w[i]) * w[j];
                if (!mulMap.containsKey(m)) {
                    mulMap.put(m, new ArrayList<>());
                }
                mulMap.get(m).add(new int[]{i+1, j+1});
            }
        }


        BIT2 bit2 = new BIT2(n+2,n+2);

        long sum = 0;
        for (long mul : mulMap.keySet()) {
            List<int[]> list = mulMap.get(mul);
            int ln = list.size();
            for (int i = ln-1 ; i >= 0 ; i--) {
                int[] ac = list.get(i);
                sum += bit2.range(ac[0]+1, ac[1]-1, ac[1]+1, n);
                bit2.add(ac[0], ac[1], 1);
            }
            for (int i = ln-1 ; i >= 0 ; i--) {
                int[] ac = list.get(i);
                bit2.add(ac[0], ac[1], -1);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] lg = new int[1000];
        for (int i = 0; i < 1000 ; i++) {
            lg[i] = i+114514;
        }
        for (int i = 0; i < 1000 ; i++) {
            if (i >= 1) {
                System.out.print(',');
            }
            System.out.print(lg[i]);
        }
        System.out.println();

        BearDarts darts = new BearDarts();
        debug(darts.count(lg));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
