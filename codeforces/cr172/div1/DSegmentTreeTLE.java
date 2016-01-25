package codeforces.cr172.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/10.
 */
// O(mlognk^2) : TLE
public class DSegmentTreeTLE {
    static final int INF = 114514810;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        SegmentTree tree = new SegmentTree(a);

        int m = in.nextInt();
        while (--m >= 0) {
            int type = in.nextInt();
            if (type == 0) {
                int idx = in.nextInt()-1;
                int val = in.nextInt();
                tree.update(idx, val);
            } else {
                int l = in.nextInt()-1;
                int r = in.nextInt();
                int k = in.nextInt();
                NodeData ret = tree.range(l, r);
                int max = -INF;
                for (int i = 0; i < 4 ; i++) {
                    for (int z = 0; z <= k; z++) {
                        max = Math.max(max, ret.data[i][z]);
                    }
                }
                out.println(max);
            }
        }
        out.flush();
    }

    static final int K = 21;

    static class NodeData {
        int[][] data;
        static NodeData empty = new NodeData();

        private NodeData() {
            data = new int[4][K];
            for (int i = 0; i < 4 ; i++) {
                Arrays.fill(data[i], -INF);
            }
            data[0][0] = 0;

        }

        NodeData(int v) {
            data = new int[4][K];
            for (int i = 0; i < 4 ; i++) {
                Arrays.fill(data[i], -INF);
            }
            data[0][0] = 0;
            data[3][1] = v;
        }


    }

    static class SegmentTree {
        int N;
        int M;

        NodeData[] data;

        int[][] zero;

        public SegmentTree(int[] arr) {
            zero = new int[4][K];
            for (int i = 0; i < 4 ; i++) {
                Arrays.fill(zero[i], -INF);
            }
            zero[0][0] = 0;

            int n = arr.length;
            N = Integer.highestOneBit(n)<<2;
            M = N / 2;
            data = new NodeData[N];
            for (int i = 0; i < N ; i++) {
                data[i] = new NodeData();
            }
            for (int i = 0; i < n ; i++) {
                data[M-1+i] = new NodeData(arr[i]);
            }
            for (int i = M-2 ; i >= 0 ; i--) {
                merge(i*2+1, i*2+2, i);
            }
        }

        public void update(int idx, int val) {
            int f = M-1+idx;
            data[f].data[0][0] = 0;
            data[f].data[3][1] = val;
            while (f > 0) {
                f = (f-1)>>1;
                merge(f*2+1, f*2+2, f);
            }
        }

        public NodeData range(int fr, int to) {
            return range(fr, to, 0, 0, M);
        }

        private NodeData range(int fr, int to, int idx, int a, int b) {
            if (fr <= a && b <= to) {
                return data[idx];
            }
            if (b <= fr || to <= a) {
                return NodeData.empty;
            }
            int med = (a+b)/2;
            NodeData left = range(fr, to, idx*2+1, a, med);
            NodeData right = range(fr, to, idx*2+2, med, b);
            return merge(left, right, new NodeData());
        }

        public void merge(int i1, int i2, int to) {
            merge(data[i1], data[i2], data[to]);
        }


        public NodeData merge(NodeData ad, NodeData bd, NodeData cd) {
            int[][] mergeRet = cd.data;
            for (int i = 0; i < 4 ; i++) {
                Arrays.fill(mergeRet[i], -INF);
            }
            int[][] a = ad.data;
            int[][] b = bd.data;
            for (int w = 0; w < 4; w++) {
                Arrays.fill(mergeRet[w], -INF);
                int[] ax = (w <= 1) ? a[0] : a[2];
                int[] ao = (w <= 1) ? a[1] : a[3];
                int[] xb = ((w & 1) == 0) ? b[0] : b[1];
                int[] ob = ((w & 1) == 0) ? b[2] : b[3];
                for (int ak = 0; ak < K ; ak++) {
                    for (int bk = 0; bk < K ; bk++) {
                        int akbk0 = ak+bk;
                        int akbk1 = ak+bk-1;
                        if (akbk0 < K) {
                            int best = Math.max(ax[ak] + xb[bk], Math.max(ax[ak] + ob[bk], ao[ak] + xb[bk]));
                            mergeRet[w][akbk0] = Math.max(mergeRet[w][akbk0], best);
                        }
                        if (akbk1 >= 0 && akbk1 < K) {
                            mergeRet[w][akbk1] = Math.max(mergeRet[w][akbk1], ao[ak] + ob[bk]);
                        }
                    }
                }
            }
            return cd;
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
