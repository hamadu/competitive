// http://arc031.contest.atcoder.jp/submissions/363031
package atcoder.arc.arc031;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] b = new int[n][2];
        for (int i = 0; i < n; i++) {
            b[i][0] = in.nextInt();
            b[i][1] = i+1;
        }
        Arrays.sort(b, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        BITRange left = new BITRange(n+10);
        BITRange right = new BITRange(n+10);

        long ans = 0;
        for (int i = 0 ; i < n ; i++) {
            int idx = b[i][1];
            long l = Math.max(0, (idx - 1) - left.get(idx));
            long r = Math.max(0, (n - idx) - right.get(idx));
            if (l < r) {
                ans += l;
            } else {
                ans += r;
            }
            left.addRange(idx + 1, n, 1L);
            right.addRange(1, idx - 1, 1L);
        }

        out.println(ans);
        out.flush();
    }

    static class BITRange {
        BIT bit0;
        BIT bit1;
        BITRange(int n) {
            bit0 = new BIT(n);
            bit1 = new BIT(n);
        }

        void addRange(int l, int r, long x) {
            bit0.add(l, -x * (l-1));
            bit1.add(l, x);
            bit0.add(r+1, x * r);
            bit1.add(r+1, -x);
        }

        long range(int l, int r) {
            long right = bit0.sum(r) + bit1.sum(r) * r;
            long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
            return right - left;
        }

        long get(int i) {
            return range(i, i);
        }
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }

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



