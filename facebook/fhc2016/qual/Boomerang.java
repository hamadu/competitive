package facebook.fhc2016.qual;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 2016/01/10.
 */
public class Boomerang {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            long[][] stars = new long[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    stars[i][j] = in.nextInt();
                }
            }
            out.println(String.format("Case #%d: %d", c, solve(stars)));
        }
        out.flush();
    }

    private static long solve(long[][] stars) {
        int n = stars.length;
        long total = 0;
        for (int i = 0; i < n ; i++) {
            Map<Long,Long> distanceMap = new HashMap<>();
            for (int j = 0 ; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                long d = distance2(stars[i], stars[j]);
                distanceMap.put(d, distanceMap.getOrDefault(d, 0L)+1);
            }
            for (long v : distanceMap.values()) {
                total += v * (v - 1) / 2;
            }
        }
        return total;
    }

    private static long distance2(long[] starA, long[] starB) {
        long dx = starA[0] - starB[0];
        long dy = starA[1] - starB[1];
        return dx*dx + dy*dy;
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
