package codeforces.cf0xx.cr55.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/03.
 */
public class E {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[][] graph = buildGraph(in, n, m);

        Map<Integer,Set<Integer>> forbidden = new HashMap<>();
        for (int i = 0; i < k ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int code = (a<<12)+b;
            int c = in.nextInt()-1;
            if (!forbidden.containsKey(code)) {
                forbidden.put(code, new HashSet<>());
            }
            forbidden.get(code).add(c);
        }

        int[][] dp = new int[n][n];
        int[][] fr = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;

        int[] que = new int[9000000];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        que[qh++] = 0;
        while (qt < qh) {
            int code = que[qt++];
            int tim = que[qt++] + 1;
            int prv = code >> 12;
            int now = code & 4095;
            if (forbidden.containsKey(code)) {
                Set<Integer> wrong = forbidden.get(code);
                for (int to : graph[now]) {
                    if (wrong.contains(to)) {
                        continue;
                    }
                    if (dp[to][now] > tim) {
                        fr[to][now] = prv;
                        dp[to][now] = tim;
                        que[qh++] = (now<<12)+to;
                        que[qh++] = tim;
                    }
                }
            } else {
                for (int to : graph[now]) {
                    if (dp[to][now] > tim) {
                        fr[to][now] = prv;
                        dp[to][now] = tim;
                        que[qh++] = (now<<12)+to;
                        que[qh++] = tim;
                    }
                }
            }
        }



        int best = INF;
        int now = -1;
        int prv = -1;
        for (int i = 0; i < n ; i++) {
            if (dp[n-1][i] < best) {
                now = n-1;
                prv = i;
                best = dp[n-1][i];
            }
        }
        if (best == INF) {
            out.println(-1);
        } else {
            List<Integer> a = new ArrayList<>();
            while (now != 0) {
                a.add(now);
                int pp = fr[now][prv];
                now = prv;
                prv = pp;
            }
            a.add(0);
            StringBuilder line = new StringBuilder();
            for (int i = a.size()-1 ; i >= 0 ; i--) {
                line.append(' ').append(a.get(i)+1);
            }
            out.println(best);
            out.println(line.substring(1));
        }



        out.flush();
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
