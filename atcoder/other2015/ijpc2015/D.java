package atcoder.other2015.ijpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/10/18.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        main2();

        int w = in.nextInt();
        int h = in.nextInt();
        int r = in.nextInt()-1;
        if (w > 10 || h > 10) {
            throw new RuntimeException("i have no idea");
        }

        int[][] data = new int[h][w];
        int a = in.nextInt();
        for (int i = 0; i < a ; i++) {
            int x = in.nextInt()-1;
            int b = in.nextInt();
            for (int j = 0; j < b ; j++) {
                data[x][in.nextInt()-1] = 1;
            }
        }
        boolean[] res = solve(data, r);
        int cnt = 0;
        for (int i = 0; i < res.length ; i++) {
            if (res[i]) {
                cnt++;
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(cnt);
        for (int i = 0; i < res.length ; i++) {
            if (res[i]) {
                builder.append(' ');
                builder.append(i+1);
            }
        }

        out.println(builder.toString());
        out.flush();
    }

    static int[] dx = {-1, 0, 1, 0, 0};
    static int[] dy = {0, 1, 0, -1, 0};

    static boolean[] solve(int[][] map, int r) {
        int h = map.length;
        int w = map[0].length;
        for (int ptn = 0; ptn < (1<<w) ; ptn++) {
            int[][] pw = new int[h][];
            for (int j = 0; j < h ; j++) {
                pw[j] = map[j].clone();
            }

            boolean[][] pushed = new boolean[h][w];

            for (int j = 0; j < w ; j++) {
                if ((ptn & (1<<j)) >= 1) {
                    pushed[0][j] = true;
                    for (int d = 0; d < 5; d++) {
                        int ti = dy[d];
                        int tj = j + dx[d];
                        if (ti < 0 || ti >= h || tj < 0 || tj >= w) {
                            continue;
                        }
                        pw[ti][tj] = 1 - pw[ti][tj];
                    }
                }
            }

            for (int i = 1; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    if (pw[i-1][j] == 1) {
                        pushed[i][j] = true;
                        for (int d = 0; d < 5; d++) {
                            int ti = i + dy[d];
                            int tj = j + dx[d];
                            if (ti < 0 || ti >= h || tj < 0 || tj >= w) {
                                continue;
                            }
                            pw[ti][tj] = 1 - pw[ti][tj];
                        }
                    }
                }
            }

            boolean isOK = true;
            for (int i = 0 ; i < h ; i++) {
                for (int j = 0; j < w; j++) {
                    if (pw[i][j] == 1) {
                        isOK = false;
                        break;
                    }
                }
            }

            if (isOK) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w ; j++) {
                        System.out.print(pushed[i][j] ? '*' : '.');
                    }
                    System.out.println();
                }
                System.out.println("--");
                //return pushed[r];
            }
        }
        return null;
    }

    static void main2() {
        int[][] map = new int[18][18];

        map[1][1] = 1;
        solve(map, 0);

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
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
