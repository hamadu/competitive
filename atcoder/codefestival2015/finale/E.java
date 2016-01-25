package atcoder.codefestival2015.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/11/14.
 */
public class E {
    public static int solve(String s, int in) {
        int out = in;
        char[] c = s.toCharArray();
        for (int i = c.length-1 ; i >= 0 ; i--) {
            if (c[i] == '-') {
                out *= -1;
            } else {
                if (c[i] == '!') {
                    if (out == 0) {
                        out = 1;
                    } else {
                        out = 0;
                    }
                }
            }
        }
        return out;
    }

    static int[] buildTable(String s) {
        int[] table = new int[513];
        for (int i = -256 ; i <= 256 ; i++) {
            table[i+256] = solve(s, i);
        }
        return table;
    }

    static int[] answerTable;
    static String ans = null;

    static void dfs(String now, int limit) {
        if (now.length() >= limit) {
            int[] tbl = buildTable(now);
            boolean isOK = true;
            for (int i = 0; i < tbl.length; i++) {
                if (answerTable[i] != tbl[i]) {
                    isOK = false;
                    break;
                }
            }
            if (isOK) {
                ans = now;
            }
            return;
        }
        dfs(now + "-", limit);
        dfs(now + "!", limit);
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s = in.nextToken();
        answerTable = buildTable(s);

        for (int l = 0; l <= 20; l++) {
            dfs("", l);
            if (ans != null) {
                break;
            }
        }
        out.println(ans);
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
