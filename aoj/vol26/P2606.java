package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2606 {
    private static final long MOD = 1000000007;

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int[] perm = new int[n+1];
        for (int i = 1; i <= n ; i++) {
            perm[i] = in.nextInt();
        }

        int[] cur = new int[n+1];
        int[] sum = new int[n+1];
        for (int i = 1 ; i <= n ; i++) {
            int x = i;
            while (true) {
                x = perm[x];
                cur[i]++;
                sum[i] += x;
                if (sum[i] >= MOD) {
                    sum[i] -= MOD;
                }
                if (x == i) {
                    break;
                }
            }
        }

        int[][] imosCur = new int[41][n+2];
        int[][] imosSum = new int[41][n+2];
        for (int i = 2 ; i <= n+1 ; i++) {
            for (int j = 0; j < 41; j++) {
                imosCur[j][i] = imosCur[j][i-1];
                imosSum[j][i] = imosSum[j][i-1];
            }
            int ci = cur[i-1];
            imosCur[ci][i] += 1;
            imosSum[ci][i] += sum[i-1];
            if (imosSum[ci][i] >= MOD) {
                imosSum[ci][i] -= MOD;
            }
        }

        while (--q >= 0) {
            int l = in.nextInt();
            int r = in.nextInt()+1;

            long lcm = 1;
            for (int i = 1 ; i <= 40 ; i++) {
                int num = imosCur[i][r] - imosCur[i][l];
                if (num >= 1) {
                    lcm = lcm * i / gcd(lcm, i);
                }
            }

            long ans = 0;
            for (int i = 1; i <= 40 ; i++) {
                int num = imosCur[i][r] - imosCur[i][l];
                if (num >= 1) {
                    long K = (lcm / i) % MOD;
                    long add = (imosSum[i][r] - imosSum[i][l] + MOD) % MOD;
                    ans += (K * add) % MOD;
                }
            }
            out.println(ans % MOD);
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
