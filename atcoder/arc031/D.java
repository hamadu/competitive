// http://arc031.contest.atcoder.jp/submissions/363600
package atcoder.arc031;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class D {

    final static double EPS = 1e-9;

    public static class Edge {
        int to;
        double cap;
        int rev;
        public Edge(int _to, double _cap, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
        }
    }
    public static Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
    public static int[] level;
    public static int[] itr;
    public static void init(int size) {
        for (int i = 0 ; i < size ; i++) {
            graph.put(i, new ArrayList<Edge>());
        }
        level = new int[size];
        itr = new int[size];
    }
    public static void edge(int from, int to, double cap) {
        graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
        graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
    }
    public static double dfs(int v, int t, double f) {
        if (v == t) return f;
        for (int i = itr[v] ; i < graph.get(v).size() ; i++) {
            itr[v] = i;
            Edge e = graph.get(v).get(i);
            if (e.cap > EPS && level[v] < level[e.to]) {
                double d = dfs(e.to, t, Math.min(f, e.cap));
                if (d > EPS) {
                    e.cap -= d;
                    graph.get(e.to).get(e.rev).cap += d;
                    return d;
                }
            }
        }
        return 0;
    }

    public static void bfs(int s) {
        Arrays.fill(level, -1);
        Queue<Integer> q = new ArrayBlockingQueue<Integer>(graph.size()+10);
        level[s] = 0;
        q.add(s);
        while (q.size() >= 1) {
            int v = q.poll();
            for (int i = 0; i < graph.get(v).size() ; i++) {
                Edge e = graph.get(v).get(i);
                if (e.cap > EPS && level[e.to] < 0) {
                    level[e.to] = level[v] + 1;
                    q.add(e.to);
                }
            }
        }
    }

    public static double max_flow(int s, int t) {
        double flow = 0;
        while (true) {
            bfs(s);
            if (level[t] < 0) {
                return flow;
            }
            Arrays.fill(itr, 0);
            while (true) {
                double f = dfs(s, t, Integer.MAX_VALUE);
                if (f < EPS) {
                    break;
                }
                flow += f;
            }
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            s[i] = in.nextInt();
        }
        int[] t = new int[m];
        for (int i = 0; i < m; i++) {
            t[i] = in.nextInt();
        }

        int[][] table = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            int k = in.nextInt();
            for (int j = 0; j < k; j++) {
                table[i][in.nextInt()-1] = 1;
            }
        }

        double max = 10000.0;
        double min = 0.0;
        while (max - min > 1e-5) {
            double med = (max + min) / 2;
            if (isok(s, t, table, med)) {
                min = med;
            } else {
                max = med;
            }
        }
        out.println(min);
        out.flush();
    }

    private static boolean isok(int[] s, int[] t, int[][] table, double med) {
        int n = s.length;
        int m = t.length;

        init(n+m+2);
        for (int i = 0 ; i < n ; i++) {
            edge(n+m, i, s[i]);
        }
        for (int i = 0 ; i < m ; i++) {
            edge(n+i, n+m+1, t[i] * med);
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                if (table[i][j] >= 1) {
                    edge(i, n+j, 1e9);
                }
            }
        }
        double flow = max_flow(n+m, n+m+1);
        int sum = 0;
        for (int i = 0 ; i < n ; i++) {
            sum += s[i];
        }
        return (flow + EPS <= sum);
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
