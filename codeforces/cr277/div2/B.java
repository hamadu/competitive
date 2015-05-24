package codeforces.cr277.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/24.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();

        int[][] b = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                b[i][j] = in.nextInt();
            }
        }
        int[][] a = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(a[i], 1);
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                if (b[i][j] == 0) {
                    for (int k = 0; k < n ; k++) {
                        a[k][j] = 0;
                    }
                    for (int k = 0; k < m ; k++) {
                        a[i][k] = 0;
                    }
                }
            }
        }

        int[][] c = build(a);

        boolean same = true;
        sch: for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (b[i][j] != c[i][j]) {
                    same = false;
                    break sch;
                }
            }
        }

        if (same) {
            out.println("YES");
            for (int i = 0; i < n ; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < m ; j++) {
                    line.append(' ').append(a[i][j]);
                }
                out.println(line.substring(1));
            }
        } else {
            out.println("NO");
        }
        out.flush();
    }

    private static int[][] build(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        int[][] ret = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    ret[i][j] |= a[k][j];
                }
                for (int k = 0; k < m; k++) {
                    ret[i][j] |= a[i][k];
                }
            }
        }
        return ret;
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
