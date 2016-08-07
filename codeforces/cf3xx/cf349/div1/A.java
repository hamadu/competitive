package codeforces.cf3xx.cf349.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/08/06.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] c = in.nextToken().toCharArray();
        int n = c.length;

        boolean[][] word2 = new boolean[26][26];
        boolean[][][] word3 = new boolean[26][26][26];

        int[][] dp = new int[n+1][2];
        dp[n][0] = 1;
        for (int i = n ; i >= 5 ; i--) {
            for (int l = 0 ; l <= 1 ; l++) {
                if (dp[i][l] == 0) {
                    continue;
                }
                if (i+l+2 <= n) {
                    char p0 = c[i], p1 = c[i+1], p2 = (l == 1) ? c[i+2] : 0;
                    if (l == 0) {
                        word2[p0-'a'][p1-'a'] = true;
                    } else {
                        word3[p0-'a'][p1-'a'][p2-'a'] = true;
                    }

                    if (l == 0 && c[i-2] == p0 && c[i-1] == p1) {
                    } else {
                        dp[i-2][0] = 1;
                    }
                    if (l == 1 && c[i-3] == p0 && c[i-2] == p1 && c[i-1] == p2) {
                    } else {
                        dp[i-3][1] = 1;
                    }
                } else {
                    dp[i-2][0] = 1;
                    dp[i-3][1] = 1;
                }
            }
        }


        List<String> ans = new ArrayList<>();
        String[] a = new String[26];
        for (int i = 0; i < 26; i++) {
            a[i] = ""+(char)('a'+i);
        }
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (word2[i][j]) {
                    ans.add(a[i]+a[j]);
                }
                for (int k = 0; k < 26; k++) {
                    if (word3[i][j][k]) {
                        ans.add(a[i]+a[j]+a[k]);
                    }
                }
            }
        }

        out.println(ans.size());
        for (String ll : ans) {
            out.println(ll);
        }
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
