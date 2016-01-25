package atcoder.codefestival2015.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/11/14.
 */
public class I {

    static class Balloon implements Comparable<Balloon> {
        int idx;
        int height;
        int parent;
        int cost;
        Balloon next;

        Balloon(int i, int h, int p) {
            idx = i;
            height = h;
            parent = p;
            cost = 0;
        }

        @Override
        public int compareTo(Balloon o) {
            return o.height - height;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] tree = new int[n];
        int[] parent = new int[n];

        int[] l = new int[n];
        int[] height = new int[n];
        for (int i = 0; i < n ; i++) {
            l[i] = in.nextInt();
        }
        height[0] = l[0];
        parent[0] = -1;

        for (int i = 1; i < n ; i++) {
            int p = in.nextInt();
            height[i] = height[p] + l[i];
            parent[i] = p;
        }

        int q = in.nextInt();
        int[][] queries = new int[q][2];
        for (int i = 0; i < q ; i++) {
            queries[i][0] = i;
            queries[i][1] = in.nextInt();
        }

        Set<Integer> heights = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            heights.add(height[i]);
        }

        Arrays.sort(queries, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });
        int[] answer = new int[q];

        Balloon[] bl = new Balloon[n];
        for (int i = 0; i < n ; i++) {
            bl[i] = new Balloon(i, height[i], parent[i]);
        }
        for (int i = 1; i < n ; i++) {
            bl[i].next = bl[parent[i]];
        }
        Queue<Balloon> que = new PriorityQueue<>();
        for (int i = 0; i < n ; i++) {
            que.add(bl[i]);
        }

        int cut = 0;
        for (int i = 0; i < q ; i++) {
            int he = queries[i][1];
            if (!heights.contains(he)) {
                answer[queries[i][0]] = -1;
                continue;
            }
            while (que.size() >= 1 && que.peek().height > he) {
                Balloon ba = que.poll();
                cut++;
                if (ba.next != null) {
                    ba.next.cost++;
                }
                cut-=ba.cost;
            }
            answer[queries[i][0]] = cut;
        }

        for (int i = 0; i < q ; i++) {
            out.println(answer[i]);
        }
        out.flush();
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
                res += c-'0';
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
