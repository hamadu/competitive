package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/24.
 */
public class P2647 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[][] rgraph = new int[n][];
        int[] deg = new int[n];
        int[] indeg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            deg[a]++;
            indeg[b]++;
            edges[i] = new int[]{a, b};
        }
        int[] cv = new int[n];
        int cn = 0;
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
            rgraph[i] = new int[indeg[i]];
            if (indeg[i] == 0) {
                cv[cn++] = i;
            }
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            rgraph[b][--indeg[b]] = a;
        }

        int[][] table = new int[cn][cn];
        for (int i = 0; i < cn ; i++) {
            int vi = cv[i];
            int[] dp = new int[n];
            Arrays.fill(dp, Integer.MAX_VALUE);
            Queue<State> q = new PriorityQueue<State>();
            q.add(new State(vi, 0));
            dp[vi] = 0;
            while (q.size() >= 1) {
                State st = q.poll();
                for (int to : graph[st.now]) {
                    if (dp[to] > st.time) {
                        dp[to] = st.time;
                        q.add(new State(to, st.time));
                    }
                }
                for (int to : rgraph[st.now]) {
                    if (dp[to] > st.time+1) {
                        dp[to] = st.time+1;
                        q.add(new State(to, st.time+1));
                    }
                }
            }

            for (int j = 0; j < cn ; j++) {
                table[i][j] = dp[cv[j]];
            }
        }

        for (int i = 0; i < cn ; i++) {
            table[i][i] = 100000000;
        }

        int min = Integer.MAX_VALUE;
        List<Integer> ans = new ArrayList<Integer>();

        MinimumSpanningArborescence msa = new MinimumSpanningArborescence(table);


        for (int i = 0; i < cn ; i++) {
            int cost = msa.doit(i);
            if (min > cost) {
                min = cost;
                ans.clear();
                ans.add(cv[i]);
            } else if (min == cost) {
                ans.add(cv[i]);
            }
        }


        out.println(ans.size() + " " + min);
        StringBuilder line = new StringBuilder();
        for (int a : ans) {
            line.append(' ').append(a);
        }
        out.println(line.substring(1));
        out.flush();
    }

    static class MinimumSpanningArborescence {
        int INF = 100000000;
        int n;
        int[][] graph;

        MinimumSpanningArborescence(int[][] g) {
            n = g.length;
            graph = g;
        }

        int doit(int root) {
            int cost = 0;
            int[] c = doit(graph, root);
            for (int j = 0; j < n ; j++) {
                if (c[j] >= 0) {
                    cost += graph[c[j]][j];
                }
            }
            return cost;
        }

        int[] doit(int[][] graph, int root) {
            int n = graph.length;
            int[] msa = new int[n];
            Arrays.fill(msa, -1);

            for (int i = 0; i < n; i++) {
                if (i == root) {
                    continue;
                }
                int min = INF;
                int e = -1;
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (min > graph[j][i]) {
                        min = graph[j][i];
                        e = j;
                    }
                }
                msa[i] = e;
            }

            int[] res = detectCycle(msa);
            if (res != null) {
                int cv = res[0];

                int[] map = new int[n];
                Arrays.fill(map,-1);
                int quo = n-res[1];
                map[cv] = quo;

                int mincy = graph[msa[cv]][cv];
                for (int k=msa[cv]; k!=cv; k=msa[k]) {
                    map[k] = quo;
                    mincy = Math.min(mincy, graph[msa[k]][k]);
                }

                int[] imap = new int[n-res[1]];
                int ptr = 0;
                for (int i = 0; i < n; i++) {
                    if (map[i] == quo) {
                        continue;
                    }
                    map[i] = ptr;
                    imap[ptr] = i;
                    ptr++;
                }

                int[][] quog = new int[quo+1][quo+1];
                for (int i = 0; i < quo+1; i++) {
                    Arrays.fill(quog[i],INF);
                }

                int[] to = new int[n];
                int[] from = new int[n];
                for (int i = 0; i < n; i++) {
                    if (map[i] != quo) {
                        for (int j = 0; j < n; j++) {
                            if (map[j] == quo) {
                                int nc = graph[i][j] - graph[msa[j]][j] + mincy;
                                if (quog[map[i]][quo] > nc) {
                                    quog[map[i]][quo] = nc;
                                    to[i] = j;
                                }
                            }else {
                                quog[map[i]][map[j]] = graph[i][j];
                            }
                        }
                    }else {
                        for (int j = 0; j < n; j++) {
                            if (map[j] != quo) {
                                int nc = graph[i][j];
                                if (quog[quo][map[j]] > nc) {
                                    quog[quo][map[j]] = nc;
                                    from[j] = i;
                                }
                            }
                        }
                    }
                }

                int[] quomsa = doit(quog, map[root]);
                for (int i = 0; i<quo; i++) {
                    if (quomsa[i] == quo) {
                        msa[imap[i]] = from[imap[i]];
                    } else if (quomsa[i] != -1) {
                        msa[imap[i]] = imap[quomsa[i]];
                    }
                }
                int u = imap[quomsa[quo]];
                msa[to[u]] = u;
            }
            return msa;
        }


        int[] detectCycle(int[] f) {
            int n = f.length;
            BitSet visited = new BitSet(n);
            outer:
            for (int src = 0; src < n; src++) {
                if (visited.get(src) || f[src] < 0) {
                    continue;
                }
                int power = 1;
                int lambda = 1;
                int tortoise = src;
                int hare = f[src];
                visited.set(src);
                while (tortoise != hare) {
                    if (hare < 0) {
                        continue outer;
                    }
                    visited.set(hare);
                    if (power == lambda) {
                        tortoise = hare;
                        power <<= 1;
                        lambda = 0;
                    }
                    hare = f[hare];
                    lambda++;
                }
                if (lambda > 0) {
                    return new int[]{hare, lambda};
                }
            }
            return null;
        }
    }

    static class State implements Comparable<State> {
        int now;
        int time;

        State(int i, int t) {
            now = i;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
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
