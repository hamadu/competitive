package codeforces.cf3xx.cr325.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/10/12.
 */
public class B {
    private static final int INF = 100000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        while (--t >= 0) {
            int n = in.nextInt();
            int k = in.nextInt();
            char[][] map = new char[3][n];
            for (int i = 0; i < 3; i++) {
                map[i] = in.nextToken().toCharArray();
            }
            if (isOK(map, k)) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
        out.flush();
    }

    private static boolean isOK(char[][] map, int k) {
        int n = map[0].length;
        int sy = -1;
        int sx = 0;
        for (int i = 0; i < 3 ; i++) {
            if (map[i][0] == 's') {
                sy = i;
            }
        }
        
        int[][] trains = new int[26][3];
        for (int i = 0; i < 26 ; i++) {
            Arrays.fill(trains[i], INF);
            trains[i][2] = -INF;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n ; j++) {
                if ('A' <= map[i][j] && map[i][j] <= 'Z') {
                    int id = map[i][j] - 'A';
                    trains[id][0] = i;
                    trains[id][1] = Math.min(trains[id][1], j);
                    trains[id][2] = Math.max(trains[id][2], j);
                }
            }
        }

        boolean[][] visited = new boolean[3][n];
        Queue<Integer> q = new ArrayBlockingQueue<>(100000);
        q.add(sy);
        q.add(sx);
        while (q.size() >= 1) {
            int ny = q.poll();
            int nx = q.poll();
            int time = nx;
            int tx = nx+1;
            if (tx >= n) {
                continue;
            }

            boolean canGo = true;
            for (int i = 0; i < 26 ; i++) {
                if (trains[i][0] == INF) {
                    continue;
                }
                if (trains[i][0] == ny) {
                    int fr = trains[i][1] - time * 2;
                    int to = trains[i][2] - time * 2;
                    if (fr <= tx && tx <= to) {
                        canGo = false;
                    }
                }
            }
            if (!canGo) {
                continue;
            }
            for (int d = -1 ; d <= 1; d++) {
                int ty = ny + d;
                if (ty < 0 || ty >= 3) {
                    continue;
                }
                if (visited[ty][tx]) {
                    continue;
                }
                boolean hit = false;
                for (int i = 0; i < 26 ; i++) {
                    if (trains[i][0] == INF) {
                        continue;
                    }
                    if (trains[i][0] == ty) {
                        int fr = trains[i][1] - time * 2;
                        int to = trains[i][2] - time * 2;
                        for (int c = 0; c <= 2; c++) {
                            int tfr = fr - c;
                            int tto = to - c;
                            if (tfr <= tx && tx <= tto) {
                                hit = true;
                            }
                        }
                    }
                }
                if (!hit) {
                    visited[ty][tx] = true;
                    q.add(ty);
                    q.add(tx);
                }
            }
        }
        for (int i = 0; i < 3 ; i++) {
            if (visited[i][n-1]) {
                return true;
            }
        }
        return false;
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
