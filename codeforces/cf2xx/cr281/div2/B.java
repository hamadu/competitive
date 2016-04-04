package codeforces.cf2xx.cr281.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/21.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int last = 0;
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        int n = in.nextInt();
        for (int i = 0 ; i < n ; i++) {
            int a = in.nextInt();
            if (a > 0) {
                first.add(a);
                last = 1;
            } else {
                second.add(-a);
                last = -1;
            }
        }

        long firstSum = sum(first);
        long secondSum = sum(second);
        boolean win;
        if (firstSum != secondSum) {
            win = (firstSum > secondSum);
        } else {
            int c = compare(first, second);
            if (c != 0) {
                win = c == 1;
            } else {
                win = last == 1;
            }
        }

        out.println(win ? "first" : "second");
        out.flush();
    }

    private static int compare(List<Integer> a, List<Integer> b) {
        int n = Math.min(a.size(), b.size());
        for (int i = 0; i < n ; i++) {
            int ai = a.get(i);
            int bi = b.get(i);
            if (ai > bi) {
                return 1;
            } else if (bi > ai) {
                return -1;
            }
        }
        if (a.size() == b.size()) {
            return 0;
        }
        return a.size() > b.size() ? 1 : -1;
    }

    static long sum(List<Integer> i) {
        long a = 0;
        for (int ii : i) {
            a += ii;
        }
        return a;
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
