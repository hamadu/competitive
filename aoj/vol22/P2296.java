package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/07/23.
 */
public class P2296 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int W = in.nextInt();
        int T = in.nextInt();

        Map<String,Integer> itemMap = new HashMap<String,Integer>();
        int[][] items = new int[m][2];
        for (int i = 0; i < m ; i++) {
            String name = in.nextToken();
            itemMap.put(name, i);
            for (int j = 0; j < 2 ; j++) {
                items[i][j] = in.nextInt();
            }
        }

        int[][][] cities = new int[n][][];
        int[][] pos = new int[n][2];
        for (int i = 0; i < n; i++) {
            int l = in.nextInt();
            for (int j = 0; j < 2 ; j++) {
                pos[i][j] = in.nextInt();
            }
            cities[i] = new int[l][2];
            for (int j = 0; j < l ; j++) {
                String str = in.nextToken();
                int id = itemMap.get(str);
                cities[i][j][0] = items[id][0];
                cities[i][j][1] = items[id][1] - in.nextInt();
            }
        }
        int[] moveCost = computeMoveCost(pos);
        long[][] knapsack = new long[1<<n][W+1];

        for (int p = 1; p < 1<<n ; p++) {
            for (int i = 0; i < n ; i++) {
                if ((p & (1 << i)) == 0) {
                    continue;
                }
                int tp = p - (1 << i);
                for (int j = 0; j <= W; j++) {
                    knapsack[p][j] = Math.max(knapsack[p][j], knapsack[tp][j]);
                }
            }
            for (int i = 0; i < n ; i++) {
                if ((p & (1 << i)) == 0) {
                    continue;
                }
                for (int j = 0; j <= W; j++) {
                    for (int k = 0; k < cities[i].length ; k++) {
                        int tj = j - cities[i][k][0];
                        if (tj >= 0) {
                            knapsack[p][j] = Math.max(knapsack[p][j], knapsack[p][tj] + cities[i][k][1]);
                        }
                    }
                }
            }
        }

        long[][] timeAndProfit = new long[1<<n][2];
        for (int i = 0; i < (1<<n) ; i++) {
            long max = 0;
            for (int w = 0; w <= W; w++) {
                max = Math.max(max, knapsack[i][w]);
            }
            timeAndProfit[i][0] = moveCost[i];
            timeAndProfit[i][1] = max;
        }

        long[] dp = new long[T+1];
        long max = 0;
        for (int t = 0; t <= T ; t++) {
            for (int i = 0; i < (1<<n) ; i++) {
                long lt = t - timeAndProfit[i][0];
                if (lt < 0) {
                    continue;
                }
                int it = (int)lt;
                dp[t] = Math.max(dp[t], dp[it] + timeAndProfit[i][1]);
            }
            max = Math.max(max, dp[t]);
        }
        out.println(dp[T]);
        out.flush();
    }

    static int[] computeMoveCost(int[][] pos) {
        int n = pos.length;
        int[] ord = new int[n];
        for (int i = 0; i < n ; i++) {
            ord[i] = i;
        }
        int[] best = new int[1<<n];
        Arrays.fill(best, Integer.MAX_VALUE);
        best[0] = 0;
        do {
            int ptn = 0;
            int nx = 0;
            int ny = 0;
            int cost = 0;
            for (int i = 0; i < n; i++) {
                ptn |= 1<<ord[i];
                int cx = pos[ord[i]][0];
                int cy = pos[ord[i]][1];
                cost += Math.abs(nx - cx) + Math.abs(ny - cy);
                int returnCost = cost + Math.abs(cx) + Math.abs(cy);
                best[ptn] = Math.min(best[ptn], returnCost);
                nx = cx;
                ny = cy;
            }
        } while (next_permutation(ord));
        return best;
    }

    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int next() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public char nextChar() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            if ('a' <= c && c <= 'z') {
                return (char) c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char) c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = next();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
