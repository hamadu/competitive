package topcoder.srm6xx.srm689.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/09.
 */
public class MultiplicationTable3 {
    public int[] construct(int x) {
        if (x == 1) {
            return new int[]{0};
        }

        Arrays.fill(best, 21);
        best[0] = 0;
        dfs(1, 0, new int[20]);

        for (int left = 1 ; left <= x-1 ; left++) {
            int right = (x-1) - left;
            if (best[left] + best[right] <= 19) {
                int l = best[left];
                int r = best[right];
                int[][] map = new int[l+r+1][l+r+1];
                for (int i = 0 ; i < l ; i++) {
                    for (int j = 0; j < l ; j++) {
                        map[i+1][j+1] = bestMap[left][Math.max(i, j)]+1;
                    }
                }
                for (int i = 0 ; i < r ; i++) {
                    for (int j = 0; j < r ; j++) {
                        map[l+i+1][l+j+1] = bestMap[right][Math.max(i, j)]+1+l;
                    }
                }
                for (int i = 0; i < map.length ; i++) {
                    map[0][i] = map[i][0] = (i+1)%map.length;
                }
                return decompose(map);
            }
        }
        return null;
    }

    private int[] decompose(int[][] ret) {
        int n = ret.length;
        int[] r = new int[n*n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                r[i*n+j] = ret[i][j];
            }
        }
        return r;
    }

    static int[] best = new int[1001];
    static int[][] bestMap = new int[1001][];

    private static void dfs(int ptn, int len, int[] data) {
        if (ptn > 1001) {
            return;
        }
        if (ptn >= 2) {
            if (best[ptn-1] > len) {
                best[ptn-1] = len;
                bestMap[ptn-1] = Arrays.copyOf(data, len);
            }
        }
        for (int k = 1 ; len+k <= 19 ; k++) {
            int[] tdata = data.clone();
            for (int i = len ; i < len+k ; i++) {
                tdata[i] = len+k-1;
            }
            int mul = (1<<(k-1))+1;
            dfs(ptn * mul, len+k, tdata);
        }
    }

    public static void main(String[] args) {
        MultiplicationTable3 tbl = new MultiplicationTable3();
        debug(tbl.construct(2));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
