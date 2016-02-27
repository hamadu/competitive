package atcoder.other2015.codefestival2015.qualb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 15/10/25.
 */
public class D {

    private static final long INF = (long) (1e18);

    static class Range implements Comparable<Range> {
        long fr;
        long to;

        Range(long f, long t) {
            fr = f;
            to = t;
        }

        @Override
        public int compareTo(Range o) {
            if (fr == o.fr) {
                return Long.compare(to, o.to);
            }
            return Long.compare(fr, o.fr);
        }

        @Override
        public String toString() {
            return fr + "-" + to;
        }
    }
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        TreeSet<Range> painted = new TreeSet<>();

        for (int i = 0; i < n ; i++) {
            long pos = in.nextInt();
            long len = in.nextInt() - 1;
            

            long now = pos;
            long left = pos;
            while (true) {
                Range l = painted.floor(new Range(now, INF));
                if (l != null && l.fr <= now && now <= l.to) {
                    left = Math.min(left, l.fr);
                    painted.remove(l);
                    now = l.to+1;
                } else {
                    break;
                }
            }

            while (true) {
                Range r = painted.ceiling(new Range(now, -1));
                if (r == null) {
                    now += len;
                    break;
                } else {
                    long d = r.fr - now;
                    if (len < d) {
                        now += len;
                        break;
                    } else {
                        len -= d;
                        now = r.to+1;
                        painted.remove(r);
                    }
                }
            }
            painted.add(new Range(left, now));
            out.println(now);
        }
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
