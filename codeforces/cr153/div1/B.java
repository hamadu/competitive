package codeforces.cr153.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/03.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int k = in.nextInt();

        int[] q = new int[n];
        for (int i = 0; i < n ; i++) {
            q[i] = in.nextInt()-1;
        }
        int[] want = new int[n];
        for (int i = 0; i < n ; i++) {
            want[i] = in.nextInt()-1;
        }

        int[][] perm = new int[401][n];
        for (int i = 0; i < n ; i++) {
            perm[200][i] = i;
        }
        // go

        int minX = 0;
        int maxX = 400;
        for (int i = 0; i <= k ; i++) {
            int fr = 200+i;
            boolean eq = true;
            for (int j = 0; j < n ; j++) {
                if (perm[fr][j] != want[j]) {
                    eq = false;
                    break;
                }
            }
            if (eq) {
                maxX = fr;
                break;
            }

            int to = fr+1;
            for (int j = 0; j < n ; j++) {
                perm[to][j] = perm[fr][q[j]];
            }
        }
        // rev
        for (int i = 0; i <= k ; i++) {
            int fr = 200-i;
            boolean eq = true;
            for (int j = 0; j < n ; j++) {
                if (perm[fr][j] != want[j]) {
                    eq = false;
                    break;
                }
            }
            if (eq) {
                minX = fr;
                break;
            }

            int to = fr-1;
            for (int j = 0; j < n ; j++) {
                perm[to][q[j]] = perm[fr][j];
            }
        }

        boolean[][] mo = new boolean[k+1][401];
        mo[0][200] = true;
        for (int i = 0; i < k ; i++) {
            for (int j = 0; j < 401; j++) {
                if (!mo[i][j]) {
                    continue;
                }
                for (int d = -1; d <= 1; d += 2) {
                    int tj = j+d;
                    if (minX < tj && tj < maxX) {
                        mo[i+1][tj] = true;
                    }
                    if (i+1 == k && minX <= tj && tj <= maxX) {
                        mo[i+1][tj] = true;
                    }
                }
            }
        }

        out.println(mo[k][maxX] || mo[k][minX] ? "YES" : "NO");
        out.flush();
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
