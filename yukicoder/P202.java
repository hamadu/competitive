package yukicoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by dhamada on 15/05/12.
 */
public class P202 {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        List<Integer>[][] bucket = new List[1001][1001];
        for (int i = 0 ; i < 1001 ; i++) {
            for (int j = 0 ; j < 1001 ; j++) {
                bucket[i][j] = new ArrayList<>();
            }
        }

        int cnt = 0;
        int n = in.nextInt();
        int[][] pos = new int[n][2];
        for (int i = 0 ; i < n ; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            pos[i][0] = x;
            pos[i][1] = y;

            int bx = x / 20;
            int by = y / 20;
            boolean conflict = false;
            sch: for (int xi = bx-1 ; xi <= bx+1 ; xi++) {
                for (int yi = by-1 ; yi <= by+1 ; yi++) {
                    if (xi < 0 || yi < 0 || xi >= 1001 || yi >= 1001) {
                        continue;
                    }
                    for (int pi : bucket[xi][yi]) {

                        if (conflict(pos[pi][0], pos[pi][1], x, y)) {
                            conflict = true;
                            break sch;
                        }
                    }
                }
            }
            if (!conflict) {
                cnt++;
                bucket[bx][by].add(i);
            }
        }

        out.println(cnt);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    static boolean conflict(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return (dx * dx + dy * dy) < 400;
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
