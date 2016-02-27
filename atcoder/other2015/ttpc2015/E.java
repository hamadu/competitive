package atcoder.other2015.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/20.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        boolean[][] isRed = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                isRed[i][j] = (i+j)%2 == 0;
            }
        }

        boolean[] importantX = new boolean[n];
        boolean[] importantY = new boolean[n];
        importantX[0] = importantX[n-1] = true;
        importantY[0] = importantY[n-1] = true;

        int[][] rep = new int[k][2];
        for (int i = 0; i < k ; i++) {
            for (int j = 0; j < 2 ; j++) {
                rep[i][j] = in.nextInt()-1;
            }
            isRed[rep[i][0]][rep[i][1]] = !isRed[rep[i][0]][rep[i][1]];
            for (int d = -3 ; d <= 3; d++) {
                int ti = rep[i][0]+d;
                if (ti >= 0 && ti < n) {
                    importantY[ti] = true;
                }
                int tj = rep[i][1]+d;
                if (tj >= 0 && tj < n) {
                    importantX[tj] = true;
                }
            }
        }

        int[] xs = new int[n];
        int xn = 0;
        int[] ys = new int[n];
        int yn = 0;
        for (int i = 0; i < n ; i++) {
            if (importantX[i]) {
                xs[xn++] = i;
            }
            if (importantY[i]) {
                ys[yn++] = i;
            }
        }

        int[][] imos = new int[n+1][n+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                imos[i+1][j+1] = imos[i+1][j] + imos[i][j+1] - imos[i][j] + (isRed[i][j] ? 1 : 0);
            }
        }
        int max = 1;
        for (int y1 = 0; y1 < yn ; y1++) {
            for (int y2 = y1 ; y2 < yn; y2++) {
                for (int x1 = 0; x1 < xn ; x1++) {
                    for (int x2 = x1; x2 < xn ; x2++) {
                        int fy = ys[y1];
                        int ty = ys[y2];
                        int fx = xs[x1];
                        int tx = xs[x2];
                        int range = (ty-fy+1) * (tx-fx+1);
                        int red = imos[ty+1][tx+1]-imos[ty+1][fx]-imos[fy][tx+1]+imos[fy][fx];
                        int blue = range-red;
                        max = Math.max(max, Math.abs(red-blue));
                    }
                }
            }
        }

        out.println(max);
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
