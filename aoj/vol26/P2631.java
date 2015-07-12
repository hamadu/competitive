package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/11.
 */
public class P2631 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        int[] ord = new int[n];
        for (int i = 0 ; i < n ; i++) {
            ord[i] = i;
        }

        long ans = Long.MAX_VALUE;
        do {
            ans = Math.min(ans, dfs(a, ord, 0, new boolean[20][20], 0));
        } while (next_permutation(ord));
        out.println(ans);
        out.flush();
    }

    private static long dfs(long[] a, int[] ord, int idx, boolean[][] graph, int gn) {
        int n = a.length;
        if (idx >= n) {
            return 0;
        }

        int maxNeed = (int)Math.min(a[ord[idx]], idx);
        List<Integer> bestV = findBest(maxNeed, graph, gn);
        int vs = bestV.size();
        for (int i : bestV) {
            for (int j : bestV) {
                graph[i][j] = true;
            }
        }
        int lf = (int) Math.max(0, Math.min(4, a[ord[idx]] - vs));
        for (int i = gn ; i < gn+lf ; i++) {
            for (int j = gn+1 ; j < gn+lf ; j++) {
                graph[i][j] = true;
            }
            for (int j : bestV) {
                graph[j][i] = true;
            }
        }
        return dfs(a, ord, idx+1, graph, gn + lf) + a[ord[idx]] - vs;
    }

    public static List<Integer> findBest(int want, boolean[][] graph, int gn) {
        bestV = new ArrayList<Integer>();
        dfs2(0, want, 0, new ArrayList<Integer>(), graph, gn);
        return bestV;
    }

    private static List<Integer> bestV;

    public static void dfs2(int idx, int max, int fr, List<Integer> v,  boolean[][] graph, int gn) {
        if (bestV.size() < v.size()) {
            bestV = v;
        }
        if (v.size() == max || idx == gn) {
            return;
        }
        boolean isOK = true;
        for (int vi : v) {
            if (graph[vi][fr]) {
                isOK = false;
                break;
            }
        }
        if (isOK) {
            List<Integer> vv = new ArrayList<Integer>(v);
            vv.add(fr);
            dfs2(idx+1, max, fr+1, vv, graph, gn);
        }
        dfs2(idx+1, max, fr+1, v, graph, gn);
    }


    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
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
