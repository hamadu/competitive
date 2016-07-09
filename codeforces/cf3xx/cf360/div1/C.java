package codeforces.cf3xx.cf360.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/07/09.
 */
public class C {


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int[] coin = new int[n];
        for (int i = 0; i < n ; i++) {
            coin[i] = in.nextInt();
        }

        boolean[][][] visited = new boolean[2][k+1][k+1];
        visited[0][0][0] = true;
        for (int ci = 0 ; ci < n ; ci++) {
            int fr = ci % 2;
            int to = 1 - fr;
            int co = coin[ci];
            for (int i = 0; i <= k; i++) {
                for (int j = 0; j <= k; j++) {
                    if (visited[fr][i][j]) {
                        visited[to][i][j] = true;
                        if (i+co <= k) {
                            visited[to][i+co][j] = true;
                            if (j+co <= k) {
                                visited[to][i+co][j+co] = true;
                            }
                        }
                    }
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i <= k; i++) {
            if (visited[n%2][k][i]) {
                ans.add(i);
            }
        }
        output(out, ans);
        out.flush();
    }

    private static void output(PrintWriter out, List<Integer> left) {
        int n = left.size();
        out.println(n);
        for (int i = 0; i < n ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(left.get(i));
        }
        out.println();
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
            return res*sgn;
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
            return res*sgn;
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
