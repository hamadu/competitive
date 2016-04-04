package codeforces.cf3xx.cr326.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/10/17.
 */
public class B {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long l = in.nextLong();
        int k = in.nextInt();

        int[] a = new int[n+1];
        int[] orig = new int[n];

        a[0] = -1;
        for (int i = 0; i < n; i++) {
            a[i+1] = in.nextInt();
        }
        for (int i = 0; i < n ; i++) {
            orig[i] = a[i+1];
        }

        for (int i = 0; i <= n ; i++) {
            int tm = (int)(Math.random() * i);
            int tmp = a[i];
            a[i] = a[tm];
            a[tm] = tmp;
        }

        Arrays.sort(a);

        Map<Integer,Integer> vmap = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            vmap.put(a[i], i);
        }
        int[] itoi = new int[n];
        for (int i = 0; i < n ; i++) {
            itoi[i] = vmap.get(orig[i]);
        }

        long ans = 0;
        long[][] dp = new long[2][n+1];
        dp[0][0] = 1;
        for (int len = 1; len <= k ; len++) {
            int ti = len % 2;
            int fi = 1 - ti;
            Arrays.fill(dp[ti], 0);

            long prev = 0;
            int fr = 0;
            for (int j = 1; j <= n; j++) {
                while (fr <= n && a[fr] <= a[j]) {
                    prev += dp[fi][fr];
                    fr++;
                }
                dp[ti][j] = prev % MOD;
            }

            long sum = 0;
            for (int i = 1 ; i <= n ; i++) {
                sum += dp[ti][i];
            }
            sum %= MOD;
            long all = l / n;
            if (len <= all) {
                ans += (sum * ((all-len+1) % MOD)) % MOD;
            }
            if (len <= all+1) {
                for (int i = 0; i < l % n; i++) {
                    int v = orig[i];
                    ans += dp[ti][vmap.get(v)];
                }
            }
            ans %= MOD;
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
