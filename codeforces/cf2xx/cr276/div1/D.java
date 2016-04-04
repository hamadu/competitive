package codeforces.cf2xx.cr276.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by dhamada on 15/05/20.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int[] f = new int[n];

        List<Segment> segs = new ArrayList<>();

        long dir = 0;
        int first = 0;
        for (int i = 1 ; i < n ; i++) {
            long x = a[i] - a[i-1];
            if (x == 0) {
                continue;
            }
            long d = x / Math.abs(x);
            if (dir != 0 && dir != d) {
                segs.add(new Segment(first, i-1));
                first = i-1;
                dir *= -1;
            } else if (dir == 0) {
                dir = d;
            }
        }
        if (first < n-1) {
            segs.add(new Segment(first, n-1));
        }

        int sn = segs.size();
        long[][] dp = new long[2][sn+1];
        for (int i = 0; i < 2 ; i++) {
            Arrays.fill(dp[i], Long.MIN_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < sn ; i++) {
            Segment seg = segs.get(i);
            for (int j = 0 ; j < 2; j++) {
                if (dp[j][i] == Long.MIN_VALUE) {
                    continue;
                }
                long d = dp[j][i];
                if (j == 0) {
                    dp[0][i+1] = Math.max(dp[0][i + 1], d + Math.abs(a[seg.last - 1] - a[seg.first]));
                    dp[1][i+1] = Math.max(dp[1][i + 1], d + Math.abs(a[seg.last] - a[seg.first]));
                } else {
                    if (seg.first+1 <= seg.last-1) {
                        dp[0][i + 1] = Math.max(dp[0][i + 1], d + Math.abs(a[seg.last - 1] - a[seg.first + 1]));
                    } else {
                        dp[0][i + 1] = Math.max(dp[0][i + 1], d);
                    }
                    dp[1][i+1] = Math.max(dp[1][i + 1], d + Math.abs(a[seg.last] - a[seg.first+1]));
                }
            }
        }

//        for (Segment seg : segs) {
//            debug(seg.first,seg.last);
//        }
//        debug(dp);

        if (sn == 0) {
            out.println(0);
        } else {
            out.println(dp[1][sn]);
        }
        out.flush();
    }


    static class Segment {
        int first;
        int last;

        Segment(int f, int l) {
            first = f;
            last = l;
        }
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
