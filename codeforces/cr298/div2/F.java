package codeforces.cr298.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[] a = new int[n];
        int[] b = new int[m];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < m ; i++) {
            b[i] = in.nextInt();
        }

        char[][] ans = solve(a, b);
        for (char[] line : ans) {
            out.println(String.valueOf(line));
        }
        out.flush();
        out.flush();
    }

    private static char[][] solve(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        if (n == 1) {
            return solveOneRow(a, b);
        } else if (m == 1) {
            return solveOneColumn(a, b);
        }

        decodeTable = new int[8000][n];
        for (int i = 0 ; i < 8000 ; i++) {
            int ii = i;
            for (int j = 0 ; j < n ; j++) {
                decodeTable[i][j] = ii % 6;
                ii /= 6;
            }
        }

        int half = m / 2;

        int[] b1 = new int[half];
        int[] b2 = new int[m-half];
        for (int i = 0 ; i < half ; i++) {
            b1[i] = b[i];
        }
        for (int i = m-1 ; i >= half ; i--) {
            b2[(m-1)-i] = b[i];
        }

        DPResult dp1 = solveColumns(n, b1);
        DPResult dp2 = solveColumns(n, b2);


        int last1 = -1;
        int p1 = -1;
        int last2 = -1;
        int p2 = -1;


        int pt = 1<<n;
        find: for (int last = 0 ; last < pt ; last++) {
            for (int p = 0 ; p < 8000 ; p++) {
                if (!dp1.dp[half][last][p]) {
                    continue;
                }

                int[] decoded = decodeTable[p];

                for (int alast = 0 ; alast < pt ; alast++) {
                    int code = 0;
                    for (int i = n-1 ; i >= 0 ; i--) {
                        code *= 6;
                        int pl = a[i] - decoded[i];
                        if ((last & (1<<i)) >= 1 && (alast & (1<<i)) >= 1) {
                            pl++;
                        }
                        code += pl;
                        if (pl < 0 || pl > 5) {
                            code = -1;
                            break;
                        }
                    }


                    if (code >= 0 && code < 8000 && dp2.dp[m-half][alast][code]) {
                        last1 = last;
                        p1 = p;
                        last2 = alast;
                        p2 = code;
                        break find;
                    }
                }
            }
        }

        char[][] r1 = rebuild(dp1, n, half, last1, p1);
        char[][] r2 = rebuild(dp2, n, m-half, last2, p2);

        char[][] ret = new char[n][m];
        for (int i = 0 ; i < n ; i++) {
            ret[i] = (
                    String.valueOf(r1[i]) +
                    (new StringBuilder(String.valueOf(r2[i])).reverse().toString())
            ).toCharArray();
        }
        return ret;
    }

    static int[][] decodeTable;

    static class DPResult {
        boolean[][][] dp;
        int[][][][] prev;
        DPResult(boolean[][][] a, int[][][][] b) {
            dp = a;
            prev = b;
        }
    }

    private static char[][] rebuild(DPResult res, int n, int m, int p2, int p3) {
        char[][] v = new char[n][m];

        int p1 = m;
        while (p1 >= 1) {
            for (int i = 0 ; i < n ; i++) {
                v[i][p1-1] = (p2 & (1<<i)) >= 1 ? '*' : '.';
            }
            int tp1 = res.prev[p1][p2][p3][0];
            int tp2 = res.prev[p1][p2][p3][1];
            int tp3 = res.prev[p1][p2][p3][2];
            p1 = tp1;
            p2 = tp2;
            p3 = tp3;
        }
        return v;
    }

    private static DPResult solveColumns(int n, int[] b1) {
        int m = b1.length;
        int pt = 1<<n;

        int[] cont = new int[pt];
        for (int i = 0 ; i < pt ; i++) {
            int on = 0;
            for (int j = 0 ; j < n ; j++) {
                if ((i & (1<<j)) >= 1) {
                    if (on == 0) {
                        cont[i]++;
                    }
                    on = 1;
                } else {
                    on = 0;
                }
            }
        }



        boolean[][][] dp = new boolean[m+1][pt][8000];
        int[][][][] prev = new int[m+1][pt][8000][];
        dp[0][0][0] = true;
        for (int i = 0 ; i < m ; i++) {
            for (int p = 0 ; p < pt ; p++) {
                for (int st = 0 ; st < 8000 ; st++) {
                    if (!dp[i][p][st]) {
                        continue;
                    }
                    for (int v = 0 ; v < pt ; v++) {
                        if (b1[i] != cont[v]) {
                            continue;
                        }

                        int encoded = 0;
                        for (int d = n-1 ; d >= 0 ; d--) {
                            encoded *= 6;
                            if ((p & (1<<d)) == 0 && (v & (1<<d)) >= 1) {
                                encoded += decodeTable[st][d] + 1;
                            } else {
                                encoded += decodeTable[st][d];
                            }
                        }
                        if (!dp[i+1][v][encoded]) {
                            dp[i + 1][v][encoded] = true;
                            prev[i + 1][v][encoded] = new int[]{i, p, st};
                        }
                    }
                }
            }
        }
        return new DPResult(dp, prev);
    }

    private static char[][] solveOneRow(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        char[][] ret = new char[n][m];
        for (int i = 0 ; i < m ; i++) {
            ret[0][i] = b[i] == 1 ? '*' : '.';
        }
        return ret;
    }

    private static char[][] solveOneColumn(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        char[][] ret = new char[n][m];
        for (int i = 0 ; i < n ; i++) {
            ret[i][0] = a[i] == 1 ? '*' : '.';
        }
        return ret;
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



