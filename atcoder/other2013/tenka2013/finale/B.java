package atcoder.other2013.tenka2013.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 4/23/16.
 */
public class B {
    public static int loop(int n) {
        int[] c = new int[n];
        for (int i = 0; i < n ; i++) {
            c[i] = i;
        }
        for (int i = 0; i < n*n ; i++) {
            c = shuffle(c);

            boolean one = true;
            for (int j = 0; j < n ; j++) {
                if (c[j] != j) {
                    one = false;
                    break;
                }
            }
            if (one) {
                return i+1;
            }
        }
        throw new RuntimeException("bang : " + n);
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt() * 2 + 1;
        int m = in.nextInt();
        int[] c = new int[n];
        for (int i = 0; i < n ; i++) {
            c[i] = in.nextInt()-1;
        }
        int[] k = new int[m];
        for (int i = 0; i < m ; i++) {
            k[i] = in.nextInt();
        }

        int best = Integer.MAX_VALUE;
        int shift = -1;
        int need = -1;
        int lp = loop(n);
        for (int shu = 0; shu < n ; shu++) {
            int deg = deg(c);
            if (deg != -1 && shift == -1) {
                shift = shu;
                need = zero(c);
            }
            c = shuffle(c);
        }
        if (shift == -1) {
            out.println(shift);
            out.flush();
            return;
        }

        int[] two = new int[n+1];
        two[0] = 1;
        for (int j = 1; j <= n ; j++) {
            two[j] = two[j-1]*2%n;
        }

        boolean[] next = new boolean[n];
        for (int l = 0 ; l <= shift ; l++) {
            for (int j = 0; j < m; j++) {
                next[k[j]*two[l]%n] = true;
            }
        }

        Queue<Integer> q = new ArrayBlockingQueue<>(2*n);
        for (int i = shift ; i < n ; i += lp) {
            if (i >= best) {
                break;
            }
            int[] dp = new int[n];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            q.clear();
            q.add(0);

            if (i > shift) {
                for (int l = 0 ; l <= i; l++) {
                    for (int j = 0; j < m; j++) {
                        int idx = k[j] * two[l] % n;
                        if (!next[idx]) {
                            next[idx] = true;
                        }
                    }
                }
            }

            while (q.size() >= 1) {
                int now = q.poll();
                int time = dp[now];
                for (int j = 0; j < n ; j++) {
                    if (next[j]) {
                        int to = (now+j)%n;
                        if (dp[to] > time+1) {
                            dp[to] = time+1;
                            q.add(to);
                        }
                    }
                }
            }
            if (dp[need] != Integer.MAX_VALUE) {
                best = Math.min(best, dp[need] + i);
            }
        }
        out.println(best == Integer.MAX_VALUE ? -1 : best);
        out.flush();
    }

    private static int[] shift(int[] c, int k) {
        int[] d = c.clone();
        int n = c.length;
        for (int i = k ; i < k + n ; i++) {
            d[i-k] = c[i%n];
        }
        return d;
    }

    private static int[] shuffle(int[] c) {
        int[] d = c.clone();
        for (int i = 0; i < c.length ; i += 2) {
            d[i] = c[i/2];
        }
        int half = (c.length+1)/2;
        for (int i = 1 ; i < c.length ; i += 2) {
            d[i] = c[i/2+half];
        }
        return d;
    }

    private static int zero(int[] c) {
        int n = c.length;
        for (int i = 0; i < n ; i++) {
            if (c[i] == 0) {
                return i;
            }
        }
        throw new RuntimeException("that cant be");
    }

    private static int deg(int[] c) {
        int n = c.length;
        int i = zero(c);
        for (int j = 0; j < n ; j++) {
            if (c[(i+j)%n] != j) {
                return -1;
            }
        }
        return i;
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
