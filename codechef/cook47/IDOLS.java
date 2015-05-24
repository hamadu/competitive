package codechef.cook47;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by dhamada on 15/05/23.
 */
public class IDOLS {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();

        int[][] graph = buildGraph(in, n, n-1);
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        HeavyLightDecomposer hl = new HeavyLightDecomposer(graph, 0);
        hl.decompose(0, 0, -1, 0);
        hl.buildHLComponent();

        while (--q >= 0) {
            int type = in.nextInt();
            int x = in.nextInt() - 1;
            if (type == 0) {
                // q
            } else {
                // upd
                int newValue = in.nextInt();
                a[x] = newValue;

            }
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
    }

    static class HLComponent {
        int head;
        int n;
        int parentGid;
        int[] idx;

        SegmentTree seg;

        HLComponent(int h, int pgid, int[] ids) {
            n = ids.length;
            head = h;
            idx = ids;
            parentGid = pgid;

            int[] v = new int[n];
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

        public HeavyLightDecomposer(int[][] g, int root) {
            n = graph.length;
            graph = g;
            parent = new int[n];
            children = new int[n];
            gid = new int[n];
            orderInGroup = new int[n];
            parentGid = new int[n];

            dfs0(root, -1);
        }

        public int dfs0(int now, int par) {
            children[now] = 1;
            parent[now] = par;
            for (int to : graph[now]) {
                if (to != par) {
                    children[now] += dfs0(to, now);
                }
            }
            return children[now];
        }

        public int decompose(int head, int gi, int pgi, int depth) {
            gid[head] = gi;
            orderInGroup[head] = depth;
            parentGid[head] = pgi;

            int maxTo = -1;
            int max = -1;
            for (int to : graph[head]) {
                if (parent[to] != head) {
                    if (max < children[to]) {
                        max = children[to];
                        maxTo = to;
                    }
                }
            }
            if (maxTo == -1) {
                return gi;
            }

            // heavy
            int retGid = decompose(maxTo, gi, gi, depth+1);

            // light
            for (int to : graph[head]) {
                if (parent[to] != head && to != maxTo) {
                    decompose(to, retGid++, gi, 0);
                }
            }
            return retGid;
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

                List<Integer> ids = new ArrayList<>();
                while (true) {
                    ids.add(now);
                    int next = -1;
                    for (int to : graph[now]) {
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

                int[] aids= new int[ids.size()];
                int ai = 0;
                for (int i : ids) {
                    aids[ai++] = i;
                }
                components[gid[head]] = new HLComponent(head, parentGid, aids);
            }
        }


    }


    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = i+1;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
