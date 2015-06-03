package codeforces.cr170.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/02.
 */
public class E {
    static final double EPS = 1e-9;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] cd = new int[n][2];
        for (int i = 0; i < n ; i++) {
            cd[i][0] = in.nextInt();
            cd[i][1] = in.nextInt();
        }

        double INF = 10000000;
        double[][] table = new double[n][2*n];
        for (int i = 0 ; i < n ; i++) {
            Arrays.fill(table[i], INF);
        }

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (cd[i][1] > cd[j][1]) {
                    // have parent!
                    int dx = Math.abs(cd[i][0] - cd[j][0]);
                    int dy = Math.abs(cd[i][1] - cd[j][1]);
                    double cost = Math.sqrt(dx * dx + dy * dy);
                    table[j][i*2] = table[j][i*2+1] = cost;
                }
            }
        }

        int[] matching = hungarian(table);
        double cost = 0;
        for (int i = 0; i < n ; i++) {
            cost += table[i][matching[i]];
        }

        // root
        cost -= INF;

        if (cost >= INF) {
            out.println(-1);
        } else {
            out.println(cost);
        }
        out.flush();
    }

    public static int[] hungarian(double[][] table) {
        int n = table.length;
        int m = table[0].length;
        int[] toright = new int[n];
        int[] toleft = new int[m];
        double[] ofsleft = new double[n];
        double[] ofsright = new double[m];
        Arrays.fill(toright, -1);
        Arrays.fill(toleft, -1);

        for (int r = 0 ; r < n ; r++) {
            boolean[] left = new boolean[n];
            boolean[] right = new boolean[m];
            int[] trace = new int[m];
            int[] ptr = new int[m];
            Arrays.fill(trace, -1);
            Arrays.fill(ptr, r);
            left[r] = true;
            while (true) {
                double d = Double.MAX_VALUE;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j]) {
                        d = Math.min(d, table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]);
                    }
                }
                for (int i = 0 ; i < n ; i++) {
                    if (left[i]) {
                        ofsleft[i] -= d;
                    }
                }
                for (int j = 0 ; j < m ; j++) {
                    if (right[j]) {
                        ofsright[j] += d;
                    }
                }
                int b = -1;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j] && Math.abs(table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]) <= EPS) {
                        b = j;
                    }
                }
                trace[b] = ptr[b];
                int c = toleft[b];
                if (c < 0) {
                    while (b >= 0) {
                        int a = trace[b];
                        int z = toright[a];
                        toleft[b] = a;
                        toright[a] = b;
                        b = z;
                    }
                    break;
                }
                right[b] = true;
                left[c] = true;
                for (int j = 0 ; j < m ; j++) {
                    if (table[c][j] + ofsleft[c] + ofsright[j] < table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]) {
                        ptr[j] = c;
                    }
                }
            }
        }
        return toright;
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
