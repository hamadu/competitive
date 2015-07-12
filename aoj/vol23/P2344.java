package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/12.
 */
public class P2344 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] lr = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                lr[i][j] = in.nextInt()-1;
            }
        }

        int[] stk = new int[1000010];
        int[] depth = new int[n-1];
        int[] order = new int[n-1];
        int oi = 0;

        int sp = 0;
        stk[sp++] = 0;
        stk[sp++] = 0;
        while (sp >= 1) {
            int dep = stk[--sp];
            int now = stk[--sp];
            depth[now] = dep;
            order[oi++] = now;
            for (int i = 0; i < 2 ; i++) {
                if (lr[now][i] < n-1) {
                    stk[sp++] = lr[now][i];
                    stk[sp++] = dep+1;
                }
            }
        }

        int[] endingQty = new int[n-1];
        int[] depthSum = new int[n-1];
        for (int i = n-2 ; i >= 0 ; i--) {
            int now = order[i];
            for (int j = 0; j < 2 ; j++) {
                if (lr[now][j] == n-1) {
                    endingQty[now]++;
                    depthSum[now]++;
                } else {
                    endingQty[now] += endingQty[lr[now][j]];
                    depthSum[now] += depthSum[lr[now][j]] + endingQty[lr[now][j]];
                }
            }
        }

        int[] dp = new int[n-1];
        for (int i = n-2; i >= 0 ; i--) {
            int now = order[i];
            if (lr[now][0] == n-1 && lr[now][1] == n-1) {
                dp[now] = 2;
            } else if (lr[now][0] == n-1) {
                dp[now] = dp[lr[now][1]] + 2;
            } else if (lr[now][1] == n-1) {
                dp[now] = dp[lr[now][0]] + 2;
            } else {
                if (now >= 1) {
                    int ptn1 = dp[lr[now][0]] + depthSum[lr[now][1]] + endingQty[lr[now][1]];
                    int ptn2 = dp[lr[now][1]] + depthSum[lr[now][0]] + endingQty[lr[now][0]];
                    dp[now] = Math.min(ptn1, ptn2) + 1;
                    dp[now] = Math.min(dp[now], dp[lr[now][0]] + dp[lr[now][1]] + 2 + depth[now]);
                } else {
                    dp[now] = dp[lr[now][0]] + dp[lr[now][1]] + 2;
                }
            }
        }

        out.println(dp[0]);
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
