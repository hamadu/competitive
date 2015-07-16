package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/16.
 */
public class P1336 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int l = in.nextInt();
            if (n + l == 0) {
                break;
            }

            int[][] ants = new int[n][3];
            for (int i = 0; i < n ; i++) {
                ants[i][0] = i;
                ants[i][2] = in.nextToken().charAt(0) == 'L' ? -1 : 1;
                ants[i][1] = in.nextInt();
            }

            int[] timeAndAntID = solve(ants, l);
            out.println(String.format("%d %d", timeAndAntID[0], timeAndAntID[1] + 1));
        }

        out.flush();
    }

    private static int[] solve(int[][] ants, int L) {
        int n = ants.length;
        boolean[] goaled = new boolean[n];
        int goaledCount = 0;



        int time = 0;
        int lastID = -1;
        while (goaledCount < n) {
            time++;

            // move
            for (int i = 0 ; i < n ; i++) {
                if (goaled[i]) {
                    continue;
                }
                ants[i][1] += ants[i][2];
            }

            // flip
            for (int i = 0 ; i < n ; i++) {
                for (int j = i+1; j < n ; j++) {
                    if (!goaled[i] && !goaled[j] && ants[i][1] == ants[j][1]) {
                        ants[i][2] *= -1;
                        ants[j][2] *= -1;
                        break;
                    }
                }
            }

            // goaled from Right
            for (int i = 0 ; i < n ; i++) {
                if (!goaled[i] && ants[i][1] == L) {
                    goaled[i] = true;
                    goaledCount++;
                    lastID = ants[i][0];
                }
            }

            // goaled from Left
            for (int i = 0 ; i < n ; i++) {
                if (!goaled[i] && ants[i][1] == 0) {
                    goaled[i] = true;
                    goaledCount++;
                    lastID = ants[i][0];
                }
            }
        }
        return new int[]{time, lastID};
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
