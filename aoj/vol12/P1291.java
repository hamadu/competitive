package aoj.vol12;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/24.
 */
public class P1291 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            char[][] words = new char[n][];
            for (int i = 0; i < n ; i++) {
                words[i] = in.nextToken().toCharArray();
            }
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < m ; i++) {
                line.append(in.nextToken());
            }
            out.println(solve(words, line.toString().toCharArray()));
        }

        out.flush();
    }

    private static int solve(char[][] w, char[] h) {
        words = w;
        haystack = h;
        n = words.length;
        m = haystack.length;

        for (int i = 0; i < n ; i++) {
            Arrays.fill(match[i], false);
        }
        for (int i = 0; i < n ; i++) {
            int wl = words[i].length;
            for (int j = 0; j+wl <= m ; j++) {
                boolean ok = true;
                for (int k = 0; k < wl ; k++) {
                    if (words[i][k] != haystack[j+k]) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    match[i][j] = true;
                }
            }
        }
        for (int i = 0; i < memo.length ; i++) {
            Arrays.fill(memo[i], 0);
        }
        int cnt = 0;
        for (int i = 0; i < m ; i++) {
            if (dfs(i, 0) == 1) {
                cnt++;
            }
        }
        return cnt;
    }

    static int n;
    static int m;
    static char[][] words;
    static char[] haystack;

    static boolean[][] match = new boolean[12][5010];
    static int[][] memo = new int[5001][1<<12];

    static int dfs(int head, int ptn) {
        if (head > m) {
            return -1;
        }
        if (ptn == (1<<n)-1) {
            return 1;
        }
        if (head == m) {
            return -1;
        }
        if (memo[head][ptn] != 0) {
            return memo[head][ptn];
        }
        int ret = -1;
        for (int i = 0; i < n ; i++) {
            if ((ptn & (1<<i)) == 0 && match[i][head]) {
                if (dfs(head + words[i].length, ptn | (1<<i)) == 1) {
                    ret = 1;
                    break;
                }
            }
        }
        memo[head][ptn] = ret;
        return ret;
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
