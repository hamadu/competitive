package csacademy.round011;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 2016/08/28.
 */
public class D {
    private static final int INF = 1000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int m = in.nextInt();
        int first = in.nextInt()-1;
        boolean[] forbidden = new boolean[n];
        for (int i = 0; i < m ; i++) {
            forbidden[in.nextInt()-1] = true;
        }

        TreeSet<Integer>[] req = new TreeSet[2];
        for (int i = 0; i < 2 ; i++) {
            req[i] = new TreeSet<>();
        }
        for (int i = 0 ; i < n ; i++) {
            int d = Math.abs(i-first) % 2;
            if (!forbidden[i] && first != i) {
                req[d].add(i);
            }
        }

        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        dp[first] = 0;
        int[][] ques = new int[2][n];
        int[] qh = new int[2];
        int[] qt = new int[2];
        ques[0][qh[0]++] = first;
        int updcnt = 0;
        for (int p = 0 ; ; p ^= (k % 2 == 0) ? 1 : 0) {
            updcnt++;
            int np = (k % 2 == 1) ? p : 1-p;
            while (qt[p] < qh[p]) {
                int now = ques[p][qt[p]++];
                int time = dp[now]+1;

                int low = now-(k-1);
                if (low < 0) {
                    low = -low;
                }

                int high = now+(k-1);
                if (high >= n) {
                    high = (n-1)-(high-n+1);
                }

                // go left
                int head = (high <= now ? high : now);
                while (true) {
                    Integer to = req[np].floor(head);
                    if (to == null || Math.abs(now-to) >= k || to < low) {
                        break;
                    }
                    int ti = to;
                    if (dp[ti] > time) {
                        updcnt = 0;
                        dp[ti] = time;
                        ques[np][qh[np]++] = ti;
                        req[np].remove(to);
                    }
                    head = ti-1;
                }

                // go right
                head = ((now < low) ? low : now);
                while (true) {
                    Integer to = req[np].ceiling(head);
                    if (to == null || Math.abs(now-to) >= k || to > high) {
                        break;
                    }
                    int ti = to;
                    if (dp[ti] > time) {
                        updcnt = 0;
                        dp[ti] = time;
                        ques[np][qh[np]++] = ti;
                        req[np].remove(to);
                    }
                    head = ti+1;
                }
            }
            if (updcnt >= 2) {
                break;
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(dp[i] == INF ? -1 : dp[i]);
        }
        out.println(line.substring(1));
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
