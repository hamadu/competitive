package facebook.fhc2016.round2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/01/10.
 */
public class Decoration {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            char[] c1 = in.nextToken().toCharArray();
            char[] c2 = in.nextToken().toCharArray();
            out.println(String.format("Case #%d: %d", c, solve(c1, c2)));
        }
        out.flush();
    }

    private static char[] reverse(char[] c1) {
        int n = c1.length;
        char[] k = new char[n];
        for (int i = 0; i < n ; i++) {
            k[i] = c1[n-1-i];
        }
        return k;
    }

    private static int[] reverse(int[] c1) {
        int n = c1.length;
        int[] k = new int[n];
        for (int i = 0; i < n ; i++) {
            k[i] = c1[n-1-i];
        }
        return k;
    }

    private static int solve(char[] c1, char[] c2) {
        int n = c1.length;


        int[] dp1 = solveSub(c1, c2);
        int[] dp2 = reverse(solveSub(reverse(c1), reverse(c2)));

        int best = c1.length*10;
        for (int i = 0; i < n ; i++) {
            best = Math.min(best, Math.max(dp1[i], dp2[i]));
        }
        return best;
    }

    private static int[] solveSub(char[] c1, char[] c2) {
        int n = c1.length;
        int[][] dp = new int[26][n];
        for (int i = 0 ; i < n ; i++) {
            if (i == 0) {
                for (int k = 0; k < 26; k++) {
                    dp[k][i] = (c2[0] - 'A') == k ? 0 : 1;
                }
            } else {
                for (int k = 0; k < 26; k++) {
                    if (c2[i] - 'A' == k) {
                        dp[k][i] = dp[k][i-1];
                    } else {
                        dp[k][i] = dp[c2[i]-'A'][i-1] + 1;
                    }
                }
            }
        }


        int[] ret = new int[n+1];
        for (int i = 0; i < n ; i++) {
            if (c1[i] == c2[i]) {
                ret[i+1] = ret[i];
            } else {
                ret[i+1] = dp[c1[i]-'A'][i];
            }
        }
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
