package codeforces.goodbye2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/06.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int h = in.nextInt();
        int w = in.nextInt();
        char[][] map = new char[h][];
        for (int i = 0; i < h ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        int[][] placeW = new int[h][w];
        int[][] placeH = new int[h][w];
        int[][] imosW = new int[h+1][w+1];
        int[][] imosH = new int[h+1][w+1];
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                if (j+1 < w && map[i][j] == '.' && map[i][j+1] == '.') {
                    placeW[i][j]++;
                }
                if (i+1 < h && map[i][j] == '.' && map[i+1][j] == '.') {
                    placeH[i][j]++;
                }
            }
        }
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                imosW[i+1][j+1] = imosW[i+1][j] + imosW[i][j+1] - imosW[i][j] + placeW[i][j];
                imosH[i+1][j+1] = imosH[i+1][j] + imosH[i][j+1] - imosH[i][j] + placeH[i][j];
            }
        }


        int q = in.nextInt();
        while (--q >= 0) {
            int r1 = in.nextInt()-1;
            int c1 = in.nextInt()-1;
            int r2 = in.nextInt()-1;
            int c2 = in.nextInt()-1;
            int ans = range(imosH, r1, c1, r2-1, c2) + range(imosW, r1, c1, r2, c2-1);
            out.println(ans);
        }
        out.flush();
    }

    private static int range(int[][] imos, int r1, int c1, int r2, int c2) {
        return imos[r2+1][c2+1] - imos[r2+1][c1] - imos[r1][c2+1] + imos[r1][c1];
    }

    private static int solveMonth(int x) {
        if (x <= 29) {
            return 12;
        }
        if (x == 30) {
            return 11;
        }
        return 7;
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
