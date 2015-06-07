package codeforces.looksery2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class G {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        int[] base = new int[n];
        for (int i = 0; i < n ; i++) {
            base[i] = a[i] = in.nextInt();
        }
        Map<Integer,Integer> c = compress(a);
        for (int i = 0 ; i < n ; i++) {
            a[i] = c.get(a[i]) + 1;
        }

        BIT bitLarge  = new BIT(n+10);
        BIT bitSmall  = new BIT(n+10);
        int[] smaller = new int[n];
        int [] larger = new int[n];
        for (int i = 0 ; i < n ; i++) {
            larger[i] = (int)bitLarge.range(a[i]+1, n+5);
            bitLarge.add(a[i], 1);
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            smaller[i] = (int)bitSmall.range(1, a[i]-1);
            bitSmall.add(a[i], 1);
        }

        int[][] diff = new int[n][2];
        for (int i = 0 ; i < n ; i++) {
            diff[i][0] = base[i] + larger[i] - smaller[i];
            diff[i][1] = smaller[i] - larger[i];
        }

        Arrays.sort(diff, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        boolean isok = true;
        long d = 0;
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            line.append(' ').append(diff[i][0]);
            d += diff[i][1];
            if (d  >= 1) {
                isok = false;
            }
        }

        if (isok) {
            out.println(line.substring(1));
        } else {
            out.println(":(");
        }
        out.flush();
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }
    }

    static Map<Integer, Integer> compress(int[] a) {
        int n = a.length;
        int[] b = a.clone();
        for (int i = 0 ; i < n ; i++) {
            int j = (int)(Math.random() * n);
            int tmp = b[j];
            b[j] = b[i];
            b[i] = tmp;
        }

        HashSet<Integer> set = new HashSet<>();
        List<Integer> nadd = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            if (!set.contains(b[i])) {
                set.add(b[i]);
                nadd.add(b[i]);
            }
        }
        Collections.shuffle(nadd);
        Collections.sort(nadd);

        Map<Integer, Integer> comp = new HashMap<>();
        for (int na : nadd) {
            comp.put(na, comp.size());
        }
        return comp;
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

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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



