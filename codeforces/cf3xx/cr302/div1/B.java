package codeforces.cf3xx.cr302.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[][] graph = buildGraph(in, n, m);

        memo = new int[n][];

        int _s1 = in.nextInt()-1;
        int _t1 = in.nextInt()-1;
        int l1 = in.nextInt();

        int _s2 = in.nextInt()-1;
        int _t2 = in.nextInt()-1;
        int l2 = in.nextInt();

        int best = Integer.MAX_VALUE;
        for (int[] st1 : new int[][]{{_s1, _t1}, {_t1, _s1}}) {
            for (int[] st2 : new int[][]{{_s2, _t2}, {_t2, _s2}}) {
                int s1 = st1[0];
                int t1 = st1[1];
                int s2 = st2[0];
                int t2 = st2[1];
                int[] s1d = dist(graph, s1);
                int[] t1d = dist(graph, t1);
                int[] s2d = dist(graph, s2);
                int[] t2d = dist(graph, t2);
                for (int p = 0 ; p < n ; p++) {
                    int[] pd = dist(graph, p);
                    for (int q = 0 ; q < n ; q++) {
                        if (s1d[p] + t1d[p] <= l1) {
                            if (s2d[q] + t2d[q] <= l2) {
                                best = Math.min(best, s1d[p] + t1d[p] + s2d[q] + t2d[q]);
                            }
                        }

                        if (s1d[p] + pd[q] + t1d[q] <= l1) {
                            if (s2d[p] + pd[q] + t2d[q] <= l2) {
                                best = Math.min(best, s1d[p] + t1d[q] + s2d[p] + t2d[q] + pd[q]);
                            }
                        }
                    }
                }
            }
        }


        int ans = best == Integer.MAX_VALUE ? -1 : m - best;
        out.println(ans);
        out.flush();
    }

    static int[] que;

    static int[][] memo;

    static int[] dist(int[][] graph, int from) {
        if (memo[from] != null) {
            return memo[from];
        }

        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[from] = 0;

        if (que == null) {
            que = new int[n*4];
        }
        int qh = 0;
        int qt = 0;
        que[qt++] = from;
        que[qt++] = 0;
        while (qh < qt) {
            int now = que[qh++];
            int time = que[qh++];
            for (int to : graph[now]) {
                if (dist[to] > time + 1) {
                    dist[to] = time + 1;
                    que[qt++] = to;
                    que[qt++] = time + 1;
                }
            }
        }
        memo[from] = dist;
        return dist;
    }

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
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



