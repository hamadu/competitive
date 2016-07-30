package atcoder.other2016.tenka1.quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/07/30.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] edge = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                edge[i][j] = in.nextToken().toCharArray()[0]-'A';
            }
        }
        graph = buildGraph(n, edge);

        char[][] map = new char[100][100];
        pos = new int[n][2];
        for (int rt = 0 ; rt < n ; rt++) {
            dfs(rt, -1, 0, 0, -1, 1<<27);

            Map<Integer,Integer> ys = compress(pos, 0);
            Map<Integer,Integer> xs = compress(pos, 1);
            if (ys.size() >= 98 || xs.size() >= 98) {
                continue;
            }
            for (int i = 0; i < n ; i++) {
                pos[i][0] = ys.get(pos[i][0])+1;
                pos[i][1] = xs.get(pos[i][1])+1;
            }

            for (int i = 0; i < 100 ; i++) {
                Arrays.fill(map[i], '.');
            }
            for (int i = 0; i < n ; i++) {
                int y = pos[i][0];
                int x = pos[i][1];
                map[y][x] = (char)('A' + i);
            }
            for (int i = 0 ; i < n ; i++) {
                for (int j : graph[i]) {
                    if (pos[i][0] != pos[j][0]) {
                        int x = pos[i][1];
                        int fy = pos[i][0];
                        int ty = pos[j][0];
                        int dy = (ty-fy)/Math.abs(fy-ty);
                        fy += dy;
                        while (fy != ty) {
                            map[fy][x] = '|';
                            fy += dy;
                        }
                    } else {
                        int y = pos[i][0];
                        int fx = pos[i][1];
                        int tx = pos[j][1];
                        int dx = (tx-fx)/Math.abs(fx-tx);
                        fx += dx;
                        while (fx != tx) {
                            map[y][fx] = '-';
                            fx += dx;
                        }
                    }
                }
            }
            break;
        }

        out.println("100 100");
        for (int i = 0 ; i < 100 ; i++) {
            out.println(String.valueOf(map[i]));
        }
        out.flush();
    }


    static int[] DY = {0, -1, 0, 1};
    static int[] DX = {-1, 0, 1, 0};

    static Map<Integer,Integer> compress(int[][] p, int t) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0 ; i < p.length ; i++) {
            for (int d = -1 ; d <= 1 ; d++) {
                set.add(p[i][t]+d);
            }
        }
        List<Integer> ll = new ArrayList<>();
        ll.addAll(set);
        Collections.sort(ll);
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < ll.size() ; i++) {
            map.put(ll.get(i), i);
        }
        return map;
    }

    static void dfs(int now, int par, int py, int px, int pdir, int len) {
        pos[now][0] = py;
        pos[now][1] = px;

        int used = pdir == -1 ? 0 : 1<<pdir;
        for (int to : graph[now]) {
            if (par == to) {
                continue;
            }
            for (int d = 0 ; d < 4 ; d++) {
                if ((used & (1<<d)) >= 1) {
                    continue;
                }
                used |= 1<<d;
                dfs(to, now, py + DY[d] * len, px + DX[d] * len, (d+2)%4, len>>1);
                break;
            }
        }
    }


    static int[][] pos;
    static int[][] graph;
    static char[][] possibleMap;

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
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


    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        Queue<Integer> q = new PriorityQueue<>();


        int[] res = new int[n];
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                q.add(i);
            }
        }

        int idx = 0;
        while (q.size() >= 1) {
            int now = q.poll();
            res[idx++] = now;
            for (int t : graph[now]) {
                in[t]--;
                if (in[t] == 0) {
                    q.add(t);
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
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
