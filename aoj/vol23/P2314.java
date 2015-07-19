package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/07/18.
 */
public class P2314 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        h = in.nextInt();
        w = in.nextInt();
        map = new char[h][];
        for (int i = 0; i < h ; i++) {
            map[i] = in.nextToken().toCharArray();
        }
        if (w < h) {
            int t = h;
            h = w;
            w = t;
            char[][] nmap = new char[h][w];
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    nmap[i][j] = map[j][i];
                }
            }
            map = nmap;
        }

        memo = new Map[h][w];
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                memo[i][j] = new HashMap<Long,Integer>();
            }
        }


        int[] r = new int[w];
        Arrays.fill(r, -1);
        int ret = dfs(0, 0, encode(r));
        out.println(ret < 0 ? -1 : ret);
        out.flush();
    }

    static long __f = 0;

    static Map<Long,Integer>[][] memo;

    static long encode(int[] ptn) {
        long ret = 0;
        for (int i = 0; i < w ; i++) {
            ret |= (ptn[i]+1L)<<(i*3L);
        }
        return ret;
    }
    
    static int[] decode(long eptn) {
        int[] dec = new int[w];
        for (int i = 0; i < w ; i++) {
            dec[i] = (int)((eptn & 7) - 1);
            eptn >>= 3;
        }
        return dec;
    }

    static int dfs(int i, int j, long eptn) {
        if (i == h) {
            int[] ptn = decode(eptn);
            for (int k = 0; k < w ; k++) {
                if (ptn[k] >= 2) {
                    return -INF;
                }
            }
            return 0;
        }
        if (j == w) {
            return dfs(i+1, 0, eptn);
        }
        if (memo[i][j].containsKey(eptn)) {
            return memo[i][j].get(eptn);
        }

        int[] ptn = decode(eptn);
        boolean canWhite = true;
        boolean canBlack = true;
        if (map[i][j] == '#') {
            canWhite = false;
        } else if (map[i][j] == '.') {
            canBlack = false;
        }
        if (i >= 1 && ptn[j] == 0) {
            canWhite = false;
        }
        if (j >= 1 && ptn[j-1] == 0) {
            canWhite = false;
        }
        if (j >= 1 && ptn[j] >= 1 && ptn[j] == ptn[j-1]) {
            canBlack = false;
        }
        if (ptn[j] >= 1) {
            int upc = 0;
            for (int k = 0; k < w ; k++) {
                if (ptn[j] == ptn[k]) {
                    upc++;
                }
            }
            if (upc == 1) {
                canWhite = false;
            }
        }


        int max = -INF;
        if (canWhite) {
            int[] toptn = ptn.clone();
            toptn[j] = 0;
            max = Math.max(max, dfs(i, j+1, encode(toptn)));
        }
        if (canBlack) {
            int col = -1;
            int anti = -1;
            int[] toptn = ptn.clone();
            int pm = 1;
            for (int k = 0; k < w; k++) {
                if (ptn[k] >= 1) {
                    pm = Math.max(pm, ptn[k] + 1);
                }
            }
            if (j == 0) {
                if (ptn[j] >= 1) {
                    col = ptn[j];
                } else {
                    col = pm;
                }
            } else {
                if (ptn[j] >= 1 && ptn[j-1] >= 1) {
                    col = Math.min(ptn[j], ptn[j-1]);
                    anti = Math.max(ptn[j], ptn[j - 1]);
                } else if (ptn[j] >= 1 || ptn[j-1] >= 1) {
                    col = Math.max(ptn[j], ptn[j-1]);
                } else {
                    col = pm;
                }
            }
            if (anti != -1) {
                for (int k = 0; k < w ; k++) {
                    if (toptn[k] == anti) {
                        toptn[k] = col;
                    }
                }
            }
            toptn[j] = col;

            int[] cnv = new int[12];
            int cid = 1;
            Arrays.fill(cnv, -1);
            for (int k = 0; k < w; k++) {
                if (toptn[k] >= 1 && cnv[toptn[k]] == -1) {
                    cnv[toptn[k]] = cid;
                    cid++;
                }
            }
            for (int k = 0; k < w; k++) {
                if (toptn[k] >= 1) {
                    toptn[k] = cnv[toptn[k]];
                }
            }

            max = Math.max(max, dfs(i, j+1, encode(toptn)) + 1);
        }
        memo[i][j].put(eptn, max);
        return max;
    }

    static int INF = 1000000;
    static int h;
    static int w;
    static char[][] map;

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
