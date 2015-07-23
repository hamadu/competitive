package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by hama_du on 15/07/22.
 */
public class P1145 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            char[] genom = in.nextToken().toCharArray();
            int idx = in.nextInt();
            if (String.valueOf(genom).equals("0") && idx == 0) {
                break;
            }
            out.println(solve(genom, idx));
        }

        out.flush();
    }

    private static char solve(char[] genom, long idx) {
        G = genom;
        closeP = new int[G.length];
        memo = new long[101][101];
        for (int i = 0; i < 101 ; i++) {
            Arrays.fill(memo[i], -1);
        }
        Stack<Integer> openPos = new Stack<Integer>();
        for (int i = 0; i < G.length ; i++) {
            if (genom[i] == '(') {
                openPos.add(i);
            } else if (genom[i] == ')') {
                int o = openPos.pop();
                closeP[o] = i;
            }
        }
        return dfs(0, idx);
    }

    private static char dfs(int head, long idx) {
        if (idx < 0 || head >= G.length) {
            return '0';
        }
        if ('A' <= G[head] && G[head] <= 'Z') {
            if (idx == 0) {
                return G[head];
            }
            return dfs(head+1, idx-1);
        }
        long loop = 0;
        int nhead = head;
        while ('0' <= G[nhead] && G[nhead] <= '9') {
            loop *= 10;
            loop += G[nhead] - '0';
            nhead++;
        }
        if (G[nhead] == '(') {
            int ntail = closeP[nhead];
            long inner = cnt(nhead+1, ntail);
            if (idx < loop * inner) {
                return dfs(nhead+1, idx % inner);
            }
            return dfs(ntail+1, idx - loop * inner);
        } else {
            if (idx < loop) {
                return G[nhead];
            } else {
                return dfs(nhead+1, idx - loop);
            }
        }
    }

    static char[] G;
    static int[] closeP;
    static long[][] memo;

    static long INF = 10000000000L;

    static long cnt(int head, int tail) {
        if (head >= tail) {
            return 0;
        }
        if (memo[head][tail] != -1) {
            return memo[head][tail];
        }

        long ret = 0;
        if ('A' <= G[head] && G[head] <= 'Z') {
            ret = 1 + cnt(head+1, tail);
        } else {
            long loop = 0;
            int nhead = head;
            while ('0' <= G[nhead] && G[nhead] <= '9') {
                loop *= 10;
                loop += G[nhead] - '0';
                nhead++;
            }
            if (G[nhead] == '(') {
                int ntail = closeP[nhead];
                ret = loop * cnt(nhead+1, ntail) + cnt(ntail+1, tail);
            } else {
                ret = loop + cnt(nhead+1, tail);
            }
        }
        if (ret >= INF) {
            ret = INF;
        }
        memo[head][tail] = ret;
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
