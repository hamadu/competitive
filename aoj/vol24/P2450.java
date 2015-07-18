package aoj.vol24;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/18.
 */
public class P2450 {
    static int INF = 200000000;

    static final int[] EMPTY = {-INF, -INF, 0, -INF};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        W = new int[n];
        for (int i = 0; i < n ; i++) {
            W[i] = in.nextInt();
        }
        int[][] g = buildGraph(in, n, n-1);
        HeavyLightDecomposer dec = new HeavyLightDecomposer(g, 0);
        dec.doit();

//        debug(dec.parent);
//        debug(dec.children);
//        debug(dec.gid);

        while (--q >= 0) {
            int t = in.nextInt();
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int c = in.nextInt();
            if (t == 1) {
                while (dec.gid[a] != dec.gid[b]) {
                    HLComponent ca = dec.components[dec.gid[a]];
                    HLComponent cb = dec.components[dec.gid[b]];
                    if (ca.depth < cb.depth) {
                        HLComponent comp = cb;
                        comp.seg.upd(0, dec.orderInGroup[b] + 1, c);
                        b = dec.parent[comp.head];
                    } else {
                        HLComponent comp = ca;
                        comp.seg.upd(0, dec.orderInGroup[a]+1, c);
                        a = dec.parent[comp.head];
                    }
                }
                int lastGid = dec.gid[a];
                int l = Math.min(dec.orderInGroup[a], dec.orderInGroup[b]);
                int r = Math.max(dec.orderInGroup[a], dec.orderInGroup[b]);
                dec.components[lastGid].seg.upd(l, r + 1, c);

                HLComponent comp = dec.components[lastGid];
            } else {
                int[] av = EMPTY.clone();
                int[] bv = EMPTY.clone();
                while (dec.gid[a] != dec.gid[b]) {
                    // debug(a, b, dec.gid[a], dec.gid[b], av, bv);
                    HLComponent ca = dec.components[dec.gid[a]];
                    HLComponent cb = dec.components[dec.gid[b]];
                    if (ca.depth < cb.depth) {
                        HLComponent comp = cb;
                        int[] fbv = comp.seg.val(0, dec.orderInGroup[b]+1);
                        bv = SegmentTree.compute(fbv, bv);
                        b = dec.parent[comp.head];
                    } else {
                        HLComponent comp = ca;
                        int[] fav = comp.seg.val(0, dec.orderInGroup[a]+1);
                        av = SegmentTree.compute(fav, av);
                        a = dec.parent[comp.head];
                    }
                }
                int lastGid = dec.gid[a];
                int l = Math.min(dec.orderInGroup[a], dec.orderInGroup[b]);
                int r = Math.max(dec.orderInGroup[a], dec.orderInGroup[b]);
                int[] abv = dec.components[lastGid].seg.val(l, r+1);
                if (dec.orderInGroup[a] < dec.orderInGroup[b]) {
                    bv = SegmentTree.compute(abv, bv);
                } else {
                    av = SegmentTree.compute(abv, av);
                }
                int tmp = av[0];
                av[0] = av[1];
                av[1] = tmp;
                int[] ans = SegmentTree.compute(av, bv);
                out.println(ans[3]);
            }
        }
        out.flush();
    }

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    public static class SegmentTree {
        int N;
        int M;
        int[][] data;
        int[] cov;
        public SegmentTree(int[] v) {
            N = Integer.highestOneBit(v.length)<<2;
            M = (N >> 1) - 1;

            data = new int[N][4];
            for (int i = 0 ; i < N ; i++) {
                for (int j = 0; j < 4 ; j++) {
                    data[i][j] = EMPTY[j];
                }
            }
            for (int i = 0 ; i < v.length ; i++) {
                int mi = M+i;
                for (int j = 0; j < 4 ; j++) {
                    data[mi][j] = v[i];
                }
            }
            cov = new int[N];
            Arrays.fill(cov, INF);
            for (int i = M-1 ; i >= 0 ; i--) {
                compute(i);
            }
        }

        public void propergate(int idx, int fr, int to) {
            if (cov[idx] != INF) {
                int val = cov[idx] * (to - fr);
                if (cov[idx] >= 1) {
                    Arrays.fill(data[idx], val);
                } else {
                    Arrays.fill(data[idx], cov[idx]);
                    data[idx][2] = val;
                }
                int L = idx*2+1;
                int R = idx*2+2;
                if (R < data.length) {
                    cov[L] = cov[R] = cov[idx];
                }
                cov[idx] = INF;
            }
        }

        public void compute(int i) {
            int L = i*2+1;
            int R = i*2+2;
            compute(data[i], data[L], data[R]);
        }

        public static void compute(int[] ret, int[] a, int[] b) {
            ret[0] = Math.max(a[0], a[2] + b[0]);
            ret[1] = Math.max(b[1], b[2] + a[1]);
            ret[2] = a[2] + b[2];
            ret[3] = Math.max(Math.max(a[3], b[3]), a[1] + b[0]);
        }

        public static int[] compute(int[] a, int[] b) {
            int[] ret = new int[4];
            ret[0] = Math.max(a[0], a[2] + b[0]);
            ret[1] = Math.max(b[1], b[2] + a[1]);
            ret[2] = a[2] + b[2];
            ret[3] = Math.max(Math.max(a[3], b[3]), a[1] + b[0]);
            return ret;
        }

        public int[] val(int l, int r) {
            return val(l, r, 0, 0, M + 1);
        }

        public int[] val(int l, int r, int idx, int fr, int to) {
            propergate(idx, fr, to);

            if (to <= l || r <= fr) {
                return EMPTY.clone();
            }
            if (l <= fr && to <= r) {
                return data[idx];
            }

            int med = (fr+to) / 2;
            int[] L = val(l, r, idx*2+1, fr, med);
            int[] R = val(l, r, idx*2+2, med, to);
            return compute(L, R);
        }

        public void upd(int l, int r, int c) {
            upd(l, r, c, 0, 0, M + 1);
        }

        public void upd(int l, int r, int c, int idx, int fr, int to) {
            propergate(idx, fr, to);

            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r) {
                cov[idx] = c;
                propergate(idx, fr, to);
                return;
            }
            int med = (fr+to) / 2;
            upd(l, r, c, idx*2+1, fr, med);
            upd(l, r, c, idx*2+2, med, to);
            compute(idx);
        }
    }

    static int[] W;

    static class HLComponent {
        int head;
        int n;
        int parentGid;
        int[] idx;
        int depth;

        SegmentTree seg;

        HLComponent(int h, int pgid, int[] ids, int de) {
            n = ids.length;
            head = h;
            idx = ids;
            parentGid = pgid;
            depth = de;

            int[] v = new int[n];
            for (int i = 0; i < n ; i++) {
                v[i] = W[ids[i]];
            }
            seg = new SegmentTree(v);
        }
    }

    static class HeavyLightDecomposer {
        int n;
        int[][] graph;
        int[] parent;
        int[] children;

        int[] gid;
        int[] orderInGroup;
        int[] parentGid;

        HLComponent[] components;

        static int[] _temp_ids = new int[300000];
        static int[] _stk = new int[1000000];

        public HeavyLightDecomposer(int[][] g, int root) {
            graph = g;
            n = graph.length;
            parent = new int[n];
            children = new int[n];
            gid = new int[n];
            orderInGroup = new int[n];
            parentGid = new int[n];

            dfs0(root);
        }

        public void dfs0(int root) {
            int head = 0;
            _stk[head++] = root;
            _stk[head++] = -1;
            int tid = 0;
            while (head > 0) {
                int par = _stk[--head];
                int now = _stk[--head];
                _temp_ids[tid++] = now;
                parent[now] = par;
                for (int to : graph[now]) {
                    if (to != par) {
                        _stk[head++] = to;
                        _stk[head++] = now;
                    }
                }
            }
            for (int t = tid -1 ; t >= 0 ; t--) {
                int now = _temp_ids[t];
                children[now] += 1;
                if (parent[now] != -1) {
                    children[parent[now]] += children[now];
                }
            }
        }

        public void doit() {
            ngi = 0;
            decompose(0);
            buildHLComponent();
        }

        static int ngi = 0;

        public void decompose(int root) {
            int sh = 0;
            _stk[sh++] = root;
            _stk[sh++] = -1;
            _stk[sh++] = -1;
            _stk[sh++] = 0;
            while (sh > 0) {
                int depth = _stk[--sh];
                int pgi = _stk[--sh];
                int gi = _stk[--sh];
                int head = _stk[--sh];
                if (gi == -1) {
                    gi = ngi++;
                }

                gid[head] = gi;
                orderInGroup[head] = depth;
                parentGid[head] = pgi;

                int maxTo = -1;
                int max = -1;
                for (int to : graph[head]) {
                    if (parent[head] == to) {
                        continue;
                    }
                    if (max < children[to]) {
                        max = children[to];
                        maxTo = to;
                    }
                }
                if (maxTo == -1) {
                    continue;
                }

                // heavy
                _stk[sh++] = maxTo;
                _stk[sh++] = gi;
                _stk[sh++] = gi;
                _stk[sh++] = depth+1;

                // light
                for (int to : graph[head]) {
                    if (parent[head] == to) {
                        continue;
                    }
                    if (to != maxTo) {
                        _stk[sh++] = to;
                        _stk[sh++] = -1;
                        _stk[sh++] = gi;
                        _stk[sh++] = 0;
                    }
                }
            }
        }

        public void decompose(int head, int gi, int pgi, int depth) {
            if (gi == -1) {
                gi = ngi++;
            }
            gid[head] = gi;
            orderInGroup[head] = depth;
            parentGid[head] = pgi;

            int maxTo = -1;
            int max = -1;
            for (int to : graph[head]) {
                if (parent[head] == to) {
                    continue;
                }
                if (max < children[to]) {
                    max = children[to];
                    maxTo = to;
                }
            }
            if (maxTo == -1) {
                return;
            }

            // heavy
            decompose(maxTo, gi, gi, depth+1);

            // light
            for (int to : graph[head]) {
                if (parent[head] == to) {
                    continue;
                }
                if (to != maxTo) {
                    decompose(to, -1, gi, 0);
                }
            }
        }

        public void buildHLComponent() {
            int maxG = 0;
            for (int gi : gid) {
                maxG = Math.max(maxG, gi+1);
            }

            components = new HLComponent[maxG];
            int[] que = new int[n*2];
            int qh = 0;
            int qt = 0;
            que[qh++] = 0;
            que[qh++] = -1;
            while (qt < qh) {
                int head = que[qt++];
                int parentGid = que[qt++];
                int now = head;

                int tid = 0;
                while (true) {
                    _temp_ids[tid++] = now;
                    int next = -1;
                    for (int to : graph[now]) {
                        if (to == parent[now]) {
                            continue;
                        }
                        if (gid[head] != gid[to]) {
                            que[qh++] = to;
                            que[qh++] = gid[head];
                        } else {
                            next = to;
                        }
                    }
                    if (next == -1) {
                        break;
                    }
                    now = next;
                }

                int[] aids = Arrays.copyOf(_temp_ids, tid);
                int depth = 0;
                if (parentGid != -1) {
                    depth = components[parentGid].depth + 1;
                }
                components[gid[head]] = new HLComponent(head, parentGid, aids, depth);
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
