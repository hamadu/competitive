package hackerrank.codestorm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/10/30.
 */
public class Independent {
    private static final long MOD = 1000000007;

    static long solve(int[] a) {
        int n = a.length;
        UnionFind uf = new UnionFind(n);

        boolean[] markA = new boolean[n];
        boolean[] markB = new boolean[n];
        int leftMax = -1;
        for (int i = 0; i < n ; i++) {
            if (leftMax >= a[i]) {
                markA[i] = true;
            }
            if (leftMax < a[i]) {
                leftMax = a[i];
            }
        }
        int rightMin = Integer.MAX_VALUE;
        for (int i = n-1; i >= 0 ; i--) {
            if (rightMin <= a[i]) {
                markB[i] = true;
            }
            if (rightMin > a[i]) {
                rightMin = a[i];
            }
            rightMin = Math.min(rightMin, a[i]);
        }

        long ct = 1;
        for (int i = 0; i < n ; i++) {
            if (markA[i] && markB[i]) {
                ct = 0;
            }
        }
        if (ct >= 1) {
            int[][] order = new int[n][2];
            for (int i = 0; i < n ; i++) {
                order[i][0] = a[i];
                order[i][1] = i;
            }
            Arrays.sort(order, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] == o2[0]) {
                        return o1[1] - o2[1];
                    }
                    return o1[0] - o2[0];
                }
            });

            boolean[] done = new boolean[n];
            for (int i = n-1 ; i >= 0 ; i--) {
                if (done[i]) {
                    continue;
                }
                for (int j = i-1 ; j >= 0 ; j--) {
                    if (a[j] >= a[i]) {
                        done[j] = true;
                        uf.unite(i, j);
                    }
                }
            }

            boolean[] foe = new boolean[n];
            for (int i = 0; i < n ; i++) {
                int id = uf.find(i);
                if (!foe[id]) {
                    foe[id] = true;
                    ct *= 2;
                    ct %= MOD;
                }
            }
        }
        return ct;
    }

    static long solve2(int[] a) {
        int n = a.length;

        boolean[] markA = new boolean[n];
        boolean[] markB = new boolean[n];
        int leftMax = -1;
        for (int i = 0; i < n ; i++) {
            if (leftMax >= a[i]) {
                markA[i] = true;
            }
            if (leftMax < a[i]) {
                leftMax = a[i];
            }
        }
        int rightMin = Integer.MAX_VALUE;
        for (int i = n-1; i >= 0 ; i--) {
            if (rightMin <= a[i]) {
                markB[i] = true;
            }
            if (rightMin > a[i]) {
                rightMin = a[i];
            }
            rightMin = Math.min(rightMin, a[i]);
        }
        for (int i = 0; i < n ; i++) {
            if (markA[i] && markB[i]) {
                return 0;
            }
        }

        long pev = 0;
        SegmentTree seg = new SegmentTree(a);
        for (int i = 0; i < n ; i++) {
            int max = seg.max(0, i+1);
            int min = seg.min(i+1, n);
            if (max < min) {
                pev++;
            }
        }

        long ct = 1;
        for (int i = 0; i < pev ; i++) {
            ct *= 2;
            ct %= MOD;
        }
        return ct;
    }

    public static class SegmentTree {
        int N;
        int M;
        int[] segMin;
        int[] segMax;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            segMin = new int[N];
            segMax = new int[N];
            Arrays.fill(segMin, Integer.MAX_VALUE);
            Arrays.fill(segMin, Integer.MIN_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                segMin[M+i] = data[i];
                segMax[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                segMin[i] = computeMin(i);
                segMax[i] = computeMax(i);
            }
        }

        public int computeMax(int i) {
            return Math.max(segMax[i*2+1], segMax[i*2+2]);
        }

        public int computeMin(int i) {
            return Math.min(segMin[i*2+1], segMin[i*2+2]);
        }

        public int max(int l, int r) {
            return max(l, r, 0, 0, M+1);
        }

        public int min(int l, int r) {
            return min(l, r, 0, 0, M+1);
        }

        public int max(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MIN_VALUE;
            }
            if (l <= fr && to <= r) {
                return segMax[idx];
            }
            int med = (fr+to) / 2;
            int ret = Integer.MIN_VALUE;
            ret = Math.max(ret, max(l, r, idx * 2+1, fr, med));
            ret = Math.max(ret, max(l, r, idx * 2+2, med, to));
            return ret;
        }


        public int min(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MAX_VALUE;
            }
            if (l <= fr && to <= r) {
                return segMin[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MAX_VALUE;
            ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
            ret = Math.min(ret, min(l, r, idx*2+2, med, to));
            return ret;
        }

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
        java.util.Arrays.sort(num, x+1, len);
        return true;
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        if (n <= 100) {
            out.println(solve(a));
        } else {
            out.println(solve2(a));
        }
        out.flush();
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
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
