package topcoder.srm6xx.srm685.div1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 2016/05/11.
 */
public class MultiplicationTable2 {
    public int minimalGoodSet(int[] table) {
        int[][] a = decode(table);
        int n = a.length;
        int min = n;
        for (int i = 0 ; i < n ; i++) {
            Set<Integer> set = new HashSet<>();
            set.add(i);
            boolean upd = true;
            while (upd) {
                upd = false;
                for (int u = 0; u < n ; u++) {
                    for (int v = 0; v < n ; v++) {
                        if (set.contains(u) && set.contains(v) && !set.contains(a[u][v])) {
                            upd = true;
                            set.add(a[u][v]);
                        }
                    }
                }
            }
            min = Math.min(min, set.size());
        }
        return min;
    }

    private int[][] decode(int[] table) {
        int n2 = table.length;
        for (int n = 1 ; n <= 50 ; n++) {
            if (n * n == n2) {
                int[][] ret = new int[n][n];
                for (int i = 0; i < n ; i++) {
                    for (int j = 0; j < n ; j++) {
                        ret[i][j] = table[i*n+j];
                    }
                }
                return ret;
            }
        }
        throw new RuntimeException("wrong number of elements");
    }

    public static void main(String[] args) {
        MultiplicationTable2 solution = new MultiplicationTable2();
        debug(solution.minimalGoodSet(
                new int[]{1,1,1,1}
        ));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
