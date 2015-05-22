package codeforces.cr190.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/21.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long x = in.nextInt();
        long y = in.nextInt();
        char[] c = in.nextToken().toCharArray();

        out.println(solve(x, y, c) ? "Yes" : "No");
        out.flush();
    }

    private static boolean solve(long x, long y, char[] c) {
        if (x == 0 && y == 0) {
            return true;
        }
        int[] dx = new int[256];
        int[] dy = new int[256];
        dx['L'] = -1;
        dx['R'] = 1;
        dy['D'] = -1;
        dy['U'] = 1;

        int nx = 0;
        int ny = 0;
        for (int i = 0 ; i < c.length ; i++) {
            nx += dx[c[i]];
            ny += dy[c[i]];
            if (nx == x && ny == y) {
                return true;
            }
        }
        if (nx == 0 && ny == 0) {
            return false;
        }

        long min = 0;
        long max = 1000000000L;
        while (Math.abs(max - min) > 3) {
            long med1 = (max + min * 2) / 3;
            long med2 = (max * 2 + min) / 3;
            BigInteger d1 = compute(x, y, nx, ny, med1);
            BigInteger d2 = compute(x, y, nx, ny, med2);
            if (d1.compareTo(d2) < 0) {
                max = med2;
            } else {
                min = med1;
            }
        }

        long time = max * c.length;
        long sx = max * nx;
        long sy = max * ny;

        // forward
        if (find(time, time+100000, sx - x, sy - y, dx, dy, c)) {
            return true;
        }

        // backward
        if (find(time, Math.max(0, time-100000), sx - x, sy - y, dx, dy, c)) {
            return true;
        }

        return false;
    }

    private static boolean find(long time, long toTime, long x, long y, int[] dx, int[] dy, char[] c) {
        if (x == 0 && y == 0) {
            return true;
        }
        int dt = (int)((toTime - time) / Math.abs(toTime - time));

        int n = c.length;
        int cur = 0;
        while (time != toTime) {
            if (dt >= 1) {
                x += dx[c[cur]];
                y += dy[c[cur]];
            } else {
                int bc = (cur-1+n)%n;
                x -= dx[c[bc]];
                y -= dy[c[bc]];
            }
            if (x == 0 && y == 0) {
                return true;
            }
            time += dt;
            cur = (cur + dt) % n;
        }
        return false;
    }

    static BigInteger compute(long x, long y, long nx, long ny, long time) {
        long tx = nx * time;
        long ty = ny * time;
        BigInteger dx = BigInteger.valueOf(tx - x);
        BigInteger dy = BigInteger.valueOf(ty - y);
        dx = dx.multiply(dx);
        dy = dy.multiply(dy);
        return dx.add(dy);
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
