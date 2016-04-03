package codeforces.other2016.indiahacks2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 2016/03/19.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        String[][] op = new String[q][2];
        for (int i = 0; i < q; i++) {
            op[i][0] = in.nextToken();
            op[i][1] = in.nextToken();
        }
        char[] map = new char[36];
        Arrays.fill(map, '~');
        for (int i = 0; i < q ; i++) {
            int one = firstTwo(op[i][0]);
            map[one] = op[i][1].charAt(0);
        }
        dfs(n, "", map);

        out.println(ans);
        out.flush();
    }

    private static void dfs(int n, String s, char[] map) {
        if (s.length() == n) {
            if (isOK(s, map)) {
                ans++;
            }
            return;
        }
        for (char a : "abcdef".toCharArray()) {
            dfs(n, s + a, map);
        }
    }

    private static int firstTwo(String s) {
        return (s.charAt(0)-'a')*6+(s.charAt(1)-'a');
    }

    private static boolean isOK(String s, char[] map) {
        while (s.length() >= 2) {
            int idx = firstTwo(s);
            if (map[idx] == '~') {
                return false;
            }
            s = String.valueOf(map[idx]) + s.substring(2);
        }
        return s.equals("a");
    }

    static int ans = 0;

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
