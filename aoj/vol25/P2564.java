package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 15/07/19.
 */
public class P2564 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][][] gr = buildDirectedGraph(in, n, m);
        int[][] graph = gr[0];
        int[][] rgraph = gr[1];

        int[] indeg = new int[n];
        int[] oudeg = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                indeg[to]++;
                oudeg[i]++;
            }
        }

        boolean[][] done = new boolean[n][n];
        int cnt = 0;
        while (true) {
            int min = Integer.MAX_VALUE;
            int bestV = -1;
            for (int i = 0; i < n ; i++) {
                int sum = indeg[i] + oudeg[i];
                if (sum >= 1 && min > sum) {
                    min = sum;
                    bestV = i;
                }
            }
            if (bestV == -1) {
                break;
            }
            for (int to : graph[bestV]) {
                if (done[bestV][to]) {
                    continue;
                }
                done[bestV][to] = true;
                cnt++;
                oudeg[bestV]--;
                indeg[to]--;
            }
            for (int fr : rgraph[bestV]) {
                if (done[fr][bestV]) {
                    continue;
                }
                done[fr][bestV] = true;
                cnt++;
                oudeg[fr]--;
                indeg[bestV]--;
            }
            cnt--;
        }
        out.println(cnt);
        out.flush();
    }
    static int[][][] buildDirectedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[][] rgraph = new int[n][];
        int[] deg = new int[n];
        int[] rdeg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            rdeg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
            rgraph[i] = new int[rdeg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            rgraph[b][--rdeg[b]] = a;
        }
        return new int[][][]{graph, rgraph};
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
