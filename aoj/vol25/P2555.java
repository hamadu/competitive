package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2555 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int m = in.nextInt();
        int n = in.nextInt();
        int[][][] cond = new int[m][n][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                cond[i][j][0] = 0;
                cond[i][j][1] = 1000;
            }
            int k = in.nextInt();
            for (int j = 0; j < k ; j++) {
                int type = in.nextInt()-1;
                String q = in.nextToken();
                int d = in.nextInt();
                if (q.equals(">=")) {
                    cond[i][type][0] = Math.max(cond[i][type][0], d);
                } else {
                    cond[i][type][1] = Math.min(cond[i][type][1], d);
                }
            }
        }
        out.println(solve(cond) ? "Yes" : "No");



        out.flush();
    }

    @SuppressWarnings("unchecked")
    private static boolean solve(int[][][] cond) {
        int n = cond.length;
        int m = cond[0].length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (cond[i][j][0] > cond[i][j][1]) {
                    return false;
                }
            }
        }

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                boolean needToGo = false;
                for (int k = 0; k < m ; k++) {
                    if (cond[i][k][1] < cond[j][k][0]) {
                        needToGo = true;
                    }
                }
                if (needToGo) {
                    graph[i].add(j);
                }
            }
        }

        return toposort(graph) != null;
    }

    // O(n)
    // ループを含むならnull
    // int[] に順番が返る
    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                res[idx++] = i;
            }
        }
        for (int i = 0 ; i < idx ; i++) {
            for (int t : graph[res[i]]) {
                in[t]--;
                if (in[t] == 0) {
                    res[idx++] = t;
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
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
