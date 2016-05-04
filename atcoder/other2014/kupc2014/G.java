package atcoder.other2014.kupc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 5/2/16.
 */
public class G {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int d = in.nextInt();

        String ptn = generate(generate("0", d) + generate("1", d) + generate("10", d), n);
        out.println(ptn);
        out.flush();

        boolean large = solve(in, out, d);
        finalize(in, out, large);
    }

    private static boolean solve(InputReader in, PrintWriter out, int d) {
        int k0 = in.nextInt();
        int u0 = in.nextInt();
        int k1 = move(in, out, 'A', 1)[0];
        int k2 = move(in, out, 'A', 1)[0];
        int u1 = move(in, out, 'B', 1)[1];
        int u2 = move(in, out, 'B', 1)[1];
        int[] map = {
                1, 1,  3, 1, 3, 3, 2, 2
        };
        int kpos = map[(k0<<2)|(k1<<1)|k2];
        int upos = map[(u0<<2)|(u1<<1)|u2];
        if (kpos == 2 && upos == 1) {
            return true;
        }
        if (kpos == 3 && upos == 2) {
            return true;
        }
        if (kpos == 1 && upos == 3) {
            return true;
        }
        return false;
    }

    private static int[] move(InputReader in, PrintWriter out, char ch, int dir) {
        out.println(String.format("Move(%c,%d)", ch, dir));
        out.flush();
        int k = in.nextInt();
        int u = in.nextInt();
        return new int[]{k, u};
    }

    private static void finalize(InputReader in, PrintWriter out, boolean flg) {
        out.println(flg ? "i>j" : "i<j");
        out.flush();
    }

    private static String generate(String s, int n) {
        char[] ret = new char[n];
        int l = s.length();
        for (int i = 0; i < n ; i++) {
            ret[i] = s.charAt(i % l);
        }
        return String.valueOf(ret);
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
