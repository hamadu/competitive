package aoj.vol21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/21.
 */
public class P2162 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] planets = new int[n][];
            int[] offset = new int[n];
            for (int i = 0; i < n; i++) {
                planets[i] = new int[in.nextInt()];
                offset[i] = in.nextInt();
                for (int j = 0; j < planets[i].length ; j++) {
                    planets[i][j] = in.nextInt();
                }
            }
            out.println(solve(planets, offset));
        }

        out.flush();
    }

    private static long solve(int[][] planets, int[] offset) {
        int n = planets.length;

        long[][] days = new long[25][];
        for (int i = 1; i <= 24 ; i++) {
            days[i] = new long[i];
        }
        for (int i = 0; i < n ; i++) {
            int cycle = planets[i].length;
            for (int j = 0; j < cycle ; j++) {
                days[cycle][j] += planets[i][(offset[i]+j)%cycle];
            }
        }

        boolean[] ignore = new boolean[25];
        ignore[13] = ignore[17] = ignore[19] = ignore[23] = true;

        int lcm = 1;
        for (int i = 1 ; i <= 24 ; i++) {
            if (ignore[i]) {
                continue;
            }
            lcm = lcm * i / gcd(lcm, i);
        }

        long max = 0;
        for (int i = 0 ; i < lcm ; i++) {
            long entry = 0;
            for (int d = 1; d <= 24; d++) {
                if (ignore[d]) {
                    continue;
                }
                entry += days[d][i%d];
            }
            max = Math.max(max, entry);
        }

        for (int d = 1; d <= 24; d++) {
            if (ignore[d]) {
                long mv = 0;
                for (int i = 0; i < d ; i++) {
                    mv = Math.max(mv, days[d][i]);
                }
                max += mv;
            }
        }
        return max;
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
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
