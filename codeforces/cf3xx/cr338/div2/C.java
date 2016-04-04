package codeforces.cf3xx.cr338.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/09.
 */

public class C {
    private static final int INF = 1141514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String line = in.nextToken();
        char[] material = line.toCharArray();
        char[] materialRev = new StringBuilder(line).reverse().toString().toCharArray();
        char[] want = in.nextToken().toCharArray();

        int[][] matchL = buildMatchingLength(want, material);
        int[][] matchR = buildMatchingLength(want, materialRev);


        int n = want.length;
        int m = material.length;
        int[] dp = new int[n+1];
        int[][] bestRange = new int[n+1][2];
        Arrays.fill(dp, INF);

        dp[0] = 0;
        for (int i = 0; i < n ; i++) {
            int base = dp[i];
            if (base == INF) {
                continue;
            }

            // forward
            for (int k = 0; k < matchL[i][1] ; k++) {
                int ti = i + k + 1;
                if (ti <= n && base+1 < dp[ti]) {
                    dp[ti] = base+1;
                    bestRange[ti] = new int[]{matchL[i][0]+1, matchL[i][0]+k+1};
                }
            }

            // reverse
            for (int k = 0; k < matchR[i][1] ; k++) {
                int ti = i + k + 1;
                if (ti <= n && base+1 < dp[ti]) {
                    dp[ti] = base+1;
                    bestRange[ti] = new int[]{m-matchR[i][0], m-(matchR[i][0]+k)};
                }
            }
        }

        if (dp[n] == INF) {
            out.println(-1);
        } else {
            List<int[]> r = new ArrayList<>();
            int nn = n;
            while (nn >= 1) {
                int[] range = bestRange[nn];
                r.add(range);
                nn -= Math.abs(range[0] - range[1]) + 1;
            }
            Collections.reverse(r);

            out.println(r.size());
            for (int[] rr : r) {
                out.println(rr[0] + " " + rr[1]);
            }
        }
        out.flush();
    }

    private static int[][] buildMatchingLength(char[] want, char[] material) {
        int n = want.length;
        int m = material.length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                dfs(i, j, want, material, dp);
            }
        }

        int[][] matching = new int[n][2];
        for (int i = 0; i < n ; i++) {
            int max = -1;
            int maxPos = -1;
            for (int j = 0; j < m ; j++) {
                if (max < dp[i][j]) {
                    max = dp[i][j];
                    maxPos = j;
                }
            }
            matching[i][0] = maxPos;
            matching[i][1] = max;
        }
        return matching;
    }

    private static int dfs(int i, int j, char[] w, char[] m, int[][] memo) {
        if (i >= memo.length || j >= memo[0].length) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        memo[i][j] = w[i] == m[j] ? dfs(i+1, j+1, w, m, memo) + 1 : 0;
        return memo[i][j];
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
                res += c-'0';
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
                res += c-'0';
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
