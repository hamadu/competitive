package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1316 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int h = in.nextInt();
            int w = in.nextInt();
            if (h == 0 && w == 0) {
                break;
            }
            char[][] map = new char[h][];
            for (int i = 0; i < h ; i++) {
                map[i] = in.nextToken().toCharArray();
            }
            out.println(solve(map));
        }

        out.flush();
    }

    static int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

    private static String solve(char[][] map) {
        int n = map.length;
        int m = map[0].length;

        String best = "";
        Set<String> seen = new HashSet<String>();

        int[][] visited = new int[n][m];
        int vid = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                for (int d = 0 ; d < 8 ; d++) {
                    vid++;
                    StringBuilder spell = new StringBuilder();
                    int ny = i;
                    int nx = j;
                    while (visited[ny][nx] != vid) {
                        spell.append(map[ny][nx]);
                        String str = spell.toString();
                        if (best.length() <= str.length()) {
                            if (seen.contains(str)) {
                                if (best.length() == str.length()) {
                                    if (str.compareTo(best) < 0) {
                                        best = str;
                                    }
                                } else {
                                    best = str;
                                }
                            } else {
                                seen.add(str);
                            }
                        }
                        visited[ny][nx] = vid;
                        ny = (ny + dy[d] + n) % n;
                        nx = (nx + dx[d] + m) % m;
                    }
                }
            }
        }
        if (best.equals("") || best.length() <= 1) {
            return "0";
        }
        return best;
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
