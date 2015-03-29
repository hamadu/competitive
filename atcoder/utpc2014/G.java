package atcoder.utpc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class G {
    static final long MOD = 1000000007;

    static int[] decode(int p) {
        int[] x = new int[11];
        for (int d = 0 ; d < x.length ; d++) {
            x[d] = p % 3;
            p /= 3;
        }
        return x;
    }

    static int encode(int[] d) {
        int num = 0;
        for (int i = d.length-1 ; i >= 0 ; i--) {
            num *= 3;
            num += d[i];
        }
        return num;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int X = in.nextInt();
        int P = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            String l = in.next();
            if (l.equals("?")) {
                a[i] = -1;
            } else {
                a[i] = Integer.valueOf(l);
            }
        }


        long[][] dp = new long[n+1][200000];
        dp[0][1] = 1;
        for (int i = 0 ; i < n ; i++) {
            for (int p = 0 ; p < dp[0].length ; p++) {
                if (dp[i][p] == 0) {
                    continue;
                }
                int[] now = decode(p);
                for (int d = 0 ; d <= P ; d++) {
                    if (a[i] == -1 || a[i] == d) {
                        int[] to = now.clone();
                        for (int f = 0 ; f+d < to.length ; f++) {
                            to[f+d] += now[f];
                            to[f+d] = Math.min(to[f+d], 2);
                        }
                        int tp = encode(to);
                        dp[i+1][tp] += dp[i][p];
                        dp[i+1][tp] %= MOD;
                    }
                }
            }
        }

        long ans = 0;
        for (int p = 0 ; p < dp[0].length ; p++) {
            int[] ptn = decode(p);
            if (ptn[X] == 1) {
                ans += dp[n][p];
                ans %= MOD;
            }
        }
        out.println(ans);
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



