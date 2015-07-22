package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/22.
 */
@SuppressWarnings("unchecked")
public class P1321 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            char[][] map = new char[n][];
            for (int i = 0; i < n ; i++) {
                map[i] = in.nextToken().toCharArray();
            }
            out.println(solve(map));
        }

        out.flush();
    }

    static int[] dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    static int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

    private static int solve(char[][] map) {
        int n = map.length;
        int m = map[0].length;

        long id = 1L;
        deg = new long[n*m][16];
        numc = new int[15];
        goal = 0;
        dn = 0;
        nn = 0;


        ord = new int[n*m];
        boolean[][] added = new boolean[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if ('0' <= map[i][j] && map[i][j] <= '9') {
                    goal += id * (map[i][j] - '0');
                    numc[nn] = map[i][j] - '0';
                    for (int d = 0; d < 9 ; d++) {
                        int y = i + dy[d];
                        int x = j + dx[d];
                        if (y < 0 || x < 0 || y >= n || x >= m || map[y][x] == '.') {
                            continue;
                        }
                        deg[y*m+x][nn] += 1;
                        deg[y*m+x][15] += id;
                        if (!added[y][x]) {
                            added[y][x] = true;
                            ord[dn] = y*m+x;
                            dn++;
                        }
                    }
                    id *= 10L;
                    nn++;
                }
            }
        }
        min = Integer.MAX_VALUE;

        visited = new Map[dn];
        for (int i = 0; i < dn ; i++) {
            visited[i] = new HashMap<Long,Integer>();
        }

        dfs(0, 0, 0);

        return min;
    }

    static int nn;
    static int[] numc;
    static int dn;
    static long[][] deg;
    static int min;
    static long goal;
    static int[] ord;

    static Map<Long,Integer>[] visited;

    static void dfs(int idx, long stat, int val) {
        if (stat == goal) {
            min = Math.min(min, val);
            return;
        }
        if (idx == dn || stat > goal || val >= min) {
            return;
        }
        if (visited[idx].containsKey(stat) && visited[idx].get(stat) <= val) {
            return;
        }
        visited[idx].put(stat, val);

        int[] nu = new int[nn];
        long vstat = stat;
        for (int i = 0; i < nn; i++) {
            nu[i] = (int)(vstat % 10);
            vstat /= 10;
        }
        for (int i = 0; i < nn ; i++) {
            if (nu[i] > numc[i]) {
                return;
            }
        }
        for (int i = idx; i < dn ; i++) {
            for (int j = 0; j < nn ; j++) {
                nu[j] += deg[ord[i]][j];
            }
        }
        for (int i = 0; i < nn ; i++) {
            if (nu[i] < numc[i]) {
                return;
            }
        }

        dfs(idx+1, stat, val);
        dfs(idx+1, stat + deg[ord[idx]][15], val+1);
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
