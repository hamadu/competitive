package aoj.vol24;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/29.
 */
public class P2434 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        Idol chihaya = new Idol(in.nextInt(), in.nextInt(), in.nextInt());
        Idol[] rivals = new Idol[n-1];
        for (int i = 0; i < n-1 ; i++) {
            rivals[i] = new Idol(in.nextInt(), in.nextInt(), in.nextInt());
        }
        out.println(String.format("%.12f", solve(m, chihaya, rivals)));
        out.flush();
    }

    private static double solve(int m, Idol chihaya, Idol[] rivals) {
        int[] winScore = new int[]{5, 3, 2};

        double[][] pred = new double[m+1][m+1];
        pred[0][0] = 1.0d;
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < m ; j++) {
                if (pred[i][j] == 0) {
                    continue;
                }
                pred[i+1][j+1] += pred[i][j] / 3.0d;
                pred[i+1][j] += 2 * pred[i][j] / 3.0d;
            }
        }


        double[] imos = new double[m+1];
        for (int i = m ; i >= 0; i--) {
            imos[i] += pred[m][i];
            if (i >= 1) {
                imos[i-1] += imos[i];
            }
        }

        int n = rivals.length;
        double[][] vidavo = new double[m+1][3];
        for (int i = 0; i <= m ; i++) {
            for (int j = 0; j < 3 ; j++) {
                int score = chihaya.vidavo[j] * i;

                double[] overRate = new double[n];
                for (int k = 0; k < n ; k++) {
                    int cnt = (score + rivals[k].vidavo[j] - 1) / rivals[k].vidavo[j];
                    if (cnt <= m) {
                        overRate[k] = imos[cnt];
                    }
                }

                double[][] dp = new double[n+1][3];
                dp[0][0] = 1.0d;
                for (int k = 0; k < n ; k++) {
                    for (int l = 0; l < 3 ; l++) {
                        if (dp[k][l] == 0) {
                            continue;
                        }
                        dp[k+1][l] += dp[k][l] * (1.0d - overRate[k]);
                        if (l+1 < 3) {
                            dp[k+1][l+1] += dp[k][l] * overRate[k];
                        }
                    }
                }
                double win = dp[n][0] + dp[n][1] + dp[n][2];
                double lose = 1.0d;
                for (int k = 0; k < n ; k++) {
                    lose *= overRate[k];
                }
                vidavo[i][j] = win * winScore[j] - lose;
            }
        }

        double max = -10000;
        for (int vi = 0; vi <= m; vi++) {
            for (int da = 0; vi+da <= m; da++) {
                int vo = m - vi - da;
                max = Math.max(max, vidavo[vi][0] + vidavo[da][1] + vidavo[vo][2]);
            }
        }
        return max;
    }

    static class Idol {
        int[] vidavo;

        Idol(int a, int b, int c) {
            vidavo = new int[]{a, b, c};
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
