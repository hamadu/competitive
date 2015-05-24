package codeforces.cr277.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/24.
 */
public class D {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int d = in.nextInt();
        int n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        graph = buildGraph(in, n, n-1);
        for (int min = 1 ; min <= 2000 ; min++) {
            dfs(0, -1, min, min+d);
        }
        out.println(ans);
        out.flush();
    }

    static int[] a;

    static int[][] graph;

    static long ans;

    static long[] dfs(int now, int par, int min, int max) {
        long ret = 1;
        long retNonMin = 1;
        boolean isLeaf = true;
        for (int to : graph[now]) {
            if (to != par) {
                isLeaf = false;
                long[] f = dfs(to, now, min, max);
                if (min <= a[to] && a[to] <= max) {
                    long ptn = (f[0] + f[1]) % MOD;
                    ret *= (ptn+1) % MOD;
                    ret %= MOD;

                    retNonMin *= (f[1] + 1) % MOD;
                    retNonMin %= MOD;
                }
            }
        }

        long retMin = 1;
        if (isLeaf) {
            if (a[now] != min) {
                retMin = 0;
                retNonMin = 1;
            } else {
                retMin = 1;
                retNonMin = 0;
            }
        } else {
            if (a[now] != min) {
                retMin = (ret + MOD - retNonMin) % MOD;
            } else {
                retMin = ret;
                retNonMin = 0;
            }
        }
        if (min <= a[now] && a[now] <= max) {
            ans += retMin;
            ans %= MOD;
        }
        return new long[] {retMin, retNonMin};
    }


    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
