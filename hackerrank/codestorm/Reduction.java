package hackerrank.codestorm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/10/30.
 */
public class Reduction {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        Arrays.fill(grundy, -1);
        for (int i = 0 ; i <= 1000000; i++) {
            game(i);
        }
        int[][] g = new int[8][1000010];
        for (int i = 1 ; i <= 1000000; i++) {
            for (int j = 0; j < 8; j++) {
                g[j][i] += g[j][i-1];
            }
            g[grundy[i]][i]++;
        }

        long[] pow2 = new long[1000010];
        pow2[0] = 1;
        for (int i = 1 ; i <= 1000000; i++) {
            pow2[i] = (pow2[i-1] * 2) % MOD;
        }

        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] left = new int[8];
            for (int i = 0; i < 8 ; i++) {
                left[i] = g[i][n];
            }

            int xor = 0;
            for (int i = 0; i < m ; i++) {
                int a = in.nextInt();
                left[grundy[a]]--;
                xor ^= grundy[a];
            }

            long[][][] dp = new long[9][32][2];
            dp[0][0][0] = 1;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 32; j++) {
                    for (int f = 0; f <= 1; f++) {
                        long base = dp[i][j][f];
                        if (base == 0) {
                            continue;
                        }

                        // use odd
                        if (left[i] >= 1) {
                            int txor = j ^ i;
                            dp[i+1][txor][1] += (base * pow2[left[i]-1]) % MOD;
                            dp[i+1][txor][1] %= MOD;
                        }

                        // use even
                        if (left[i] >= 0) {
                            long pt = left[i] == 0 ? 1 : ((pow2[left[i]-1])%MOD);
                            dp[i+1][j][1] += (base * pt) % MOD;
                            dp[i+1][j][1] %= MOD;
                        }

                        // use nothing
                        // dp[i+1][j][f] += base;
                        // dp[i+1][j][f] %= MOD;
                    }
                }
            }
            long ans = dp[8][xor][1];
            if (xor == 0) {
                ans = ((ans + MOD) - 1) % MOD;
            }
            out.println(ans);
        }

        out.flush();
    }

    static int[] grundy = new int[1000010];

    static int game(int num) {
        if (num <= 9) {
            grundy[num] = 0;
            return 0;
        }
        if (grundy[num] != -1) {
            return grundy[num];
        }

        Set<Integer> gd = new HashSet<>();

        String ns = String.valueOf(num);
        char[] ch = ns.toCharArray();
        for (int i = 0; i+1 < ch.length ; i++) {
            int d1 = ch[i]-'0';
            int d2 = ch[i+1]-'0';
            int result = (d1+d2)%10;
            int to = -1;

            String left = ns.substring(0, i);
            String right = ns.substring(i+2);
            if (left.length() == 0 && right.length() == 0) {
                to = result;
            } else {
                to = Integer.valueOf(left+result+right);
            }
            gd.add(game(to));
        }
        int now = 0;
        while (gd.contains(now)) {
            now++;
        }
        grundy[num] = now;
        return now;
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
                res += c-'0';
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
                res += c-'0';
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
