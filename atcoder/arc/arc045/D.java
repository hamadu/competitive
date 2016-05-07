package atcoder.arc.arc045;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/05/07.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        n *= 2;
        n++;
        int[][] v = new int[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                v[i][j] = in.nextInt();
            }
            v[i][2] = i;
        }

        List<int[]> edges = new ArrayList<>();

        // x-y and y-x
        for (int ord : new int[]{0, 1}) {
            Arrays.sort(v, (o1, o2) -> (o1[ord] != o2[ord]) ? o1[ord] - o2[ord] : o1[1-ord] - o2[1-ord]);
            for (int fi = 0; fi < n; ) {
                int ti = fi;
                while (ti < n && v[ti][ord] == v[fi][ord]) {
                    ti++;
                }
                for (int i = fi ; i < ti ; i++) {
                    for (int j = i-2 ; j <= i+2 ; j++) {
                        if (j < fi || j >= ti || i == j || v[i][2] > v[j][2]) {
                            continue;
                        }
                        edges.add(new int[]{v[i][2], v[j][2]});
                    }
                }
                fi = ti;
            }
        }

        UnionFind uf = new UnionFind(n);
        int[][] graph = buildGraph(n, edges);
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                uf.unite(i, to);
            }
        }
        boolean[] found = new boolean[n];
        int rongai = 0;
        for (int i = 0 ; i < n ; i++) {
            if (!found[uf.find(i)]) {
                found[uf.find(i)] = true;
                rongai += uf.groupCount(i) % 2;
            }
        }

        // sort to original order
        Arrays.sort(v, (o1, o2) -> o1[2] - o2[2]);

        LowLink lowLink = new LowLink(graph);
        lowLink.build();

        for (int i = 0 ; i < n ; i++) {
            if (rongai >= 2) {
                out.println("NG");
                continue;
            }
            int cnt = uf.groupCount(i);
            if (cnt % 2 == 0) {
                out.println("NG");
            } else {
                if (lowLink.isArticulationPoint(i)) {
                    boolean isOK = true;

                    // compute the seprated group size
                    if (lowLink.root[i]) {
                        for (int to : lowLink.dfsTree[i]) {
                            if (to != -1) {
                                isOK &= lowLink.cnt[to] % 2 == 0;
                            }
                        }
                    } else {
                        int left = n-1;
                        int myOrd = lowLink.ord[i];
                        for (int to : lowLink.dfsTree[i]) {
                            if (to != -1 && myOrd <= lowLink.low[to]) {
                                isOK &= lowLink.cnt[to] % 2 == 0;
                                left -= lowLink.cnt[to];
                            }
                        }
                        isOK &= left % 2 == 0;
                    }
                    out.println(isOK ? "OK" : "NG");
                } else {
                    out.println("OK");
                }
            }
        }

        out.flush();
    }


    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
    }

    static class LowLink {
        int n;
        int[] parent;
        int[] cnt;
        int[] ord;
        int[] low;
        int[][] graph;
        int[][] dfsTree;
        boolean[] root;
        int oi = 0;

        public LowLink(int[][] graph) {
            this.n = graph.length;
            this.parent = new int[n];
            this.graph = graph;
            this.ord = new int[n];
            this.low = new int[n];
            this.root = new boolean[n];
            this.cnt = new int[n];
            this.dfsTree = new int[n][];
            for (int i = 0; i < n ; i++) {
                this.dfsTree[i] = new int[graph[i].length];
                Arrays.fill(this.dfsTree[i], -1);
            }
            Arrays.fill(parent, -1);
            Arrays.fill(ord, -1);
            Arrays.fill(low, n);
        }

        public void build() {
            for (int i = 0 ; i < n ; i++) {
                if (ord[i] == -1) {
                    root[i] = true;
                    // optional
                    // dfsWithoutRecursive(i);
                    dfs(i, -1);
                    dfs0(i, -1);
                }
            }
        }

        private void dfsWithoutRecursive(int rt) {
            Stack<Integer> stk = new Stack<>();
            stk.push(rt);
            stk.push(-1);
            stk.push(-1);

            List<Integer> vi = new ArrayList<>();
            while (stk.size() >= 1) {
                int pid = stk.pop();
                int par = stk.pop();
                int now = stk.pop();
                if (ord[now] != -1) {
                    continue;
                }
                vi.add(now);
                if (pid >= 0) {
                    dfsTree[par][pid] = now;
                }
                parent[now] = par;
                ord[now] = oi;
                low[now] = oi++;
                for (int i = 0 ; i < graph[now].length ; i++) {
                    int to = graph[now][i];
                    if (to == par) {
                        continue;
                    }
                    if (ord[to] == -1) {
                        stk.push(to);
                        stk.push(now);
                        stk.push(i);
                    }
                }
            }

            for (int i = vi.size()-1 ; i >= 0 ; i--) {
                int now = vi.get(i);
                cnt[now] = 1;
                for (int j = 0 ; j < graph[now].length ; j++) {
                    int to = graph[now][j];
                    if (to == parent[now]) {
                        // ignore parent edge
                        continue;
                    }
                    if (dfsTree[now][j] != -1) {
                        cnt[now] += cnt[dfsTree[now][j]];
                        low[now] = Math.min(low[now], low[dfsTree[now][j]]);
                    } else {
                        // that's a back edge!
                        low[now] = Math.min(low[now], ord[to]);
                    }
                }
            }
        }

        private void dfs(int now, int par) {
            if (ord[now] != -1) {
                return;
            }
            ord[now] = oi;
            low[now] = oi++;
            for (int i = 0 ; i < graph[now].length ; i++) {
                int to = graph[now][i];
                if (to == par) {
                    continue;
                }
                if (ord[to] == -1) {
                    dfsTree[now][i] = to;
                    dfs(to, now);
                    low[now] = Math.min(low[now], low[to]);
                } else {
                    // that's a back edge!
                    low[now] = Math.min(low[now], ord[to]);
                }
            }
        }

        private void dfs0(int now, int par) {
            cnt[now] = 1;
            for (int to : dfsTree[now]) {
                if (to == -1 || to == par) {
                    continue;
                }
                dfs0(to, now);
                cnt[now] += cnt[to];
            }
        }

        private boolean isBridge(int u, int v) {
            return ord[u] < low[v];
        }

        private boolean isArticulationPoint(int u) {
            if (root[u]) {
                int cn = 0;
                for (int to : dfsTree[u]) {
                    if (to != -1) {
                        cn++;
                    }
                }
                return cn >= 2;
            } else {
                for (int to : dfsTree[u]) {
                    if (to != -1 && ord[u] <= low[to]) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    static int[][] buildGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
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
