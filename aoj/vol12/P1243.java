package aoj.vol12;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/07/30.
 */
public class P1243 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                int id = i*3+j;
                Arrays.fill(move[id], -1);
                for (int d = 0; d < 9 ; d++) {
                    int ti = i+dy[d];
                    int tj = j+dx[d];
                    if (ti < 0 || tj < 0 || ti >= 3 || tj >= 3) {
                        continue;
                    }
                    int tid = ti*3+tj;
                    move[id][d] = tid;
                }
                for (int k = 0; k < 4; k++) {
                    int ti = i+cy[k];
                    int tj = j+cx[k];
                    mask[id] |= 1<<(ti*4+tj);
                }
            }
        }

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] days = new int[n][16];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 16; j++) {
                    days[i][j] = in.nextInt();
                }
            }
            out.println(solve(days) ? 1 : 0);
        }

        out.flush();
    }

    @SuppressWarnings("unchecked")
    private static boolean solve(int[][] days) {
        int n = days.length;
        int[] daymask = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 16 ; j++) {
                if (days[i][j] == 1) {
                    daymask[i] |= 1<<j;
                }
            }
        }

        Set<Integer>[] visited = new Set[2];
        for (int i = 0; i < 2 ; i++) {
            visited[i] = new HashSet<Integer>();
        }
        visited[0].add(encode(4, 0));

        for (int i = 0; i < n; i++) {
            int fi = i%2;
            int ti = 1-fi;
            visited[ti].clear();
            for (Integer code : visited[fi]) {
                State stat = decode(code);

                int needCover = (1<<16)-1;
                if (i >= 5) {
                    int nid = stat.nid;
                    for (int prev = 4 ; prev >= 0 ; prev--) {
                        needCover &= ~mask[nid];
                        nid = move[nid][rev[stat.moves[prev]]];
                        needCover &= ~mask[nid];
                    }
                }
                for (int d = 0; d < 9 ; d++) {
                    if (move[stat.nid][d] == -1) {
                        continue;
                    }
                    int tid = move[stat.nid][d];
                    if (i >= 5 && (needCover & (~mask[tid])) >= 1) {
                        continue;
                    }
                    if ((daymask[i] & mask[stat.nid]) >= 1) {
                        continue;
                    }

                    int tmove = (stat.moveCode >> 4) | (d << 16);
                    int tcode = encode(tid, tmove);
                    if (visited[ti].contains(tcode)) {
                        continue;
                    }
                    visited[ti].add(tcode);
                }
            }
        }
        return visited[n%2].size() >= 1;
    }

    static class State {
        int nid;
        int[] moves;
        int moveCode;

        State(int id, int mc, int[] mv) {
            nid = id;
            moveCode = mc;
            moves = mv;
        }
    }

    static State decode(int code) {
        int cid = code & 15;
        int[] moves = new int[5];
        for (int i = 0; i < 5 ; i++) {
            moves[i] = (code >> (4+i*4)) & 15;
        }
        return new State(cid, (code >> 4), moves);
    }

    static int encode(int cid, int moveCode) {
        return cid | (moveCode << 4);
    }

    static int[] mask = new int[9];
    static int[][] move = new int[9][9];


    static int[] cx = {0, 1, 0, 1};
    static int[] cy = {0, 0, 1, 1};

    static int[] dx =  {-1, 0, 0, 1, -2, 0, 0, 2, 0};
    static int[] dy =  {0, 1, -1, 0, 0, 2, -2, 0, 0};
    static int[] rev = {3, 2, 1, 0, 7, 6, 5, 4, 8};

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
