package atcoder.other2015.xmas2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2015/12/24.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] pq = new int[m][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                pq[i][j] = in.nextInt()-1;
            }
        }
        List<int[]> answer;
        if (n <= 4000) {
            answer = solveSmall(n, pq);
        } else {
            answer = solveLarge(n, pq);
        }
        if (answer.size() != n-1) {
            out.println("No");
        } else {
            out.println("Yes");
            for (int[] r : answer) {
                out.println(String.format("%d %d", r[0]+1, r[1]+1));
            }
        }
        out.flush();
    }

    private static List<int[]> solveLarge(int n, int[][] pq) {
        List<int[]> ret = new ArrayList<>();
        int m = pq.length;

        int[][] deg = new int[n][2];
        for (int i = 0; i < n ; i++) {
            deg[i][0] = i;
            deg[i][1] = n-1;
        }
        for (int i = 0; i < m ; i++) {
            int a = pq[i][0];
            int b = pq[i][1];
            deg[a][1]--;
            deg[b][1]--;
        }
        int[][] fbd = new int[n][];
        for (int i = 0; i < n ; i++) {
            fbd[i] = new int[(n-1)-deg[i][1]];
        }
        int[] fbdi = new int[n];
        for (int i = 0; i < m ; i++) {
            int a = pq[i][0];
            int b = pq[i][1];
            fbd[a][fbdi[a]++] = b;
            fbd[b][fbdi[b]++] = a;
        }
        for (int i = 0; i < n ; i++) {
            Arrays.sort(fbd[i]);
        }

        Arrays.sort(deg, (o1, o2) -> o1[1] - o2[1]);

        UnionFind uf = new UnionFind(n);

        boolean[] done = new boolean[n];
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        que[qh++] = deg[0][0];
        done[deg[0][0]] = true;

        while (qt < qh) {
            int i = que[qt++];
            int wh = 0;
            for (int j = 0; j < n ; j++) {
                if (wh < fbd[i].length && j == fbd[i][wh]) {
                    wh++;
                } else {
                    if (!uf.issame(i, j)) {
                        if (!done[j]) {
                            done[j] = true;
                            que[qh++] = j;
                        }
                        uf.unite(i, j);
                        ret.add(new int[]{i, j});
                        if (ret.size() == n-1) {
                            return ret;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private static List<int[]> solveSmall(int n, int[][] pq) {
        List<int[]> ret = new ArrayList<>();
        int m = pq.length;
        int[][] forbidden = new int[n][n];
        for (int i = 0; i < m ; i++) {
            int a = pq[i][0];
            int b = pq[i][1];
            forbidden[a][b] = forbidden[b][a] = 1;
        }
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                if (!uf.issame(i, j) && forbidden[i][j] == 0) {
                    uf.unite(i, j);
                    ret.add(new int[]{i, j});
                }
            }
        }
        return ret;
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
