package codeforces.cf3xx.cf375.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    private static final int[] DY = {-1, 0, 1, 0};
    private static final int[] DX = {0, -1, 0, 1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }


        UnionFind uf = new UnionFind(n*m);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '.') {
                    for (int d = 0 ; d < 4 ; d++) {
                        int ti = i+DY[d];
                        int tj = j+DX[d];
                        if (ti < 0 || tj < 0 || ti >= n || tj >= m) {
                            continue;
                        }
                        if (map[ti][tj] == '.') {
                            uf.unite(i*m+j, ti*m+tj);
                        }
                    }
                }
            }
        }

        List<int[]> count = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            if (map[i][0] == '.') {
                set.add(uf.find(i*m+0));
            }
            if (map[i][m-1] == '.') {
                set.add(uf.find(i*m+m-1));
            }
        }
        for (int i = 0; i < m ; i++) {
            if (map[0][i] == '.') {
                set.add(uf.find(i));
            }
            if (map[n-1][i] == '.') {
                set.add(uf.find((n-1)*m+i));
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '.') {
                    int id = uf.find(i*m+j);
                    if (!set.contains(id)) {
                        set.add(id);
                        count.add(new int[]{id, uf.groupCount(id)});
                    }
                }
            }
        }

        int ume = 0;
        Collections.sort(count, (a, b) -> a[1] - b[1]);
        Set<Integer> isume = new HashSet<>();
        for (int i = 0; i < count.size() - k ; i++) {
            isume.add(count.get(i)[0]);
            ume += count.get(i)[1];
        }

        char[][] ans = new char[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '*') {
                    ans[i][j] = '*';
                } else if (map[i][j] == '.'){
                    int id = uf.find(i*m+j);
                    if (isume.contains(id)) {
                        ans[i][j] = '*';
                    } else {
                        ans[i][j] = '.';
                    }
                }
            }
        }

        out.println(ume);
        for (int i = 0; i < n ; i++) {
            out.println(String.valueOf(ans[i]));
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
