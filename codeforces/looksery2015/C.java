package codeforces.looksery2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

public class C {

    static int[][][][] stat;

    static int game(int turn, int odd, int even, int left) {
        if (left == 0) {
            if (turn == 0) {
                return (odd % 2 == 1) ? 1 : -1;
            } else {
                return (odd % 2 == 1) ? -1 : 1;
            }
        }
        if (stat[turn][odd][even][left] != 0)  {
            return stat[turn][odd][even][left];
        }
        boolean win = false;
        if (odd >= 1) {
            int d = game(1-turn, odd-1, even, left-1);
            if (d == -1) {
                win = true;
            }
        }
        if (even >= 1) {
            int d = game(1-turn, odd, even-1, left-1);
            if (d == -1) {
                win = true;
            }
        }
        stat[turn][odd][even][left] = win ? 1 : -1;
        return stat[turn][odd][even][left];
    }

    public static void ex() {
        stat = new int[2][100][100][100];
        int left = 11;
        for (int o = 0 ; o <= 16 ; o++) {
            for (int e = 0 ; e <= 16 ; e++) {
                if (o+e < left) {
                    System.out.print('-');
                    continue;
                }
                int g = game(0, o, e, left);
                System.out.print(g == 1 ? 'W' : 'L');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int odd = 0;
        int even = 0;

        for (int i = 0 ; i < n ; i++) {
            int a = in.nextInt();
            if (a % 2 == 1) {
                odd++;
            } else {
                even++;
            }
        }

        boolean win = true;
        int turn = n - k;
        if (turn % 2 == 0) {
            // final turn is opponent
            if (even > turn / 2) {
                win = false;
            } else {
                win = ((odd + even) % 2) == 1;
            }
        } else {
            if (odd <= turn / 2) {
                win = false;
            } else if (even > turn / 2 && odd > turn / 2) {
                win = true;
            } else {
                win = ((odd + even) % 2) == 0;
            }
        }

        if (n == k) {
            win = (odd % 2 == 1);
        } else if (k == 0) {
            win = false;
        }
        out.println(win ? "Stannis" : "Daenerys");
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



