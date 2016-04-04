package codeforces.cf1xx.cr167.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/09.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] which = new int[n];
        int[][] graph = buildGraph(in, n, m);
        for (int i = 0; i < n ; i++) {
            int left = 0;
            int right = 0;
            for (int hostile : graph[i]) {
                if (which[hostile] == 1) {
                    left++;
                } else if (which[hostile] == -1) {
                    right++;
                }
            }
            which[i] = (left <= right) ? 1 : -1;
        }

        int[] que = new int[1000000];
        int qh = 0;
        int qt = 0;
        int[] enemyCount = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int hostile : graph[i]) {
                if (which[hostile] == which[i]) {
                    enemyCount[i]++;
                }
            }
            if (enemyCount[i] >= 2) {
                que[qh++] = i;
            }
        }
        while (qt < qh) {
            int now = que[qt++];
            if (enemyCount[now] <= 1) {
                continue;
            }
            which[now] *= -1;
            enemyCount[now] = graph[now].length - enemyCount[now];
            for (int hostile : graph[now]) {
                if (which[hostile] == which[now]) {
                    enemyCount[hostile]++;
                    if (enemyCount[hostile] >= 2) {
                        que[qh++] = hostile;
                    }
                } else {
                    enemyCount[hostile]--;
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            out.print(which[i] == 1 ? '0' : '1');
        }
        out.println();
        out.flush();
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
