package codeforces.cr301.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class C {
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        if (solve(in)) {
            out.println("YES");
        } else {
            out.println("NO");
        }
        out.flush();
    }

    static boolean solve(InputReader in) {
        int n = in.nextInt();
        int m = in.nextInt();

        char[][] map = new char[n][];
        for (int i = 0 ; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        int y1 = in.nextInt() - 1;
        int x1 = in.nextInt() - 1;
        int y2 = in.nextInt() - 1;
        int x2 = in.nextInt() - 1;

        // same
        if (x1 == x2 && y1 == y2) {
            boolean found = false;
            for (int d = 0; d < 4; d++) {
                int tx = x2 + dx[d];
                int ty = y2 + dy[d];
                if (tx < 0 || ty < 0 || tx >= m || ty >= n || map[ty][tx] == 'X') {
                    continue;
                }
                found = true;
            }
            return found;
        }

        // just go
        if (path(map, x1, y1, x2, y2)) {
            return true;
        }

        // jump and back
        if (map[y2][x2] == '.') {
            for (int d = 0; d < 4; d++) {
                int tx = x2 + dx[d];
                int ty = y2 + dy[d];
                if (tx < 0 || ty < 0 || tx >= m || ty >= n || map[ty][tx] == 'X') {
                    continue;
                }
                map[ty][tx] = 'X';
                map[y2][x2] = 'X';

                if (path(map, x1, y1, x2, y2)) {
                    return true;
                }

                map[ty][tx] = ',';
                map[y2][x2] = '.';
            }
        }

        return false;
    }

    static boolean path(char[][] map, int sx, int sy, int gx, int gy) {
        int n = map.length;
        int m = map[0].length;
        Queue<Integer> q = new ArrayBlockingQueue<>(n * m * 4);
        q.add(sx);
        q.add(sy);
        boolean[][] visited = new boolean[n][m];
        visited[sy][sx] = true;
        while (q.size() >= 1) {
            int x = q.poll();
            int y = q.poll();
            for (int d = 0 ; d < 4 ; d++) {
                int tx = x + dx[d];
                int ty = y + dy[d];
                if (tx == gx && ty == gy && map[ty][tx] == 'X') {
                    return true;
                }
                if (tx < 0 || ty < 0 || tx >= m || ty >= n || visited[ty][tx] || map[ty][tx] == 'X') {
                    continue;
                }
                q.add(tx);
                q.add(ty);
                visited[ty][tx] = true;
            }
        }
        return false;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
}



