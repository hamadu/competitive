// http://arc033.contest.atcoder.jp/submissions/358259
// 平方分割版
package atcoder.arc.arc033;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C2 {
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

        KthBucket tree = new KthBucket(nums);

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

    static class KthBucket {
        Map<Integer,Integer> imap = new HashMap<>();
        int[] values;

        int bucketSize;
        int[] bucketCell;
        int[] bucketParent;
        KthBucket(int[] sortedBase) {
            values = sortedBase.clone();
            bucketSize = (int)Math.max(100, Math.sqrt(sortedBase.length));
            bucketParent = new int[(values.length + bucketSize - 1) / bucketSize];
            bucketCell = new int[sortedBase.length];
            for (int i = 0 ; i < sortedBase.length ; i++) {
                if (!imap.containsKey(sortedBase[i])) {
                    imap.put(sortedBase[i], i);
                }
            }
        }

        void add(int num) {
            add(num, 1);
        }

        void remove(int num) {
            add(num, -1);
        }

        void add(int num, int k) {
            if (imap.containsKey(num)) {
                int idx = imap.get(num);
                bucketCell[idx] += k;
                bucketParent[idx / bucketSize] += k;
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
            int bn = (values.length + bucketSize - 1) / bucketSize;
            for (int bi = 0 ; bi < bn ; bi++) {
                if (k < bucketParent[bi]) {
                    for (int ci = bi*bucketSize ; ci < Math.min(bucketCell.length, (bi+1)*bucketSize) ; ci++) {
                        if (bucketCell[ci] >= 1) {
                            k -= bucketCell[ci];
                            if (k < 0) {
                                return ci;
                            }
                        }
                    }
                } else {
                    k -= bucketParent[bi];
                }
            }
            return -1;
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



