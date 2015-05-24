package codeforces.cr277.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/24.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt() - 1;

        String line = in.nextToken();
        if (m >= n / 2) {
            line = new StringBuilder(line).reverse().toString();
            m = n - m - 1;
        }
        char[] c = line.toCharArray();

        int ptnA = solveA(m, c);
        int ptnB = solveB(m, c);
        int ptnC = solveC(m, c);

        out.println(Math.min(ptnA, Math.min(ptnB, ptnC)));
        out.flush();
    }

    private static int solveA(int m, char[] c) {
        int n = c.length;
        int sum = 0;
        boolean[] done = new boolean[n];

        int lastChange = m;
        for (int k = m ; k < n ; k++) {
            int diff = Math.abs(c[k] - c[n-k-1]);
            if (!done[k] && !done[n-k-1] && diff >= 1) {
                sum += Math.min(diff, 26 - diff);
                done[k] = true;
                done[n-k-1] = true;
                lastChange = k;
            }
        }
        sum += Math.abs(lastChange - m);
        return sum;
    }

    private static int solveB(int m, char[] c) {
        int n = c.length;
        int sum = 0;
        boolean[] done = new boolean[n];

        int lastChange = m;
        for (int k = m ; k >= 0 ; k--) {
            int diff = Math.abs(c[k] - c[n-k-1]);
            if (!done[k] && !done[n-k-1] && diff >= 1) {
                sum += Math.min(diff, 26 - diff);
                done[k] = true;
                done[n-k-1] = true;
                lastChange = k;
            }
        }
        sum += Math.abs(lastChange - m);

        int now = lastChange;
        for (int k = now ; k < n / 2 ; k++) {
            int diff = Math.abs(c[k] - c[n-k-1]);
            if (!done[k] && !done[n-k-1] && diff >= 1) {
                sum += Math.min(diff, 26 - diff);
                done[k] = true;
                done[n-k-1] = true;
                lastChange = k;
            }
        }
        sum += Math.abs(lastChange - now);
        return sum;
    }


    private static int solveC(int m, char[] c) {
        int n = c.length;
        int sum = 0;
        boolean[] done = new boolean[n];

        int lastChange = m;
        for (int k = m ; k < n / 2 ; k++) {
            int diff = Math.abs(c[k] - c[n-k-1]);
            if (!done[k] && !done[n-k-1] && diff >= 1) {
                sum += Math.min(diff, 26 - diff);
                done[k] = true;
                done[n-k-1] = true;
                lastChange = k;
            }
        }
        sum += Math.abs(lastChange - m);

        int now = lastChange;
        for (int k = now ; k >= 0 ; k--) {
            int diff = Math.abs(c[k] - c[n-k-1]);
            if (!done[k] && !done[n-k-1] && diff >= 1) {
                sum += Math.min(diff, 26 - diff);
                done[k] = true;
                done[n-k-1] = true;
                lastChange = k;
            }
        }
        sum += Math.abs(lastChange - now);

        return sum;
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
