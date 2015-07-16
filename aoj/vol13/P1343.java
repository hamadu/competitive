package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/16.
 */
public class P1343 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[] a = new int[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt();
            }
            out.println(solve(a));
        }
        out.flush();
    }

    private static int solve(int[] a) {
        int n = a.length;
        int max = 0;

        Set<Integer> setOfInput = new HashSet<Integer>();
        for (int i = 0; i < n ; i++) {
            setOfInput.add(a[i]);
        }

        boolean[] nonNeedToTry = new boolean[513];
        int[] f = new int[n];
        for (int i = 1 ; i <= 512 ; i++) {
            if (nonNeedToTry[i] || !setOfInput.contains(i))  {
                continue;
            }
            for (int l = i ; l <= 512 ; l *= 2) {
                nonNeedToTry[l] = true;
            }

            int fi = 0;
            for (int j = 0; j < n ; j++) {
                if (a[j] % i == 0) {
                    int d = a[j] / i;
                    if (Integer.bitCount(d) == 1) {
                        f[fi++] = d;
                    }
                }
            }

            int[] bin = Arrays.copyOfRange(f, 0, fi);
            max = Math.max(max, solveSub(bin));
        }
        return max;
    }

    @SuppressWarnings("unchecked")
    private static int solveSub(int[] bin) {
        int n = bin.length;
        int sum = 0;
        for (int i = 0; i < bin.length ; i++) {
            sum += bin[i];
        }

        int[][] dp = new int[2][sum+1];
        for (int i = 0; i <= 1; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        int subSum = 0;
        for (int i = 0; i < n ; i++) {
            int fi = i % 2;
            int ti = 1 - fi;
            Arrays.fill(dp[ti], -1);
            for (int j = 0 ; j <= subSum ; j++) {
                if (dp[fi][j] == -1) {
                    continue;
                }
                int base = dp[fi][j];

                dp[ti][j] = Math.max(dp[ti][j], base);
                if ((j % bin[i]) == 0) {
                    int tj = j + bin[i];
                    dp[ti][tj] = Math.max(dp[ti][tj], base+1);
                }
            }
            subSum += bin[i];
        }

        int ret = 0;
        for (int i = 1 ; i <= sum; i *= 2) {
            ret = Math.max(ret, dp[n%2][i]);
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
