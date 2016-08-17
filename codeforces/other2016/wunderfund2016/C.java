package codeforces.other2016.wunderfund2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/30.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] p = new long[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                p[i][j] = in.nextInt();
            }
            p[i][2] = i;
        }
        for (int i = 0; i < n ; i++) {
            int t = (int)(Math.random() * n);
            for (int j = 0; j < 3 ; j++) {
                long tmp = p[i][j];
                p[i][j] = p[t][j];
                p[t][j] = tmp;
            }
        }

        Arrays.sort(p, (o1, o2) -> (o1[0] == o2[0]) ? Long.compare(o1[1], o2[1]) : Long.compare(o1[0], o2[0]));
        long[] ret = solve(p);
        out.println(String.format("%d %d %d", ret[0], ret[1], ret[2]));
        out.flush();
    }

    static long[] solve(long[][] p) {
        int n = p.length;
        Map<Long,List<Long>> deg = new HashMap<>();
        List<Long> xs = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            if (!deg.containsKey(p[i][0])) {
                deg.put(p[i][0], new ArrayList<>());
                xs.add(p[i][0]);
            }
            deg.get(p[i][0]).add(p[i][2]);
        }

        for (int i = 0 ; i < xs.size() ; i++) {
            long x = xs.get(i);
            if (deg.get(x).size() >= 2) {
                long a = deg.get(x).get(0);
                long b = deg.get(x).get(1);
                long c = -1;
                if (i-1 >= 0) {
                    long px = xs.get(i-1);
                    c = deg.get(px).get(0);
                } else if (i+1 < xs.size()) {
                    long px = xs.get(i+1);
                    c = deg.get(px).get(0);
                }
                return new long[]{a+1, b+1, c+1};
            }
        }

        for (int i = 0 ; i + 2 < n ; i++) {
            if (isOK(p[i], p[i+1], p[i+2])) {
                return new long[]{p[i][2]+1, p[i+1][2]+1, p[i+2][2]+1};
            }
        }
        throw new RuntimeException("arien");
    }

    private static boolean isOK(long[] p1, long[] p2, long[] p3) {
        long dx1 = p2[0] - p1[0];
        long dy1 = p2[1] - p1[1];
        long dx2 = p3[0] - p1[0];
        long dy2 = p3[1] - p1[1];
        return (dy1 * dx2 - dx1 * dy2) != 0;
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
