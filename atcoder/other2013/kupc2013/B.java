package atcoder.other2013.kupc2013;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/01.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int x = in.nextInt();
        int m = in.nextInt();
        int[][] q = new int[m][3];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3 ; j++) {
                q[i][j] = in.nextInt();
            }
            q[i][0]--;
        }

        dfs(0, x, new int[n], q);

        if (best == null) {
            out.println(-1);
        } else {
            for (int i = 0; i < best.length ; i++) {
                if (i >= 1) {
                    out.print(' ');
                }
                out.print(best[i]);
            }
            out.println();
        }

        out.flush();
    }

    private static void dfs(int idx, int max, int[] lions, int[][] q) {
        if (idx == lions.length) {
            int[] imos = new int[lions.length+1];
            for (int i = 0; i < lions.length ; i++) {
                imos[i+1] = imos[i] + lions[i];
            }
            boolean isOK = true;
            for (int[] qi : q) {
                if (imos[qi[1]] - imos[qi[0]] != qi[2]) {
                    isOK = false;
                    break;
                }
            }
            if (isOK) {
                if (bestSum < imos[lions.length]) {
                    bestSum = imos[lions.length];
                    best = lions.clone();
                }
            }

            return;
        }
        for (int i = 0; i <= max; i++) {
            lions[idx] = i;
            dfs(idx+1, max, lions, q);
        }
    }

    static int bestSum = -1;
    static int[] best;

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
