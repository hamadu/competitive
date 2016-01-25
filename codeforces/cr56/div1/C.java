package codeforces.cr56.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/09/07.
 */
public class C {
    private static final long INF = 1145141919L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long[][] gcd = new long[n][n];
        long[][] lcm = new long[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(gcd[i], -1);
            Arrays.fill(lcm[i], -1);
        }
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            long g = in.nextInt();
            long l = in.nextInt();
            gcd[a][b] = gcd[b][a] = g;
            lcm[a][b] = lcm[b][a] = l;
        }

        Optional<long[]> result = doit(gcd, lcm);
        if (result.isPresent()) {
            out.println("YES");
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n ; i++) {
                line.append(' ').append(result.get()[i]);
            }
            out.println(line.substring(1));
        } else {
            out.println("NO");
        }


        out.flush();
    }

    static Optional<long[]> doit(long[][] gcd, long[][] lcm) {
        int n = gcd.length;
        long[] num = new long[n];
        Arrays.fill(num, 1);
        for (int i = 0; i < n  ; i++) {
            for (int j = 0; j < n ; j++) {
                if (gcd[i][j] == -1) {
                    continue;
                }
                long g = gcd(num[i], gcd[i][j]);
                num[i] *= gcd[i][j];
                num[i] /= g;
                if (g > INF) {
                    return Optional.empty();
                }
            }
        }

        boolean[] ok = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (ok[i]) {
                continue;
            }
            long minOP = INF;
            for (int j = 0; j < n ; j++) {
                if (lcm[i][j] != -1) {
                    minOP = Math.min(minOP, lcm[i][j]);
                }
            }
            if (minOP == INF) {
                ok[i] = true;
            } else {
                long needMul = minOP / num[i];
                for (long k = 1 ; k * k <= needMul ; k++) {
                    if (needMul % k != 0) {
                        continue;
                    }
                    long[] t1 = num.clone();
                    boolean[] v1 = new boolean[n];
                    t1[i] *= k;

                    long[] t2 = num.clone();
                    boolean[] v2 = new boolean[n];
                    t2[i] *= needMul / k;
                    if (tryit(t1, v1, i, gcd, lcm)) {
                        num = t1;
                        ok = v1;
                        break;
                    }
                    if (tryit(t2, v2, i, gcd, lcm)) {
                        num = t2;
                        ok = v2;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (gcd[i][j] == -1) {
                    continue;
                }
                long G = gcd(num[i], num[j]);
                long L = num[i] * num[j] / G;
                if (gcd[i][j] != G || lcm[i][j] != L) {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(num);
    }

    private static boolean tryit(long[] num, boolean[] visited, int idx, long[][] gcd, long[][] lcm) {
        int n = num.length;

        dfs(idx, visited, num, gcd, lcm);

        for (int i = 0; i < n ; i++) {
            if (num[i] == 0) {
                return false;
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (!visited[i] || !visited[j] || gcd[i][j] == -1) {
                    continue;
                }
                long G = gcd(num[i], num[j]);
                long L = num[i] * num[j] / G;
                if (gcd[i][j] != G || lcm[i][j] != L) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void dfs(int idx, boolean[] visited, long[] num, long[][] gcd, long[][] lcm) {
        if (visited[idx] || num[idx] == 0) {
            return;
        }
        visited[idx] = true;
        for (int i = 0; i < num.length ; i++) {
            if (gcd[idx][i] == -1 || visited[i]) {
                continue;
            }
            num[i] = gcd[idx][i] * lcm[idx][i] / num[idx];
            dfs(i, visited, num, gcd, lcm);
        }
    }

    static long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a%b);
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
