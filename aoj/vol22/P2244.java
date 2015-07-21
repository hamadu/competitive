package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/20.
 */
public class P2244 {
    static int[] dx = new int[256];
    static int[] dy  = new int[256];

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        dx['L'] = -1;
        dx['R'] = 1;
        dy['U'] = -1;
        dy['D'] = 1;

        while (true) {
            int hp = in.nextInt();
            int maxhp = in.nextInt();
            if (hp + maxhp == 0) {
                break;
            }
            int r = in.nextInt();
            int c = in.nextInt();
            int[][] map = new int[r][c];
            for (int i = 0; i < r; i++) {
                char[] line = in.nextToken().toCharArray();
                for (int j = 0; j < c ; j++) {
                    map[i][j] = line[j] - 'A';
                }
            }
            int T = in.nextInt();
            int[] trap = new int[26];
            for (int i = 0; i < T; i++) {
                int d = in.nextChar()-'A';
                int dmg = in.nextInt();
                trap[d] = dmg;
            }

            int seq = in.nextInt();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < seq ; i++) {
                char d = in.nextChar();
                int nu = in.nextInt();
                for (int j = 0; j < nu ; j++) {
                    line.append(d);
                }
            }
            int P = in.nextInt();
            int[] po = new int[P];
            for (int i = 0; i < P ; i++) {
                po[i] = in.nextInt();
            }
            Arrays.sort(po);
            out.println(solve(map, trap, line.toString().toCharArray(), po, hp, maxhp) ? "YES" : "NO");
        }

        out.flush();
    }

    static int[][] recvptn = new int[2048][2048];
    static int[] recd = new int[2048];


    private static boolean solve(int[][] map, int[] trap, char[] moves, int[] po, int hp, int maxhp) {
        int h = map.length;
        int w = map[0].length;
        int n = moves.length;
        int p = po.length;
        int[][] dp = new int[n+1][1<<p];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = hp;


        Arrays.fill(recd, 0);

        int[] leftP = new int[1<<p];
        int sumP = 0;
        for (int i = 0; i < p ; i++) {
            sumP += po[i];
        }
        for (int ptn = 0; ptn < 1<<p; ptn++) {
            int recoverHP = 0;
            for (int i = 0; i < p; i++) {
                if ((ptn & (1<<i)) >= 1) {
                    recoverHP += po[i];
                }
            }
            leftP[ptn] = sumP - recoverHP;
            if (recoverHP < recd.length) {
                recvptn[recoverHP][recd[recoverHP]++] = ptn;
            }
        }

        int ny = 0;
        int nx = 0;
        for (int i = 0; i < n ; i++) {
            ny += dy[moves[i]];
            nx += dx[moves[i]];
            int dmg = trap[map[ny][nx]];
            for (int ptn = 0; ptn < (1<<p) ; ptn++) {
                if (dp[i][ptn] <= 0) {
                    continue;
                }
                int tohp = dp[i][ptn] - dmg;
                if (tohp >= 1) {
                    dp[i+1][ptn] = Math.max(dp[i+1][ptn], tohp);
                    continue;
                }
                if (maxhp - dmg <= 0 || Math.min(maxhp, dp[i][ptn] + leftP[ptn]) - dmg <= 0) {
                    continue;
                }

                int need = -tohp + 1;
                for (int r = need ; r <= maxhp - dp[i][ptn] + 1000 ; r++) {
                    boolean found = false;
                    int thp = Math.min(maxhp, dp[i][ptn] + r) - dmg;
                    for (int rd = 0 ; rd < recd[r] ; rd++) {
                        if ((recvptn[r][rd] & ptn) == 0) {
                            found = true;
                            int tptn = ptn | recvptn[r][rd];
                            dp[i+1][tptn] = Math.max(dp[i+1][tptn], thp);
                        }
                    }
                    if (found) {
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < (1<<p) ; i++) {
            if (dp[n][i] >= 1) {
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
