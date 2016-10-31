package atcoder.agc.agc006;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);


        int N = in.nextInt();
        int x = in.nextInt();
        if (x == 1 || x == 2*N-1) {
            out.println("No");
        } else {
            out.println("Yes");

            int[] ans = new int[2*N-1];
            boolean[] used = new boolean[2*N];
            if (x == N) {
                for (int i = 1; i <= 2*N-1 ; i++) {
                    ans[i-1] = i;
                }
            } else {
                if (x < N) {
                    ans[N-2] = x+1;
                    ans[N-1] = x-1;
                    ans[N] = x;
                    ans[N+1] = x+2;
                    used[x+1] = used[x-1] = used[x] = used[x+2] = true;
                } else {
                    ans[N-2] = x-2;
                    ans[N-1] = x;
                    ans[N] = x+1;
                    ans[N+1] = x-1;
                    used[x-2] = used[x-1] = used[x] = used[x+1] = true;
                }
            }

            int head = 1;
            for (int i = 0; i < ans.length; i++) {
                if (ans[i] == 0) {
                    while (used[head]) {
                        head++;
                    }
                    ans[i] = head;
                    used[head] = true;
                }
            }
            for (int i = 0; i < ans.length ; i++) {
                out.println(ans[i]);
            }
        }

        out.flush();
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
        Arrays.sort(num, x+1, len);
        return true;
    }



    static int compute(int[] ord) {
        int n = ord.length;
        int[] now = ord.clone();
        while (now.length > 1) {
            int[] next = new int[now.length-2];
            for (int i = 0; i < next.length ; i++) {
                next[i] = now[i] + now[i+1] + now[i+2];
                next[i] -= Math.min(now[i], Math.min(now[i+1], now[i+2]));
                next[i] -= Math.max(now[i], Math.max(now[i+1], now[i+2]));
            }
            now = next;
        }
        return now[0];
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
