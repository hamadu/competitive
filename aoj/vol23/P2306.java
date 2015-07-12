package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/12.
 */
public class P2306 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] f = new int[n][n];
        lw = new long[n];
        up = new long[n];
        graph = f;

        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int w = in.nextInt();
            if (b >= 50) {
                up[a] |= 1L<<(b-50);
            } else {
                lw[a] |= 1L<<b;
            }
            if (a >= 50) {
                up[b] |= 1L<<(a-50);
            } else {
                lw[b] |= 1L<<a;
            }
            f[a][b] = f[b][a] = w;
        }

        dfs(0, 0, 0);

        out.println(best);
        out.flush();
    }

    static int[][] graph;
    static long[] lw;
    static long[] up;
    static long cnt = 0;

    static int[] member = new int[110];
    static int mi = 0;
    static int best = 0;

    static void dfs(int i, long chosenLw, long chosenUp) {
        if (i >= graph.length) {
            if (mi >= 2) {
                int score = 0;
                for (int a = 0; a < mi; a++) {
                    int min = Integer.MAX_VALUE;
                    for (int b = 0; b < mi; b++) {
                        if (a == b) {
                            continue;
                        }
                        min = Math.min(min, graph[member[a]][member[b]]);
                    }
                    score += min;
                }
                best = Math.max(best, score);
            }
            return;
        }
        cnt++;
        long l = lw[i];
        long u = up[i];
        if ((chosenLw & l) == chosenLw && (chosenUp & u) == chosenUp) {
            long tlw = chosenLw;
            long tup = chosenUp;
            if (i >= 50) {
                tup |= 1L<<(i-50);
            } else {
                tlw |= 1L<<i;
            }
            member[mi++] = i;
            dfs(i+1, tlw, tup);
            --mi;
        }
        dfs(i+1, chosenLw, chosenUp);
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
