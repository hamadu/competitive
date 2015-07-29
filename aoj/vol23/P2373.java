package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/28.
 */
public class P2373 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] ord = new int[n];
        for (int i = 0; i < n ; i++) {
            ord[i] = in.nextInt();
        }
        Arrays.sort(ord);

        double ans = dfs(0, ord, new int[n]);

        out.println(ans);
        out.flush();
    }

    private static double dfs(int ptn, int[] ord, int[] stk) {
        double max = 0;
        int v = Integer.bitCount(ptn);
        if (v >= 3) {
            int[] nu = Arrays.copyOf(stk, v);
            boolean isOK = true;
            for (int i = 0; i < v ; i++) {
                if (nu[i] > nu[v-1]) {
                   isOK = false;
                }
            }
            if (isOK) {
                max = Math.max(max, solve(nu));
            }
        }
        int n = stk.length;
        for (int i = 0; i < n ; i++) {
            if ((ptn & (1<<i)) == 0) {
                stk[v++] = ord[i];
                max = Math.max(max, dfs(ptn|(1<<i), ord, stk));
                --v;
            }
        }
        return max;
    }

    private static double solve(int[] len) {
        int n = len.length;

        long[] pairs = new long[n];
        for (int i = 0; i < n ; i++) {
            pairs[i] = len[i] * len[(i+1)%n];
        }

        double found = -1;
        double min = 0;
        double max = Math.PI*2;
        for (int cur = 0 ; cur < 50 ; cur++) {
            double med = (max + min) / 2;
            double lcos = Math.cos(med) * pairs[0];
            double sum = med;
            for (int i = 1 ; i < n ; i++) {
                double c = lcos / pairs[i];
                if (c <= -1) {
                    sum = Math.PI * 4;
                    break;
                } else if (c >= 1) {
                    sum = -1;
                    break;
                }
                sum += Math.acos(c);
            }
            if (sum >= 2*Math.PI) {
                max = med;
            } else {
                min = med;
            }
            if (Math.abs(sum - 2*Math.PI) < 1e-8) {
                found = med;
                break;
            }
        }

        if (found == -1) {
            return -1;
        }

        double sum = Math.sin(found) * pairs[0];
        for (int i = 1 ; i < n ; i++) {
            double cos = pairs[0] * Math.cos(found) / pairs[i];
            sum += pairs[i] * Math.sqrt(1 - cos * cos);
        }
        return sum * 0.5;
    }

    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
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
