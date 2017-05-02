package csacademy.round026;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class CriticalCells {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int k = in.nextInt();
        k++;
        int[][] cells = new int[k][3];
        for (int i = 0; i < k-1; i++) {
            cells[i][1] = in.nextInt();
            cells[i][0] = in.nextInt();
        }
        cells[k-1][1] = n;
        cells[k-1][0] = m;

        Arrays.sort(cells, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);
        List<Integer> rowSet = new ArrayList<>();
        for (int i = 0; i < k ; i++) {
            rowSet.add(cells[i][1]);
        }
        rowSet.add(1);
        Collections.sort(rowSet);
        Map<Integer,Integer> mapToY = new HashMap<>();
        for (int i = 0; i < rowSet.size() ;) {
            int j = i;
            while (j < rowSet.size() && rowSet.get(i) - rowSet.get(j) == 0) {
                j++;
            }
            mapToY.put(rowSet.get(i), mapToY.size());
            i = j;
        }

        // debug(mapToY);

        for (int i = 0; i < k ; i++) {
            cells[i][1] = mapToY.get(cells[i][1]);
        }
        int yn = mapToY.size()+5;
        SegmentTreePURMQ seg = new SegmentTreePURMQ(new int[yn]);
        for (int i = 0 ; i < k ; i++) {
            cells[i][2] = seg.max(0, cells[i][1]+1)+1;
            int old = seg.max(cells[i][1], cells[i][1]+1);
            if (old < cells[i][2]) {
                seg.update(cells[i][1], cells[i][2]);
            }
        }

        int max = cells[k-1][2];
        List<int[]>[] dpmap = new List[max+1];
        for (int i = 0; i <= max; i++) {
            dpmap[i] = new ArrayList<>();
        }
        for (int i = 0; i < k ; i++) {
            dpmap[cells[i][2]].add(cells[i]);
        }

        TreeSet<Integer> tree = new TreeSet<>();

        int ans = 0;
        dpmap[max].get(0)[2] = -1;

        for (int v = max-1 ; v >= 1 ; v--) {
            tree.clear();
            int fv = v+1;
            int from = dpmap[v].size()-1;
            int to = dpmap[fv].size()-1;

            int importantCount = 0;
            while (from >= 0 || to >= 0) {
                boolean nextIsFrom = false;
                if (from >= 0 && to <= -1) {
                    nextIsFrom = true;
                } else if (from <= -1 && to >= 0) {
                } else {
                    int[] cf = dpmap[v].get(from);
                    int[] ct = dpmap[v+1].get(to);
                    if ((cf[0] == ct[0] && cf[1] > ct[1]) || cf[0] > ct[0]) {
                        nextIsFrom = true;
                    }
                }

                if (nextIsFrom) {
                    int[] cf = dpmap[v].get(from);
                    if (tree.ceiling(cf[1]) != null) {
                        importantCount++;
                        cf[2] = -1;
                    }
                    from--;
                } else {
                    int[] tf = dpmap[v+1].get(to);
                    if (tf[2] == -1) {
                        tree.add(tf[1]);
                    }
                    to--;
                }
            }

            if (importantCount == 1) {
                ans++;
            }
        }

        out.println(ans);
        out.flush();
    }

    public static class SegmentTreePURMQ {
        int N;
        int M;
        int[] seg;

        public SegmentTreePURMQ(int[] data) {
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

        /**
         * Uodates value at position minIndexSum.
         *
         * @param idx
         * @param value
         */
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

        private int compute(int i) {
            return Math.max(seg[i*2+1], seg[i*2+2]);
        }

        /**
         * Finds minimum value from range [l,r).
         *
         * @param l
         * @param r
         * @return minimum value
         */
        public int max(int l, int r) {
            return max(l, r, 0, 0, M+1);
        }

        private int max(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MIN_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MIN_VALUE;
            ret = Math.max(ret, max(l, r, idx*2+1, fr, med));
            ret = Math.max(ret, max(l, r, idx*2+2, med, to));
            return ret;
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
