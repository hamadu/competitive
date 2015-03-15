// http://arc033.contest.atcoder.jp/submissions/358251
// BIT版
package atcoder.arc033;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int q = in.nextInt();
        Set<Integer> num = new HashSet<>();
        int[][] qe = new int[q][2];
        for (int i = 0; i < q; i++) {
            qe[i][0] = in.nextInt();
            qe[i][1] = in.nextInt();
            if (qe[i][0] == 1) {
                num.add(qe[i][1]);
            }
        }
        int[] nums = new int[num.size()];
        int ni = 0;
        for (int i : num) {
            nums[ni++] = i;
        }
        Arrays.sort(nums);

        KthTree tree = new KthTree(nums);

        for (int i = 0; i < q ; i++) {
            if (qe[i][0] == 1) {
                tree.add(qe[i][1]);
            } else {
                int kth = tree.kthElement(qe[i][1]-1);
                tree.remove(kth);
                out.println(kth);
            }
        }

        out.flush();
    }

    static class KthTree {
        Map<Integer,Integer> imap = new HashMap<>();
        int[] values;
        int vidx;
        BIT bit;

        KthTree(int[] sortedBase) {
            values = new int[sortedBase.length+1];
            vidx = 1;
            for (int i = 0 ; i < sortedBase.length ; i++) {
                if (!imap.containsKey(sortedBase[i])) {
                    imap.put(sortedBase[i], vidx);
                    values[vidx++] = sortedBase[i];
                }
            }
            bit = new BIT(vidx);
        }

        void add(int num) {
            add(num, 1);
        }

        void remove(int num) {
            add(num, -1);
        }

        void add(int num, int k) {
            if (imap.containsKey(num)) {
                bit.add(imap.get(num), k);
            }
        }


        int kthElement(int k) {
            int idx = kthIndex(k);
            if (idx == -1) {
                return -1;
            }
            return values[idx];
        }

        int kthIndex(int k) {
            k++;
            int l = -1;
            int r = (int)bit.N+1;
            while (r - l > 1) {
                int med = (r + l) / 2;
                if (bit.sum(med) < k) {
                    l = med;
                } else {
                    r = med;
                }
            }
            if (r == bit.N+1) {
                return -1;
            }
            return r;
        }

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



