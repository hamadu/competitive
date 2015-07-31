package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P2262 {
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int r = in.nextInt();
        int c = in.nextInt();
        char[][] program = new char[r][];
        for (int i = 0; i < r ; i++) {
            program[i] = in.nextToken().toCharArray();
        }

        int[][] chdir = new int[16][256];
        for (int i = 0; i < 16; i++) {
            Arrays.fill(chdir[i], -1);
        }
        for (int i = 0; i < 16 ; i++) {
            chdir[i]['<'] = 2;
            chdir[i]['>'] = 0;
            chdir[i]['^'] = 3;
            chdir[i]['v'] = 1;
            if (i == 0) {
                chdir[i]['_'] = 0;
                chdir[i]['|'] = 1;
            } else {
                chdir[i]['_'] = 2;
                chdir[i]['|'] = 3;
            }
        }

        boolean found = false;
        boolean[] visited = new boolean[1<<(10+2+4)];
        int[] que = new int[1<<(10+2+4)];
        int qh = 0;
        int qt = 0;
        int ini = encode(0, 0, 0, 0);
        que[qh++] = ini;
        visited[ini] = true;
        while (qt < qh) {
            int[] st = decode(que[qt++]);
            int y = st[0];
            int x = st[1];
            int dir = st[2];
            int mem = st[3];
            char op = program[y][x];

            int tdir = dir;
            if (chdir[mem][op] != -1) {
                tdir = chdir[mem][op];
            }
            if (op == '@') {
                found = true;
                break;
            }

            int tmem = mem;
            if ('0' <= op && op <= '9') {
                tmem = op - '0';
            } else if (op == '+') {
                tmem = (mem + 1) % 16;
            } else if (op == '-') {
                tmem = (mem + 15) % 16;
            }
            for (int d = 0; d < 4 ; d++) {
                if (op == '?' || tdir == d) {
                    int ty = (y + dy[d] + r) % r;
                    int tx = (x + dx[d] + c) % c;
                    int tstat = encode(ty, tx, d, tmem);
                    if (visited[tstat]) {
                        continue;
                    }
                    visited[tstat] = true;
                    que[qh++] = tstat;
                }
            }
        }
        out.println(found ? "YES" : "NO");
        out.flush();
    }

    static int[] decode(int stat) {
        int mem = stat & 15;
        int dir = (stat >> 4) & 3;
        int pos = (stat >> 6) & 1023;
        int x = pos & 31;
        int y = (pos >> 5) & 31;
        return new int[]{y, x, dir, mem};
    }

    static int encode(int y, int x, int dir, int mem) {
        int pos = (y<<5)+x;
        return (pos<<6) | (dir<<4) | mem;
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
