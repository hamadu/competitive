package atcoder.other2017.njpc2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Stack;

public class G {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        s = (in.nextToken() + '~').toCharArray();
        n = s.length;
        memo = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(memo[i], -1);
        }

        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                dfs(i, j);
            }
        }

        boolean[] dp = new boolean[n+1];
        Stack<int[]> stk = new Stack<>();
        for (int i = n-2 ; i >= 0 ; i--) {
            int fr = i;
            int to = i+1;
            while (stk.size() >= 1) {
                int[] prev = stk.pop();
                if (to != prev[0]) {
                    throw new RuntimeException("ohno");
                }
                if (canConnect(fr, to, prev[1])) {
                    to = prev[1];
                } else {
                    stk.push(prev);
                    break;
                }
            }
            stk.push(new int[]{fr, to});
        }

        out.println(stk.size() == 1 ? "Yes" : "No");
        out.flush();
    }

    static int n;
    static char[] s;
    static int[][] memo;

    // [a, k)[k, b)
    static boolean canConnect(int a, int k, int b) {
        int dist = memo[a][k];
        if (a+dist >= k || k+dist >= b) {
            int ln = k-a;
            int rn = b-k;
            return ln <= rn;
        }
        return s[a+dist] <= s[k+dist];
    }

    // a < b
    static int dfs(int a, int b) {
        if (memo[a][b] != -1) {
            return memo[a][b];
        }
        if (s[a] == s[b]) {
            int c = dfs(a+1, b+1)+1;
            memo[a][b] = c;
        } else {
            memo[a][b] = 0;
        }
        return memo[a][b];
    }



    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
