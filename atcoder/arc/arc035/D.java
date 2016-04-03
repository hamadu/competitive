// http://arc035.contest.atcoder.jp/submissions/352724
package atcoder.arc.arc035;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {
    public static void main(String[] args) {
        double[] sum = new double[2000010];
        System.err.println(Math.log(0));
        for (int i = 1 ; i < sum.length ; i++) {
            sum[i] = sum[i-1] + Math.log(i);
        }

        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        int[][] p = new int[n][2];
        for (int i = 0; i < n; i++) {
            p[i][0] = in.nextInt();
            p[i][1] = in.nextInt();
        }

        BIT bit = new BIT(n+10);
        for (int i = 1 ; i < n ; i++) {
            int dx = p[i][0] - p[i-1][0];
            int dy = p[i][1] - p[i-1][1];
            bit.add(i, sum[dx+dy] - sum[dx] - sum[dy]);
        }


        int q = in.nextInt();
        while (--q >= 0) {
            int t = in.nextInt();
            if (t == 1) {
                int k = in.nextInt()-1;
                int a = in.nextInt();
                int b = in.nextInt();
                for (int sg = -1 ; sg <= 1 ; sg += 2) {
                    if (k >= 1) {
                        int bdx = p[k][0] - p[k-1][0];
                        int bdy = p[k][1] - p[k-1][1];
                        double ptnlog = sum[bdx + bdy] - sum[bdx] - sum[bdy];
                        bit.add(k, ptnlog * sg);
                    }
                    if (k < n-1) {
                        int bdx = p[k+1][0] - p[k][0];
                        int bdy = p[k+1][1] - p[k][1];
                        double ptnlog = sum[bdx + bdy] - sum[bdx] - sum[bdy];
                        bit.add(k+1, ptnlog * sg);
                    }
                    p[k][0] = a;
                    p[k][1] = b;
                }
            } else {
                int l1 = in.nextInt()-1;
                int r1 = in.nextInt()-1;
                int l2 = in.nextInt()-1;
                int r2 = in.nextInt()-1;

                double p1 = bit.range(l1+1, r1);
                double p2 = bit.range(l2+1, r2);
                if (p1 > p2) {
                    out.println("FIRST");
                } else {
                    out.println("SECOND");
                }
            }
        }

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


    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        double[] data;
        BIT(int n) {
            N = n;
            data = new double[n+1];
        }

        double sum(int i) {
            double s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        double range(int i, int j) {
            if (i > j) {
                return 0.0d;
            }
            return sum(j) - sum(i-1);
        }

        void add(int i, double x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



