package csacademy.ioitraining2016.round002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class CographClique {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        a = in.nextInts(n);
        graph = new int[n][n];
        for (int i = 0; i < m ; i++) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            graph[u][v] = graph[v][u] = 1;
        }
        visited = new int[n];
        available = new int[n];

        List<Integer> li = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            li.add(i);
        }
        Clique cl = clique(li, 1, 0);

        out.println(cl.value);
        out.println(cl.vs.size());

        for (int i = 0; i < cl.vs.size(); i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(cl.vs.get(i)+1);
        }
        out.println();
        out.flush();
    }

    static int[] a;
    static int[][] graph;

    static int[] visited;
    static int[] available;

    static int availableSeq;
    static int visitedSeq;

    static Clique clique(List<Integer> vs, int flg, int lv) {
        int n = vs.size();
        if (n == 1) {
            return new Clique(a[vs.get(0)], vs);
        }
        availableSeq++;
        Arrays.fill(available, 0);
        for (int vi : vs) {
            available[vi] = availableSeq;
        }


        visitedSeq++;
        int startSeq = visitedSeq;
        for (int now : vs) {
            if (visited[now] >= startSeq) {
                continue;
            }
            visited[now] = visitedSeq;
            dfs(flg, now);
            visitedSeq++;
        }

        int gn = visitedSeq-startSeq;

        if (gn == 1) {
            return clique(vs, flg^1, lv);
        } else {
            List<Integer>[] list = new List[gn];
            int[] max = new int[gn];
            int[] maxvi = new int[gn];
            for (int i = 0; i < gn ; i++) {
                list[i] = new ArrayList<>();
            }
            for (int vi : vs) {
                int id = visited[vi] - startSeq;
                if (max[id] < a[vi]) {
                    max[id] = a[vi];
                    maxvi[id] = vi;
                }
                list[id].add(vi);
            }
            if (flg == 0) {
                Clique ret = new Clique(0, new ArrayList<>());
                for (List<Integer> li : list) {
                    Clique cl = clique(li, flg, lv+1);
                    ret.value += cl.value;
                    ret.vs.addAll(cl.vs);
                }
                return ret;
            } else {
                Clique ret = new Clique(0, new ArrayList<>());
                for (List<Integer> li : list) {
                    Clique cl = clique(li, flg, lv+1);
                    if (ret.value < cl.value) {
                        ret = cl;
                    }
                }
                return ret;
            }
        }
    }

    static class Clique {
        int value;
        List<Integer> vs;

        public Clique(int v, List<Integer> vv) {
            value = v;
            vs = vv;
        }

    }

    static void dfs(int flg, int now) {
        for (int i = 0 ; i < graph.length ; i++) {
            if (graph[now][i] == flg && available[i] == availableSeq && visited[i] < visitedSeq) {
                visited[i] = visitedSeq;
                dfs(flg, i);
            }
        }
    }


    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n; i++) {
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
