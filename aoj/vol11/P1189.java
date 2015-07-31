package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P1189 {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        dig();

        while (true) {
            int limit = in.nextInt();
            int start = in.nextInt();
            if (limit + start == 0) {
                break;
            }
            int[] pair = solve(limit, start);
            out.println(String.format("%d %d", pair[0], pair[1]));
        }


        out.flush();
    }

    private static int[] solve(int limit, int start) {
        for (int i = 0; i < PER ; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[num[start][0]][num[start][1]] = isp[start] ? 1 : 0;
        for (int i = num[start][0] ; i < dp.length-1 ; i++) {
            for (int j = 0; j < PER ; j++) {
                if (dp[i][j] == -1 || holes[i][j] > limit) {
                    continue;
                }
                for (int k = -1; k <= 1 ; k++) {
                    int ti = i+1;
                    int tj = j+k;
                    if (tj < 0 || tj >= PER || holes[ti][tj] > limit) {
                        continue;
                    }
                    int nu = holes[ti][tj];
                    dp[ti][tj] = Math.max(dp[ti][tj], dp[i][j]+(isp[nu] ? 1 : 0));
                }
            }
        }

        int best = 0;
        int bestNum = 0;
        for (int i = 0; i < PER ; i++) {
            for (int j = 0; j < PER ; j++) {
                if (holes[i][j] > limit || !isp[holes[i][j]]) {
                    continue;
                }
                if (dp[i][j] > best) {
                    best = dp[i][j];
                    bestNum = holes[i][j];
                } else if (dp[i][j] == best && bestNum < holes[i][j]) {
                    bestNum = holes[i][j];
                }
            }
        }
        if (best == 0) {
            bestNum = 0;
        }
        return new int[]{best, bestNum};
    }

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static final int PER = 2000;

    static int[][] dp = new int[PER][PER];

    static boolean[] isp;
    static int[][] num;
    static int[][] holes;

    static void dig() {
        isp = generatePrimes(1000010);
        num = new int[1000010][2];
        holes = new int[PER][PER];
        for (int i = 0; i < PER ; i++) {
            Arrays.fill(holes[i], INF);
        }

        int ny = PER / 2;
        int nx = PER / 2;
        int dir = 0;
        for (int d = 1 ; d <= 1000000 ; d++) {
            if (ny >= PER || nx >= PER) {
                debug(ny, nx, d);
            }
            num[d][0] = ny;
            num[d][1] = nx;
            holes[ny][nx] = d;
            int pdir = (dir + 1) % 4;
            if (holes[ny+dy[pdir]][nx+dx[pdir]] == INF) {
                dir = pdir;
            }
            ny = ny+dy[dir];
            nx = nx+dx[dir];
        }
    }

    static boolean[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }
        return isp;
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
