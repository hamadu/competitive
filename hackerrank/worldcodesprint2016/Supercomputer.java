package hackerrank.worldcodesprint2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/30.
 */
public class Supercomputer {

    private static final int[] DX = new int[]{1, 0, -1, 0};
    private static final int[] DY = new int[]{0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        char[][] pos = new char[n][m];
        for (int i = 0; i < n ; i++) {
            pos[i] = in.nextToken().toCharArray();
        }


        int[][] possible = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (pos[i][j] == 'B') {
                    continue;
                }
                for (int k = 0 ; k <= n ; k++) {
                    boolean isOK = true;
                    for (int d = 0 ; d < 4 ; d++) {
                        int ty = i + DY[d] * k;
                        int tx = j + DX[d] * k;
                        if (ty < 0 || tx < 0 || ty >= n || tx >= m || pos[ty][tx] == 'B') {
                            isOK = false;
                            break;
                        }
                    }
                    if (isOK) {
                        possible[i][j]++;
                    } else {
                        break;
                    }
                }
            }
        }


        int max = 0;
        for (int i1 = 0 ; i1 < n ; i1++) {
            for (int j1 = 0; j1 < m ; j1++) {
                int[][] mark = new int[n][m];
                for (int k1 = 0 ; k1 < possible[i1][j1] ; k1++) {
                    for (int d = 0 ; d < 4 ; d++) {
                        int ty = i1 + DY[d] * k1;
                        int tx = j1 + DX[d] * k1;
                        mark[ty][tx] = 1;
                    }
                    for (int i2 = 0 ; i2 < n ; i2++) {
                        for (int j2 = 0; j2 < m; j2++) {
                            for (int k2 = 0 ; k2 < possible[i2][j2]; k2++) {
                                boolean isOK = true;
                                for (int d = 0 ; d < 4 ; d++) {
                                    int ty = i2 + DY[d] * k2;
                                    int tx = j2 + DX[d] * k2;
                                    if (mark[ty][tx] == 1) {
                                        isOK = false;
                                        break;
                                    }
                                }
                                if (!isOK) {
                                    break;
                                }
                                max = Math.max(max, (k1 * 4 + 1) * (k2 * 4 + 1));
                            }
                        }
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
