package codeforces.cf0xx.cr92.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/26.
 */
public class C {

    static final long INF = Long.MAX_VALUE / 8;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long k = in.nextLong();

        int[] qy = new int[n*m];
        int[] qx = new int[n*m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                int ord = in.nextInt()-1;
                qy[ord] = i;
                qx[ord] = j;
            }
        }

        long cnt = 0;
        int[] col = new int[n+m-1];
        for (int i = 0 ; i < n*m ; i++) {
            int y = qy[i];
            int x = qx[i];
            int d = x+y;
            if (col[d] != 0) {
                continue;
            }
            col[d] = 1;
            long ptn = count(col);
            if (cnt + ptn < k) {
                cnt += ptn;
                col[d] = -1;
            }
        }

        for (int i = 0; i < n ; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < m ; j++) {
                int d = i+j;
                line.append(col[d] == 1 ? '(' : ')');
            }
            out.println(line.toString());
        }
        out.flush();
    }

    static long count(int[] col) {
        int n = col.length;
        long[][] dp = new long[n+1][n+1];
        dp[0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int d = 0; d <= n ; d++) {
                if (dp[i][d] == 0) {
                    continue;
                }
                for (int lr = -1 ; lr <= 1 ; lr += 2) {
                    if (col[i] == 0 || col[i] == lr) {
                        int td = d + lr;
                        if (td < 0 || td > n) {
                            continue;
                        }
                        dp[i+1][td] += dp[i][d];
                        if (dp[i+1][td] >= INF) {
                            dp[i+1][td] = INF;
                        }
                    }
                }
            }
        }
        return dp[n][0];
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
