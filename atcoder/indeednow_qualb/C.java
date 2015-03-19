// http://indeednow-qualb.contest.atcoder.jp/submissions/362757
package atcoder.indeednow_qualb;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        graph = buildGraph(in, n, n-1);
        answer = new ArrayList<>();

        PriorityQueue<Integer> q = new PriorityQueue<>();
        Set<Integer> done = new HashSet<>();
        q.add(0);
        done.add(0);
        while (q.size() >= 1) {
            int now = q.poll();
            answer.add(now);
            for (int to : graph[now]) {
                if (!done.contains(to)) {
                    done.add(to);
                    q.add(to);
                }
            }
        }

        StringBuilder b = new StringBuilder();
        for (int a : answer) {
            b.append(' ').append(a+1);
        }
        System.out.println(b.substring(1));
    }

    static List<Integer> answer;
    static int[][] graph;




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
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int next() {
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

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}
