package atcoder.other2016.dwango2016.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/02/28.
 */
public class B {
    private static final double INF = 1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        List<Integer> plus = new ArrayList<>();
        List<Integer> minus = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            int x = in.nextInt();
            if (x > 0) {
                plus.add(x);
            } else {
                minus.add(x);
            }
        }
        Collections.sort(plus);
        Collections.sort(minus);
        Collections.reverse(minus);

        out.println(String.format("%.12f", solve(plus, minus)));
        out.flush();
    }

    static double[][][] dp = new double[1050][1050][2];


    private static double solve(List<Integer> plus, List<Integer> minus) {
        int pn = plus.size();
        int mn = minus.size();
        if (pn == 0 || mn == 0) {
            return 1.0d;
        }
        double min = 1.0d;
        double max = 1e10;
        for (int k = 0 ; k < 100 ; k++) {
            double med = (min + max) / 2;
            if (isPossible(plus, minus, med)) {
                max = med;
            } else {
                min = med;
            }
        }
        return max;
    }

    private static boolean isPossible(List<Integer> plus, List<Integer> minus, double v) {
        int n = plus.size();
        int m = minus.size();

        for (int i = 0 ; i <= n ; i++) {
            for (int j = 0 ; j <= m ; j++) {
                Arrays.fill(dp[i][j], 1e18);
            }
        }
        dp[0][0][0] = 0;
        dp[0][0][1] = 0;

        for (int i = 0 ; i <= n ; i++) {
            for (int j = 0; j <= m ; j++) {
                for (int k = 0; k <= 1 ; k++) {
                    if (dp[i][j][k] >= INF) {
                        continue;
                    }
                    double base = dp[i][j][k];

                    int pos = 0;
                    if (i >= 1 && k == 0) {
                        pos = plus.get(i-1);
                    }  else if (j >= 1 && k == 1) {
                        pos = minus.get(j-1);
                    }

                    if (i < n) {
                        int dist = Math.abs(pos - plus.get(i));
                        double time = base + dist / v;
                        if (time <= Math.abs(plus.get(i))) {
                            dp[i+1][j][0] = Math.min(dp[i+1][j][0], time);
                        }
                    }
                    if (j < m) {
                        int dist = Math.abs(pos - minus.get(j));
                        double time = base + dist / v;
                        if (time <= Math.abs(minus.get(j))) {
                            dp[i][j+1][1] = Math.min(dp[i][j+1][1], time);
                        }
                    }
                }
            }
        }
        return dp[n][m][0] < INF || dp[n][m][1] < INF;
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
