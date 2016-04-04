package codeforces.cf1xx.cr199.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/23.
 */
public class A {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n =  in.nextInt();
        int[] deg = new int[8];
        for (int i = 0; i < n ; i++) {
            deg[in.nextInt()]++;
        }

        int[][] ret = solve(n, deg);

        if (ret == null) {
            out.println(-1);
        } else {
            for (int[] x : ret) {
                out.println(x[0] + " " + x[1] + " " + x[2]);
            }
        }
        out.flush();
    }

    static int[][] solve(int n, int[] deg) {
        if (deg[5] >= 1 || deg[7] >= 1) {
            return null;
        }
        if (deg[1] != n / 3) {
            return null;
        }
        int[][] g = new int[n/3][3];

        int gi = 0;

        int[][] grp = new int[][]{ {1,2,4}, {1,2,6}, {1,3,6} };
        for (int[] gr : grp) {
            while (deg[gr[0]] >= 1 && deg[gr[1]] >= 1 && deg[gr[2]] >= 1) {
                g[gi++] = gr.clone();
                deg[gr[0]]--;
                deg[gr[1]]--;
                deg[gr[2]]--;
            }
        }
        if (gi != n/3) {
            return null;
        }
        return g;
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
