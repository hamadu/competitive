package codeforces.cf3xx.cf363.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/07/19.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int k = in.nextInt();
        int n = in.nextInt();
        long[][] stone = new long[k][2];
        long[][] mon = new long[k][2];
        for (int i = 0; i < k ; i++) {
            for (int j = 0; j < 2 ; j++) {
                stone[i][j] = in.nextLong();
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                mon[i][j] = in.nextLong();
            }
        }






        int[] a = in.nextInts(n);
        for (int i = 0; i < n ; i++) {
            a[i]--;
        }

        List<Integer> heads = new ArrayList<>();
        boolean[] self = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (a[i] == i) {
                heads.add(i);
            }
        }
        int initialRoot = heads.size();

        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n ; i++) {
            uf.unite(i, a[i]);
        }
        for (int i = 0; i < n ; i++) {
            uf.find(i);
        }
        for (int x : heads) {
            self[uf.find(x)] = true;
        }

        for (int i = 0; i < n ; i++) {
            if (self[uf.find(i)]) {
                self[i] = true;
            }
        }

        for (int i = 0; i < n ; i++) {
            int id = uf.find(i);
            if (self[id]) {
                continue;
            }
            int cnt = uf.groupCount(i);
            int now = i;
            for (int j = 0; j < 2*cnt ; j++) {
                now = a[now];
            }
            heads.add(now);
            self[id] = true;
        }


        if (initialRoot >= 1) {
            int rt = heads.get(0);
            for (int i = 1 ; i < heads.size() ; i++) {
                int id = heads.get(i);
                a[id] = rt;
            }
            out.println(heads.size()-1);
            printLine(out, a);
        } else {
            int rt = heads.get(0);
            for (int i = 0 ; i < heads.size() ; i++) {
                int id = heads.get(i);
                a[id] = rt;
            }
            out.println(heads.size());
            printLine(out, a);
        }
        out.flush();
    }

    private static void printLine(PrintWriter out, int[] a) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < a.length ; i++) {
            line.append(' ').append(a[i]+1);
        }
        out.println(line.substring(1));
    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
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

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
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
