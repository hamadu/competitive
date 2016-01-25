package atcoder.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/09/20.
 */
public class H {
    private static final double INF = 1145141919810893.0f;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] p = new long[n+1][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                p[i][j] = in.nextInt();
            }
        }

        List<int[]> lines = new ArrayList<>();
        double best = INF;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1; j < n ; j++) {
                for (int k = j+1; k < n; k++) {
                    long c1 = ccw(p, i, j, n);
                    long c2 = ccw(p, j, k, n);
                    long c3 = ccw(p, k, i, n);
                    if (c1 == c2 && c2 == c3 && c1 != 0) {
                        best = Math.min(best, area(p, i, j, k));
                    }
                }
                if (between(p, i, j, n)) {
                    lines.add(new int[]{i, j});
                }
            }
        }

        for (int[] ij : lines) {
            int i = ij[0];
            int j = ij[1];
            double left = INF;
            double right = INF;
            for (int k = 0 ; k < n ; k++) {
                if (k != i && k != j) {
                    long cr = cross(p, i, j, k);
                    if (cr > 0) {
                        left = Math.min(left, area(p, i, j, k));
                    } else if (cr < 0) {
                        right = Math.min(right, area(p, i, j, k));
                    }

                }
            }
            best = Math.min(best, left+right);
        }
        if (best >= INF) {
            out.println("Impossible");
        } else {
            out.println("Possible");
            out.println(String.format("%.9f", best));
        }
        out.flush();
    }

    private static double area(long[][] p, int i, int j, int k) {
        return Math.abs(cross(p, i, j, k)) * 0.5;
    }

    private static boolean between(long[][] p, int i, int j, int k) {
        if (cross(p, i, j, k) != 0) {
            return false;
        }

        // v1:i-k
        // v2:j-k
        long[] v1 = { p[k][0] - p[i][0], p[k][1] - p[i][1] };
        long[] v2 = { p[k][0] - p[j][0], p[k][1] - p[j][1] };
        if (v1[0] != 0) {
            return v1[0] / Math.abs(v1[0]) != v2[0] / Math.abs(v2[0]);
        }
        return v1[1] / Math.abs(v1[1]) != v2[1] / Math.abs(v2[1]);
    }

    private static long cross(long[][] p, int i, int j, int k) {
        // v1:i-j
        // v2:i-k
        long[] v1 = { p[j][0] - p[i][0], p[j][1] - p[i][1] };
        long[] v2 = { p[k][0] - p[i][0], p[k][1] - p[i][1] };

        return v1[0]*v2[1] - v1[1]*v2[0];
    }


    private static long ccw(long[][] p, int i, int j, int k) {
        long cr = cross(p, i, j, k);
        if (cr == 0) {
            return 0;
        }
        return cr / Math.abs(cr);
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
