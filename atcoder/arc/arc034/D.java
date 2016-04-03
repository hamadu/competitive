package atcoder.arc.arc034;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/03/19.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        A = n;
        B = m;
        yu = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[m];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < m ; i++) {
            b[i] = in.nextInt();
        }

        averageOfB = new double[m+1];
        averageOfB[0] = 1;
        for (int i = 0; i < m ; i++) {
            for (int j = m-1 ; j >= 0 ; j--) {
                averageOfB[j+1] += averageOfB[j] * b[i];
            }
        }
        double[][] ncr = new double[m+1][m+1];
        for (int i = 0; i <= m ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1; j < i ; j++) {
                ncr[i][j] = ncr[i-1][j] + ncr[i-1][j-1];
            }
        }
        for (int i = 0; i <= m ; i++) {
            averageOfB[i] /= ncr[m][i];
        }
        for (int i = 0; i < n ; i++) {
            averageOfA += a[i];
        }
        averageOfA /= n;

        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j <= m ; j++) {
                memo[i][j] = -1;
            }
        }
        out.println(dfs(n, m));
        out.flush();
    }

    static double dfs(int a, int b) {
        if (memo[a][b] >= 0) {
            return memo[a][b];
        }
        double ret = 0;
        double aRate = (1.0 * a) / (a + b + yu);
        double bRate = (1.0 * b) / (a + b + yu);
        if (a >= 1) {
            int idx = B - b;
            ret += (averageOfA * averageOfB[idx] + dfs(a-1, b)) * aRate;
        }
        if (b >= 1) {
            ret += dfs(a, b-1) * bRate;
        }
        memo[a][b] = ret;
        return ret;
    }

    static double[][] memo = new double[101][101];

    static int A;
    static int B;
    static int yu;
    static double averageOfA;
    static double[] averageOfB;


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
