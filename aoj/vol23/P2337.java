package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/20.
 */
public class P2337 {

    static int INF = 2000000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

       //  BIT.verify();

        int na = in.nextInt();
        int nb = in.nextInt();
        int W = in.nextInt();

        int n = na+nb;

        int[][] nyaa = new int[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                nyaa[i][j] = in.nextInt();
            }
            nyaa[i][2] = (i < na) ? 0 : 1;
        }
        Arrays.sort(nyaa, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int[][][] dp = new int[2][2][W+1];
        for (int s = 0; s <= 1 ; s++) {
            for (int t = 0; t <= 1; t++) {
                Arrays.fill(dp[s][t], -1);
            }
        }
        dp[0][0][0] = 0;
        dp[1][0][0] = 0;

        int best = Integer.MAX_VALUE;
        int fr = 0;
        int to = 0;

        int[][] w2 = new int[2][W+1];

        BIT bit = new BIT(W+1);
        while (fr < n) {
            int cdiff = (to == 0 || to == fr) ? 0 : nyaa[to-1][1] - nyaa[fr][1];
            // [fr,to]
            int wdiff = INF;
            w2[0] = dp[0][to%2];
            w2[1] = dp[1][to%2];

            bit.clear();
            for (int wi = 1; wi <= W; wi++) {
                if (w2[1][wi] >= nyaa[fr][1] && w2[1][wi] != -1) {
                    bit.add(wi, 1);
                }
            }
            for (int wi = 1; wi <= W ; wi++) {
                if (w2[0][wi] < nyaa[fr][1] || w2[0][wi] == -1) {
                    continue;
                }
                int left = bit.beforeNonZeroPosition(wi);
                int right = bit.afterNonZeroPosition(wi);
                if (left >= 1) {
                    wdiff = Math.min(wdiff, wi - left);
                }
                if (right <= W) {
                    wdiff = Math.min(wdiff, right - wi);
                }
            }

            best = Math.min(best, Math.max(cdiff, wdiff));
            if (wdiff < cdiff || to == n) {
                fr++;
            } else {
                int fi = to % 2;
                int ti = 1 - fi;
                Arrays.fill(dp[0][ti], -1);
                Arrays.fill(dp[1][ti], -1);
                for (int s = 0; s <= 1 ; s++) {
                    for (int i = 0 ; i <= W ; i++) {
                        if (dp[s][fi][i] == -1) {
                            continue;
                        }
                        int base = dp[s][fi][i];
                        dp[s][ti][i] = Math.max(dp[s][ti][i], base);
                        int tw = i+nyaa[to][0];
                        if (tw <= W && s == nyaa[to][2]) {
                            int umc = base == 0 ? nyaa[to][1] : Math.min(base, nyaa[to][1]);
                            dp[s][ti][tw] = Math.max(dp[s][ti][tw], umc);
                        }
                    }
                }
                to++;
            }
        }

        out.println(best);

        out.flush();
    }


    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        int[] data;
        BIT(int n) {
            N = n;
            data = new int[n+1];
        }

        void clear() {
            Arrays.fill(data, 0);
        }

        int sum(int i) {
            int s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        int range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, int x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }

        // find rightmost non-zero position less or equal x
        int beforeNonZeroPosition(int x) {
            int u = sum(x);
            return findMostLeftPositionValueOf(u);
        }

        // find leftmost non-zero position more or equal x
        int afterNonZeroPosition(int x) {
            int u = sum(x);
            if (u > sum(x-1)) {
                return x;
            }
            return findMostLeftPositionValueOf(u+1);
        }

        // find position　at most right that have a[i] == value
        int findMostRightPositionValueOf(int value) {
            int i = 0;
            int n = data.length;
            for (int b = Integer.highestOneBit(n) ; b != 0 && i < n ; b >>= 1) {
                if (i + b < n) {
                    int t = i + b;
                    if (value >= data[t]) {
                        i = t;
                        value -= data[t];
                    }
                }
            }
            return i;
        }

        // find position at most left that have a[i] == value
        int findMostLeftPositionValueOf(int value) {
            if (value <= 0) {
                return 0;
            }
            // <most left> = <most right at previous value> + 1
            return findMostRightPositionValueOf(value-1)+1;
        }

        static void verify() {
            // input:
            //   i : 1 2 3 4 5 6 7 8 9
            //   a : 0 1 1 0 1 0 0 1 0
            //   s : 0 1 2 2 3 3 3 4 4
            // expected:
            //   < : X 2 3 3 5 5 5 8 8
            //   > : 2 2 3 5 5 8 8 8 X
            BIT bit = new BIT(9);
            bit.add(2, 1);
            bit.add(3, 1);
            bit.add(5, 1);
            bit.add(8, 1);
            for (int i = 1 ; i <= 9 ; i++) {
                debug(i, bit.beforeNonZeroPosition(i), bit.afterNonZeroPosition(i));
            }
        }
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
