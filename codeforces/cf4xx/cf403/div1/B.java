package codeforces.cf4xx.cf403.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] teams = new char[n][4];
        for (int i = 0; i < n ; i++) {
            char[] a = in.nextToken().toCharArray();
            char[] b = in.nextToken().toCharArray();
            for (int j = 0; j < 3 ; j++) {
                teams[i][j] = a[j];
            }
            teams[i][3] = b[0];
        }

        SAT2 sat = new SAT2(n);
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                if (teams[i][0] == teams[j][0] && teams[i][1] == teams[j][1]) {
                    // rule1: should not have same short name
                    for (int k = 2 ; k <= 3 ; k++) {
                        for (int l = 2 ; l <= 3; l++) {
                            if (teams[i][k] == teams[j][l]) {
                                int noti = i + (3 - k) * n;
                                int notj = j + (3 - l) * n;
                                sat.add(noti, notj);
                            }
                        }
                    }

                    // rule2: first options are same in both teams
                    if (teams[i][2] == teams[j][2]) {
                        sat.add(i, j+n);
                        sat.add(j, i+n);
                    }
                }
            }
        }

        boolean[] ans = sat.doit();
        if (ans == null) {
            out.println("NO");
        } else {
            out.println("YES");
            for (int i = 0; i < n ; i++) {
                out.println(String.format("%c%c%c", teams[i][0], teams[i][1], ans[i] ? teams[i][2] : teams[i][3]));
            }
        }
        out.flush();
    }

    /**
     * Solve 2-sat using SCC.
     */
    @SuppressWarnings("unchecked")
    public static class SAT2 {
        boolean[] visited;
        int[] nodeId;
        List<Integer> rev;

        int n;
        int vn;
        List<Integer>[] graph;
        List<Integer>[] revGraph;

        public SAT2(int maxN) {
            n = maxN;
            vn = n * 2;
            graph = new List[vn];
            revGraph = new List[vn];
            for (int i = 0; i < vn; i++) {
                graph[i] = new ArrayList<>();
                revGraph[i] = new ArrayList<>();
            }
        }

        public int not(int v) {
            return v >= n ? v - n : v + n;
        }

        public void add(int a, int b) {
            // a or b => [(not a) then b] and [(not b) then a]
            graph[not(a)].add(b);
            graph[not(b)].add(a);
            revGraph[b].add(not(a));
            revGraph[a].add(not(b));
        }

        public boolean[] doit() {
            visited = new boolean[vn];
            rev = new ArrayList<>();
            for (int i = 0; i< vn ; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }
            int id = 0;
            nodeId = new int[vn];
            visited = new boolean[vn];
            for (int i = rev.size()-1; i>=0; i--) {
                if (!visited[rev.get(i)]) {
                    rdfs(rev.get(i), id);
                    id++;
                }
            }

            boolean[] ret = new boolean[n];
            for (int i = 0; i < n ; i++) {
                if (nodeId[i] == nodeId[i+n]) {
                    return null;
                }
                ret[i] = nodeId[i] > nodeId[i+n];
            }
            return ret;
        }

        private void dfs(int i) {
            visited[i] = true;
            for (int next : graph[i]) {
                if (!visited[next]) {
                    dfs(next);
                }
            }
            rev.add(i);
        }

        private void rdfs(int i, int id) {
            visited[i] = true;
            nodeId[i] = id;
            for (int next : revGraph[i]) {
                if (!visited[next]) {
                    rdfs(next, id);
                }
            }
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
