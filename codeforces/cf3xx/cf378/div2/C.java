package codeforces.cf3xx.cf378.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int k = in.nextInt();
        int[] b = in.nextInts(k);

        if (Arrays.stream(a).sum() != Arrays.stream(b).sum()) {
            out.println("NO");
            out.flush();
            return;
        }

        int doe = 0;
        int[][] ord = new int[n-k][2];

        int head = n-1;
        for (int ki = k-1 ; ki >= 0 ; ki--) {
            int to = head;
            int sum = 0;
            int want = b[ki];
            while (head >= 0 && sum < want) {
                sum += a[head];
                head--;
            }
            if (sum > want) {
                out.println("NO");
                out.flush();
                return;
            }
            if (to - head == 1) {
                continue;
            }

            int max = 0;
            List<Integer> maxIds = new ArrayList<>();
            for (int i = to ; i > head ; i--) {
                if (max < a[i]) {
                    max = a[i];
                    maxIds.clear();
                    maxIds.add(i);
                } else if (max == a[i]) {
                    maxIds.add(i);
                }
            }
            if (to - head >= 2 && maxIds.size() == to - head) {
                out.println("NO");
                out.flush();
                return;
            }

            // (head,to]
            int base = -1;
            int R = -1;
            int L = -1;
            for (int i = 0 ; i < maxIds.size() ; i++) {
                int f = maxIds.get(i);
                if (f+1 <= to && a[f+1] < max) {
                    base = f;
                    ord[doe][0] = f;
                    ord[doe++][1] = 'R';
                    R = f+2;
                    L = f-1;
                    break;
                } else if (f-1 > head && a[f-1] < max) {
                    base = f-1;
                    ord[doe][0] = f;
                    ord[doe++][1] = 'L';
                    R = f+1;
                    L = f-2;
                    break;
                }
            }

            for (int x = R ; x <= to ; x++) {
                ord[doe][0] = base;
                ord[doe++][1] = 'R';
            }
            for (int x = L ; x > head ; x--) {
                ord[doe][0] = base;
                ord[doe++][1] = 'L';
                base--;
            }
        }

        out.println("YES");
        for (int i = 0; i < ord.length; i++) {
            out.print(ord[i][0]+1);
            out.print(' ');
            out.println((char)ord[i][1]);
        }
        out.println();
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
