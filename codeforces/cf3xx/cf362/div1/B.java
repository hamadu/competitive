package codeforces.cf3xx.cf362.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/07/15.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] par = new int[n];
        par[0] = -1;
        for (int i = 0; i < n-1 ; i++) {
            par[i+1] = in.nextInt()-1;
        }
        int[][] graph = buildRootedTreeFromPar(par);
        int[] ord = buildOrdFromRootedTree(par, graph);
        int[] cnt = new int[n];
        for (int ii = n-1 ; ii >= 0 ; ii--) {
            int now = ord[ii];
            cnt[now]++;
            if (par[now] != -1) {
                cnt[par[now]] += cnt[now];
            }
        }

        double[] ans = new double[n];
        ans[0] = 0;
        for (int ii = 0; ii < n ; ii++) {
            int now = ord[ii];
            ans[now]++;
            double average = 0;
            for (int to : graph[now]) {
                average += cnt[to] * 0.5;
            }
            for (int to : graph[now]) {
                ans[to] = ans[now] + average - cnt[to] * 0.5;
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(String.format("%.9f", ans[i]));
        }
        out.println(line.substring(1));
        out.flush();
    }

    // par[i] = -1 := vertex i is the root
    static int[][] buildRootedTreeFromPar(int[] par) {
        int n = par.length;
        int[][] edges = new int[n-1][2];
        int ei = 0;
        for (int i = 0; i < n ; i++) {
            if (par[i] != -1) {
                edges[ei][0] = i;
                edges[ei][1] = par[i];
                ei++;
            }
        }

        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < ei ; i++) {
            int b = edges[i][1];
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < ei ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    static int[] buildOrdFromRootedTree(int[] par, int[][] graph) {
        int n = graph.length;
        int[] ord = new int[n];
        int oi = n-1;
        int[] que = new int[n+1];
        int qh = 0;
        int qt = 0;
        for (int i = 0 ; i < n ; i++) {
            if (graph[i].length == 0) {
                que[qh++] = i;
            }
        }
        int[] filled = new int[n];
        while (qt < qh) {
            int now = que[qt++];
            ord[oi--] = now;
            int to = par[now];
            if (to != -1) {
                filled[to]++;
                if (filled[to] == graph[to].length) {
                    que[qh++] = to;
                }
            }
        }
        return ord;
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
