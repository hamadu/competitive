package codeforces.cf4xx.cf413;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        long[] cost = in.nextLongs(n);
        int na = in.nextInt();
        int[] a = in.nextInts(na);
        int nb = in.nextInt();
        int[] b = in.nextInts(nb);

        int[] flg = new int[n];
        for (int i = 0; i < na ; i++) {
            a[i]--;
            flg[a[i]] |= 1;
        }
        for (int i = 0; i < nb ; i++) {
            b[i]--;
            flg[b[i]] |= 2;
        }

        List<Long> other = new ArrayList<>();
        List<Long> onlyA = new ArrayList<>();
        List<Long> onlyB = new ArrayList<>();
        List<Long> both = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            switch (flg[i]) {
                case 0:
                    other.add(cost[i]);
                    break;
                case 1:
                    onlyA.add(cost[i]);
                    break;
                case 2:
                    onlyB.add(cost[i]);
                    break;
                case 3:
                    both.add(cost[i]);
                    break;
            }
        }

        Collections.sort(onlyA);
        Collections.sort(onlyB);
        Collections.sort(both);
        if (k < onlyA.size()) {
            other.addAll(onlyA.subList(k, onlyA.size()));
            onlyA = onlyA.subList(0, k);
        }
        if (k < onlyB.size()) {
            other.addAll(onlyB.subList(k, onlyB.size()));
            onlyB = onlyB.subList(0, k);
        }
        Collections.sort(other);

        long min = Long.MAX_VALUE;
        long total = 0;
        int cntA = 0;
        int cntB = 0;
        for (int i = 0; i < k ; i++) {
            if (i < onlyA.size()) {
                total += onlyA.get(i);
                cntA++;
            }
            if (i < onlyB.size()) {
                total += onlyB.get(i);
                cntB++;
            }
        }
        int cnt = cntA + cntB;

        Queue<Long> moge = new PriorityQueue<>();
        Queue<Long> amari = new PriorityQueue<>();
        for (int i = 0 ; i < other.size() ; i++) {
            if (cnt < m) {
                total += other.get(i);
                moge.add(-other.get(i));
                cnt++;
            } else {
                amari.add(other.get(i));
            }
        }
        if (cnt == m && cntA >= k && cntB >= k) {
            min = Math.min(min, total);
        }

        int la = onlyA.size()-1;
        int lb = onlyB.size()-1;

        for (int bi = 0 ; bi < both.size() ; bi++) {
            total += both.get(bi);
            cnt++;
            cntA++;
            cntB++;

            while (cnt > m && moge.size() >= 1) {
                long remove = moge.poll(); // its negative value
                total += remove;
                amari.add(-remove);
                cnt--;
            }

            if (cntA > k) {
                if (la >= 0) {
                    cnt--;
                    cntA--;
                    total -= onlyA.get(la);
                    amari.add(onlyA.get(la));
                    la--;
                }
            }
            if (cntB > k) {
                if (lb >= 0) {
                    cnt--;
                    cntB--;
                    total -= onlyB.get(lb);
                    amari.add(onlyB.get(lb));
                    lb--;
                }
            }



            while (cnt > m && moge.size() >= 1) {
                long remove = moge.poll(); // its negative value
                total += remove;
                amari.add(-remove);
                cnt--;
            }

            while (cnt < m && amari.size() >= 1) {
                long add = amari.poll(); // its positive value
                total += add;
                moge.add(-add);
                cnt++;
            }

            if (cnt == m && cntA >= k && cntB >= k) {
                min = Math.min(min, total);
            }
        }
        out.println(min == Long.MAX_VALUE ? -1 : min);
        out.flush();
    }

    static class Deco {
        boolean a;
        boolean b;
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

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
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

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
