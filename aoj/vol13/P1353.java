package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeMap;

/**
 * Created by hama_du on 15/07/30.
 */

@SuppressWarnings("unchecked")
public class P1353 {
    static final long INF  = 1000000000000000L;
    static final long INF2 = 10000000000000000L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long A = in.nextInt();
        long B = in.nextInt();
        long[][] s = new long[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                s[i][j] = in.nextInt();
            }
        }

        long[] ret = solve(A, B, s);
        out.println(ret[0] + " " + ret[1]);
        out.flush();
    }

    private static long[] solve(long a, long b, long[][] s) {
        n = s.length;
        sweets = s;
        left = new long[n+1];
        for (int i = 0; i < n ; i++) {
            for (int j = i ; j < n; j++) {
                left[i] += sweets[j][1];
            }
        }
        memo = new TreeMap[2][151];
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j < 151; j++) {
                memo[i][j] = new TreeMap<Long,Long>();
            }
        }

        long alice = dfs2(0, 0, a - b);
        return new long[]{alice, left[0] - alice};
    }

    static int n;
    static long[][] sweets;
    static long[] left;

    static TreeMap<Long,Long> memo[][];

    static void prec(int turn, int idx) {
        if (memo[turn][idx].size() >= 1) {
            return;
        }
        long min = dfs(turn, idx, -INF);
        long max = dfs(turn, idx, INF) + 1;
        memo[turn][idx].put(-INF2, min);
        memo[turn][idx].put(INF2, max);

        for (long j = min+1 ; j < max ; j++) {
            long left = -INF;
            long right = INF;
            while (right - left > 1) {
                long med = (right + left) / 2;
                if (dfs(turn, idx, med) < j) {
                    left = med;
                } else {
                    right = med;
                }
            }
            j = dfs(turn, idx, right);
            memo[turn][idx].put(right, j);
        }
    }

    static long dfs2(int turn, int idx, long diff) {
        if (idx == n) {
            return 0;
        }
        prec(turn, idx);

        // find the key <= diff
        return memo[turn][idx].floorEntry(diff).getValue();
    }

    static long dfs(int turn, int idx, long diff) {
        if (idx == n) {
            return 0;
        }
        // take the idx-th sweets
        long ret = left[idx+1] - dfs2(1-turn, idx+1, -(diff + sweets[idx][0])) + sweets[idx][1];
        if (diff >= 1) {
            // pass and let opponent take the idx-th sweets
            ret = Math.max(ret, dfs2(turn, idx + 1, diff - 1 - sweets[idx][0]));
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
