package atcoder.arc045;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/10/10.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] imos = new int[n+1];
        int[][] range = new int[m][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                range[i][j] = in.nextInt();
            }
            range[i][0]--;

            imos[range[i][0]]++;
            imos[range[i][1]]--;
        }

        for (int i = 0; i < n ; i++) {
            imos[i+1] += imos[i];
        }

        SegmentTree seg = new SegmentTree(imos);
        List<Integer> ranges = new ArrayList<>();
        for (int i = 0; i < m ; i++) {
            int l = range[i][0];
            int r = range[i][1];
            if (seg.min(l, r) >= 2) {
                ranges.add(i+1);
            }
        }

        out.println(ranges.size());
        for (int x : ranges) {
            out.println(x);
        }
        out.flush();
    }


    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MAX_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void update(int idx, int value) {
            seg[M+idx] = value;
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                seg[i] = compute(i);
                if (i == 0) {
                    break;
                }
            }
        }

        public int compute(int i) {
            return Math.min(seg[i*2+1], seg[i*2+2]);
        }

        public int min(int l, int r) {
            return min(l, r, 0, 0, M+1);
        }

        public int min(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MAX_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MAX_VALUE;
            ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
            ret = Math.min(ret, min(l, r, idx*2+2, med, to));
            return ret;
        }

        /**
         * L番目以降で、最初にV以下になるインデックスを見つける。O(logn)
         * 見つからない時は -1
         *
         * @param l
         * @param v
         * @return
         */
        public int findLessOrEqualIndexRight(int l, int v) {
            int cur = M + l;
            while (true) {
                if (seg[cur] <= v) {
                    if (cur < M) {
                        cur = cur*2+1;
                    } else {
                        return cur - M;
                    }
                } else {
                    cur += 1;
                    if ((cur & (cur+1)) == 0) {
                        return -1;
                    }
                    if ((cur&1) == 1) {
                        cur >>>= 1;
                    }
                }
            }
        }

        /**
         * L番目以前、最初にV以下になるインデックスを見つける。O(logn)
         * 見つからない時は -1
         *
         * @param l
         * @param v
         * @return
         */
        public int findLessOrEqualIndexLeft(int l, int v) {
            int cur = M + l;
            while (true) {
                if (seg[cur] <= v) {
                    if (cur < M) {
                        cur = cur*2+2;
                    } else {
                        return cur - M;
                    }
                } else {
                    if ((cur & (cur+1)) == 0) {
                        return -1;
                    }
                    cur--;
                    if ((cur&1)==0) {
                        cur >>>= 1;
                    }
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
