package yukicoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/12.
 */
public class P196 {

    static final long MOD = 1_000_000_007;
    static long __cur = 0;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        graph = buildGraph(in, n, n-1);


        children = new int[n];
        dfs0(0, -1);

        long[] ans = dfs(0, -1);

        out.println(ans[k]);
        out.flush();
    }

    static int dfs0(int now, int parent) {
        children[now] = 1;
        for (int to : graph[now]) {
            if (to != parent) {
                children[now] += dfs0(to, now);
            }
        }
        return children[now];
    }

    static long[] dfs(int now, int parent) {
        int nc = children[now];
        long[][] ret = new long[2][nc+1];

        ret[0][0] = 1;

        int idx = 0;
        for (int to : graph[now]) {
            if (to != parent) {
                int f = idx % 2;
                int t = 1 - f;
                Arrays.fill(ret[t], 0);
                idx++;
                long[] tbl = dfs(to, now);
                for (int j = 0 ; j < nc ; j++) {
                    if (ret[f][j] != 0) {
                        for (int i = 0 ; i < tbl.length ; i++) {
                            if (i+j >= nc) {
                                break;
                            }
                            if (tbl[i] != 0) {
                                ret[t][i+j] += (ret[f][j] * tbl[i]) % MOD;
                                if (ret[t][i+j] >= MOD) {
                                    ret[t][i+j] -= MOD;
                                }
                            }
                        }
                    }
                }
            }
        }

        ret[idx%2][nc] = 1;
        return ret[idx%2];
    }

    static int[][] graph;
    static int[] children;

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
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

}
