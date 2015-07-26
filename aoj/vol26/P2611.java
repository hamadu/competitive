package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/26.
 */
public class P2611 {
    
    static final long MOD = 1000000007;

    static final int MAX = 10;
    
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        merge = computeMergePtn(MAX);

        int n = in.nextInt();
        graph = buildDirectedGraph(in, n, n - 1);
        int[] indeg = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                indeg[to]++;
            }
        }
        int root = -1;
        for (int i = 0; i < n ; i++) {
            if (indeg[i] == 0) {
                root = i;
            }
        }


        long ans = 0;
        long[] ret = dfs(root);
        for (int i = 0; i < ret.length; i++) {
            ans += ret[i];
        }
        ans %= MOD;

        out.println(ans);
        out.flush();
    }

    static int[][] graph;

    static int[][][] merge;

    private static long[] dfs(int now) {
        int cn = graph[now].length;
        long[] ret = new long[MAX];
        if (cn == 0) {
            ret[1] = 1;
            return ret;
        }

        long[][] dp = new long[cn+1][MAX];
        dp[0][0] = 1;
        for (int idx = 0; idx < cn ; idx++) {
            long[] table = dfs(graph[now][idx]);
            for (int al = 0; al < MAX ; al++) {
                if (dp[idx][al] == 0) {
                    continue;
                }
                long base = dp[idx][al];
                for (int bl = 0 ; bl < MAX ; bl++) {
                    if (table[bl] == 0) {
                        continue;
                    }
                    for (int to = Math.max(al, bl); to < MAX ; to++) {
                        if (merge[to][al][bl] == 0) {
                           continue;
                        }
                        long add = (base * merge[to][al][bl]) % MOD;
                        add *= table[bl];
                        add %= MOD;
                        dp[idx+1][to] += add;
                        dp[idx+1][to] %= MOD;
                    }
                }
            }
        }
        for (int i = 0; i < MAX-1 ; i++) {
            ret[i+1] = dp[cn][i];
        }
        return ret;
    }

    static int[][] buildDirectedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            deg[a]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }

    static int[][][] computeMergePtn(int max) {
        int[][][] dp = new int[max][max][max];
        dp[0][0][0] = 1;
        for (int cnt = 0; cnt < max-1 ; cnt++) {
            for (int ai = 0; ai < max ; ai++) {
                for (int bi = 0; bi < max ; bi++) {
                    if (dp[cnt][ai][bi] == 0) {
                        continue;
                    }
                    int base = dp[cnt][ai][bi];
                    if (ai+1 < max) {
                        dp[cnt+1][ai+1][bi] += base;
                        dp[cnt+1][ai+1][bi] -= (dp[cnt+1][ai+1][bi] >= MOD) ? MOD : 0;
                    }
                    if (bi+1 < max) {
                        dp[cnt+1][ai][bi+1] += base;
                        dp[cnt+1][ai][bi+1] -= (dp[cnt+1][ai][bi+1] >= MOD) ? MOD : 0;
                    }
                    if (ai+1 < max && bi+1 < max) {
                        dp[cnt+1][ai+1][bi+1] += base;
                        dp[cnt+1][ai+1][bi+1] -= (dp[cnt+1][ai+1][bi+1] >= MOD) ? MOD : 0;
                    }
                }
            }
        }
        return dp;
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
