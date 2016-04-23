package atcoder.other2015.codefestival2015.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/11/14.
 */
public class I {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] parent = new int[n];

        int[] l = new int[n];
        int[] height = new int[n];
        for (int i = 0; i < n ; i++) {
            l[i] = in.nextInt();
        }
        height[0] = l[0];
        parent[0] = -1;

        int[] indeg = new int[n];
        for (int i = 1; i < n ; i++) {
            int p = in.nextInt() - 1;
            height[i] = height[p] + l[i];
            parent[i] = p;
            indeg[p]++;
        }
        graph = new int[n][];
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[indeg[i]];
        }
        for (int i = 0; i < n ; i++) {
            if (parent[i] != -1) {
                int p = parent[i];
                graph[p][--indeg[p]] = i;
            }
        }

        ho = compress(height);

        maxho = new int[n];
        bit = new BIT(n+10);
        ans = new int[n+1];
        Arrays.fill(ans, Integer.MAX_VALUE);

        EulerTour tour = new EulerTour(graph);
        tour.build(0);
        dfs0(tour);
        dfs(tour);

        Map<Integer,Integer> cmap = new HashMap<>();
        for (int i = 0 ; i < n ; i++) {
            cmap.put(height[i], ho[i]);
        }
        int q = in.nextInt();
        for (int i = 0; i < q ; i++) {
            int h = in.nextInt();
            int ch = cmap.getOrDefault(h, -1);
            if (ch == -1 || ans[ch] == Integer.MAX_VALUE) {
                out.println(-1);
            } else {
                out.println(ans[ch]);
            }
        }
        out.flush();
    }

    static int[] compress(int[] h) {
        int[] hi = h.clone();
        int n = h.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n ; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (i1, i2) -> hi[i1] - hi[i2]);

        hi[idx[0]] = 1;
        for (int i = 1 ; i < n ; i++) {
            int prev = idx[i-1];
            int now = idx[i];
            if (i >= 1 && h[prev] == h[now]) {
                hi[now] = hi[prev];
            } else {
                hi[now] = hi[prev]+1;
            }
        }
        return hi;
    }


    static int[] ho;
    static int[] maxho;
    static int[][] graph;
    static BIT bit;
    static int[] ans;

    static class EulerTour {
        int n;
        int[][] graph;
        int[] parent;
        int[] ar;
        int[] cn;
        int ai = 0;

        public EulerTour(int[][] graph) {
            this.graph = graph;
            this.n = graph.length;
            this.parent = new int[n];
            this.ar = new int[2*n];
            this.cn = new int[n];
        }

        private void go(int now) {
            ar[ai++] = now;
            int head = now;
            while (head != -1) {
                if (cn[head] != 0) {
                    break;
                }
                ar[ai++] = -(head+1);
                head = parent[head];
                if (head != -1) {
                    cn[head]--;
                }
            }
        }

        private void parentChild(int root) {
            int[] que = new int[2*n];
            int qh = 0;
            int qt = 0;
            que[qh++] = root;
            que[qh++] = -1;
            while (qt < qh) {
                int now = que[qt++];
                int par = que[qt++];
                parent[now] = par;
                for (int to : graph[now]) {
                    if (to != now) {
                        que[qh++] = to;
                        que[qh++] = now;
                    }
                }
            }
            for (int i = 0; i < n ; i++) {
                cn[i] = (parent[i] == -1) ? graph[i].length : graph[i].length-1;
            }
        }

        void build(int root) {
            parentChild(root);

            Stack<Integer> stk = new Stack<>();
            ai = 0;
            stk.push(root);
            stk.push(-1);
            while (stk.size() >= 1) {
                int par = stk.pop();
                int now = stk.pop();
                go(now);
                for (int to : graph[now]) {
                    if (to != par) {
                        stk.push(to);
                        stk.push(now);
                    }
                }
            }
        }
    }

    static void dfs0(EulerTour tour) {
        int tn = tour.ar.length;
        for (int i = 0 ; i < tn ; i++) {
            if (tour.ar[i] < 0) {
                int now = -tour.ar[i]-1;
                for (int to : graph[now]) {
                    if (to != tour.parent[now]) {
                        maxho[now] = Math.max(maxho[now], maxho[to]);
                    }
                }
            } else {
                int now = tour.ar[i];
                maxho[now] = ho[now];
            }
        }
    }

    static void dfs(EulerTour tour) {
        int tn = tour.ar.length;
        for (int i = 0 ; i < tn ; i++) {
            if (tour.ar[i] < 0) {
                // deru
                int now = -tour.ar[i]-1;
                for (int to : graph[now]) {
                    if (to != tour.parent[now]) {
                        bit.add(maxho[to], -1);
                    }
                }
                if (tour.parent[now] != -1) {
                    bit.add(maxho[now], 1);
                }
            } else {
                // hairu
                int now = tour.ar[i];
                for (int to : graph[now]) {
                    if (to != tour.parent[now]) {
                        bit.add(maxho[to], 1);
                    }
                }
                if (tour.parent[now] != -1) {
                    bit.add(maxho[now], -1);
                }
                int h = ho[now];
                ans[h] = Math.min(ans[h], (int)bit.range(h+1, graph.length+5));
            }
        }
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
