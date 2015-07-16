package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/16.
 */
public class P1337 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] rect = new int[n][4];
            for (int i = 0; i < n ; i++) {
                // left
                rect[i][1] = in.nextInt();
                // top
                rect[i][2] = in.nextInt();
                // right
                rect[i][3] = in.nextInt();
                // bottom
                rect[i][0] = in.nextInt();
            }
            out.println(solve(rect));
        }

        out.flush();
    }

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    private static int solve(int[][] rect) {
        int n = rect.length;


        Set<Integer> yset = new HashSet<Integer>();
        Set<Integer> xset = new HashSet<Integer>();
        for (int i = 0 ; i < n ; i++) {
            for (int d = 0 ; d <= 1 ; d++) {
                yset.add(rect[i][d*2]-1);
                yset.add(rect[i][d*2]);
                xset.add(rect[i][d*2+1]-1);
                xset.add(rect[i][d*2+1]);
            }
        }

        int[] ys = new int[yset.size()];
        int yi = 0;
        for (int y : yset) {
            ys[yi++] = y;
        }
        Arrays.sort(ys);
        int[] xs = new int[xset.size()];
        int xi = 0;
        for (int x : xset) {
            xs[xi++] = x;
        }
        Arrays.sort(xs);

        Map<Integer,Integer> ymap = new HashMap<Integer,Integer>();
        Map<Integer,Integer> xmap = new HashMap<Integer,Integer>();
        for (int i = 0 ; i < yi ; i++) {
            ymap.put(ys[i], i);
        }
        for (int i = 0 ; i < xi ; i++) {
            xmap.put(xs[i], i);
        }

        int GETA = 5;
        int[][] crect = new int[n][4];
        for (int i = 0 ; i < n ; i++) {
            for (int d = 0 ; d <= 1 ; d++) {
                crect[i][d*2] = ymap.get(rect[i][d*2])+GETA;
                crect[i][d*2+1] = xmap.get(rect[i][d*2+1])+GETA;
            }
        }

        int[][][] graph = new int[210][210][4];
        for (int ri = 0; ri < n; ri++) {
            int B = crect[ri][0];
            int T = crect[ri][2];
            int L = crect[ri][1];
            int R = crect[ri][3];
            for (int i = B ; i < T ; i++) {
                graph[i][L-1][0] = graph[i][R-1][0] = 1;
                graph[i][L][2] = graph[i][R][2] = 1;
            }
            for (int i = L ; i < R ; i++) {
                graph[B-1][i][1] = graph[T-1][i][1] = 1;
                graph[B][i][3] = graph[T][i][3] = 1;
            }
        }

        int cnt = 0;
        boolean[][] visited = new boolean[210][210];
        for (int i = 0; i < 210 ; i++) {
            for (int j = 0; j < 210 ; j++) {
                if (visited[i][j]) {
                    continue;
                }
                cnt++;
                int qh = 0;
                int qt = 0;
                que[qh++] = i;
                que[qh++] = j;
                while (qt < qh) {
                    int ny = que[qt++];
                    int nx = que[qt++];
                    for (int d = 0; d < 4 ; d++) {
                        if (graph[ny][nx][d] == 1) {
                            continue;
                        }
                        int ty = ny + dy[d];
                        int tx = nx + dx[d];
                        if (tx < 0 || ty < 0 || tx >= 210 || ty >= 210 || visited[ty][tx]) {
                            continue;
                        }
                        visited[ty][tx] = true;
                        que[qh++] = ty;
                        que[qh++] = tx;
                    }
                }
            }
        }
        return cnt;
    }

    static int[] que = new int[1000000];

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
