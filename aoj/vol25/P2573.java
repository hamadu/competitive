package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/07/24.
 */
public class P2573 {
    static final double EPS = 1e-9;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            char[][] start = new char[n][];
            for (int i = 0; i < n ; i++) {
                start[i] = in.nextToken().toCharArray();
            }
            char[][] goal = new char[n][];
            for (int i = 0; i < n ; i++) {
                goal[i] = in.nextToken().toCharArray();
            }
            out.println(String.format("%.9f", solve(start, goal)));
        }

        out.flush();
    }


    private static double solve(char[][] start, char[][] goal) {
        int n = start.length;
        int m = start[0].length;

        idmap.clear();
        id = 0;
        dfs(new char[n][m], goal, n, m, 0, 0, 0);

        int max = determineID(goal, goal)+1;
        int ope = n*m*2;

        double unit = 1.0d / (n * m * 2);
        double[][] A = new double[max][max];
        double[] B = new double[max];
        for (int id = 0; id < max-1 ; id++) {
            A[id][id] = 1.0d;
            for (int oi = 0; oi < ope ; oi++) {
                char[][] totbl = clone2(idTable[id]);
                int nm = oi / 2;
                int yi = nm / m;
                int xi = nm % m;
                for (int y = 0; y <= yi; y++) {
                    for (int x = 0; x <= xi ; x++) {
                        if (oi % 2 == 0) {
                            totbl[y][x] = 'B';
                        } else {
                            totbl[y][x] = 'W';
                        }
                    }
                }
                int toID = determineID(totbl, goal);
                A[id][toID] -= unit;
                B[id] += (xi + 1) * (yi + 1) * unit;
            }
        }
        A[max-1][max-1] = 1.0d;

        double[] ret = hakidasi(A, B);
        return ret[determineID(start, goal)];
    }

    public static double[] hakidasi(double[][] r1, double[] r2) {
        int len = r1.length;
        double[][] B = new double[len][len+1];
        for (int i = 0 ; i < len ; i++) {
            for (int j = 0 ; j < len ; j++) {
                B[i][j] = r1[i][j];
            }
        }
        for (int i = 0 ; i < len ; i++) {
            B[i][len] = r2[i];
        }

        for (int i = 0 ; i < len ; i++) {
            int pv = i;
            for (int j = i ; j < len ; j++) {
                if (Math.abs(B[j][i]) > Math.abs(B[pv][i])) {
                    pv = j;
                }
            }
            double[] tmp = B[i].clone();
            B[i] = B[pv].clone();
            B[pv] = tmp;
            if (Math.abs(B[i][i]) < EPS) {
                // return new double[]{};
            }

            for (int j = i + 1 ; j <= len ; j++) {
                B[i][j] /= B[i][i];
            }
            for (int j = 0 ; j < len ; j++) {
                if (i != j) {
                    for (int k = i + 1 ; k <= len ; k++) {
                        B[j][k] -= B[j][i] * B[i][k];
                    }
                }
            }
        }

        double[] ret = new double[len];
        for (int i = 0 ; i < len ; i++) {
            ret[i] = B[i][len];
        }
        return ret;
    }

    static char[][] clone2(char[][] map) {
        int n = map.length;
        int m = map[0].length;
        char[][] x = new char[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                x[i][j] = map[i][j];
            }
        }
        return x;
    }

    static Map<Integer,Integer> idmap = new HashMap<Integer,Integer>();
    static char[][][] idTable = new char[255][][];
    static int id;

    private static void dfs(char[][] now, char[][] goal, int n, int m, int idx, int last, int code) {
        if (idx == m) {
            idmap.put(code, id);
            idTable[id] = now;
            id++;
            return;
        }
        for (int c = last; c <= n; c++) {
            char[][] to = clone2(now);
            for (int i = 0; i < c ; i++) {
                to[n-1-i][idx] = goal[n-1-i][idx];
            }
            dfs(to, goal, n, m, idx+1, c, code*10+c);
        }
    }

    private static int determineID(char[][] now, char[][] goal) {
        int n = now.length;
        int m = now[0].length;
        int code = 0;
        int max = n;
        int ci = 1;
        for (int j = m-1 ; j >= 0 ; j--) {
            int correct = 0;
            for (int i = n-1 ; i >= (n-1-max+1) ; i--) {
                if (now[i][j] == goal[i][j]) {
                    correct++;
                } else {
                    break;
                }
            }
            code += correct * ci;
            max = correct;
            ci *= 10;
        }
        return idmap.get(code);
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
