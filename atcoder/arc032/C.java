package atcoder.arc032;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    static class SegmentTree {
        int N;
        int[] dataMax;
        SegmentTree(int n) {
            N = 1;
            while (N <= n) {
                N *= 2;
            }
            dataMax = new int[N*2+2];
            Arrays.fill(dataMax, 0);
        }

        void update(int i, int a) {
            i--;
            dataMax[N+i] = a;
            int now = (N+i) >> 1;
            while (true) {
                dataMax[now] = Math.max(dataMax[now*2], dataMax[now*2+1]);
                if (now == 1) {
                    break;
                }
                now >>= 1;
            }
        }


        int getMax(int idx) {
            idx--;
            return dataMax[N+idx];
        }

        int rangeMax(int l, int r) {
            return rangeMax(l, r, 1, 1, N);
        }

        int rangeMax(int l, int r, int idx, int il, int ir) {
            if (l <= il && ir <= r) {
                return dataMax[idx];
            }
            if (r < il || ir < l) {
                return Integer.MIN_VALUE;
            }
            int nextD = (ir - il + 1) >> 1;
            return Math.max(rangeMax(l, r, idx<<1, il, il+nextD-1), rangeMax(l, r, (idx<<1)+1, il+nextD, ir));
        }
    }


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] work = new int[n][3];
        for (int i = 0; i < n; i++) {
            work[i][0] = i;
            work[i][1] = in.nextInt()+1;
            work[i][2] = in.nextInt()+1;
        }

        Arrays.sort(work, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        int cnt = 0;
        int now = 0;
        for (int i = 0 ; i < n ; i++) {
            if (now <= work[i][1]) {
                now = work[i][2];
                cnt++;
            }
        }

        int[][] right = new int[n][2];
        SegmentTree tree = new SegmentTree(1000010);
        for (int i = n-1 ; i >= 0 ; i--) {
            int ma = tree.rangeMax(work[i][2], 1000010) + 1;
            if (tree.dataMax[tree.N+work[i][1]-1] < ma) {
                tree.update(work[i][1], ma);
            }
            right[i][0] = ma;
            right[i][1] = work[i][0];
        }

        Arrays.sort(right, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o2[0] - o1[0];
            }
        });
        Arrays.sort(work, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<Integer> ans = new ArrayList<>();
        now = 0;
        int find = cnt;
        for (int i = 0 ; i < n ; i++) {
            if (right[i][0] == find) {
                int idx = right[i][1];
                if (now <= work[idx][1]) {
                    ans.add(idx+1);
                    now = work[idx][2];
                    find--;
                }
            }
        }


        StringBuilder b = new StringBuilder();
        for (int a : ans) {
            b.append(' ').append(a);
        }

        System.out.println(ans.size());
        System.out.println(b.substring(1));

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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



