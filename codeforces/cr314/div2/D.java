package codeforces.cr314.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 15/08/10.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int a = in.nextInt();

        int m = in.nextInt();
        TreeSet<Range> tree = new TreeSet<>();
        tree.add(new Range(1, n));

        int current = (n + 1) / (a + 1);
        int ans = -1;
        for (int i = 0; i < m ; i++) {
            int x = in.nextInt();
            Range r = tree.floor(new Range(x, x));
            if (r == null) {
                continue;
            }
            if (x < r.fr || r.to < x) {
               continue;
            }
            tree.remove(r);
            current -= (r.to - r.fr + 1 + 1) / (a + 1);

            int left = (x-1)-r.fr+1;
            int right = r.to-(x+1)+1;
            if (left >= a) {
                current += (left + 1) / (a + 1);
                tree.add(new Range(r.fr, x-1));
            }
            if (right >= a) {
                current += (right + 1) / (a + 1);
                tree.add(new Range(x+1, r.to));
            }
            if (current < k) {
                ans = i+1;
                break;
            }
        }
        out.println(ans);
        out.flush();
    }

    // [fr,to]
    static class Range implements Comparable<Range> {
        int fr;
        int to;

        Range(int a, int b) {
            fr = a;
            to = b;
        }

        @Override
        public int compareTo(Range o) {
            return fr - o.fr;
        }

        public String toString() {
            return String.format("%d-%d", fr, to);
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
