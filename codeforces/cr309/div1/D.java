package codeforces.cr309.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/09.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        boolean[] f = new boolean[n];
        for (int i = 0; i < k ; i++) {
            f[in.nextInt()-1] = true;
        }

        int[][] graph = buildGraph(in, n, m);
        int[] order = new int[n];
        Arrays.fill(order, -1);

        TreeSet<City> citySet = new TreeSet<>();
        City[] cities = new City[n];
        for (int i = 0 ; i < n ; i++) {
            if (f[i]) {
                continue;
            }
            int total = graph[i].length;
            int inset = 0;
            for (int to : graph[i]) {
                if (!f[to]) {
                    inset++;
                }
            }
            cities[i] = new City(i, total, inset);
            citySet.add(cities[i]);
        }

        int bestOrder = -1;
        double bestValue = -1;

        int currentOrder = 0;
        while (citySet.size() >= 1) {
            City lowest = citySet.first();
            double value = lowest.inset * 1.0d / lowest.total;
            if (bestValue < value) {
                bestValue = value;
                bestOrder = currentOrder;
            }
            order[lowest.idx] = currentOrder;
            citySet.remove(lowest);
            for (int to : graph[lowest.idx]) {
                City toCity = cities[to];
                if (toCity != null && citySet.contains(toCity)) {
                    citySet.remove(toCity);
                    toCity.inset--;
                    citySet.add(toCity);
                }
            }
            currentOrder++;
        }

        List<Integer> cityIndices = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            if (bestOrder <= order[i]) {
                cityIndices.add(i+1);
                line.append(' ').append(i+1);
            }
        }

        out.println(cityIndices.size());
        out.println(line.substring(1));
        out.flush();
    }

    static class City implements Comparable<City> {
        int idx;
        long total;
        long inset;

        City(int i, long a, long b) {
            idx = i;
            total = a;
            inset = b;
        }

        @Override
        public int compareTo(City o) {
            long ad = inset * o.total;
            long bc = total * o.inset;
            if (ad == bc) {
                return idx - o.idx;
            }
            return Long.compare(ad, bc);
        }
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
