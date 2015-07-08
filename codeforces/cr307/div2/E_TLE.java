package codeforces.cr307.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 15/06/13.
 */
public class E_TLE {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        SqrtDecomp data = new SqrtDecomp(a);
        while (--q >= 0) {
            int type = in.nextInt();
            if (type == 1) {
                int l = in.nextInt()-1;
                int r = in.nextInt()-1;
                int x = in.nextInt();
                data.add(l, r, x);
            } else {
                long y = in.nextInt();
                out.println(data.maxRange(y));
            }
        }
        out.flush();
    }


    static class SqrtDecomp {
        int n;
        long[] a;

        int bn;
        long[] added;

        TreeSet<Long>[] posMap;

        static final int BUCKET_SIZE = 2000;

        SqrtDecomp(long[] x) {
            n = x.length;
            a = x;

            bn = (n + BUCKET_SIZE - 1) / BUCKET_SIZE;
            added = new long[bn];
            posMap = new TreeSet[bn];
            for (int i = 0; i < bn ; i++) {
                posMap[i] = new TreeSet<>();
            }
            for (int i = 0 ; i < n ; i++) {
                addMap(i, a[i]);
            }
        }

        void addMap(int pos, long a) {
            int bi = pos / BUCKET_SIZE;
            long id = (a<<20)+pos;
            posMap[bi].add(id);
        }

        // [l,r]
        void add(int l, int r, long x) {
            int bl = l / BUCKET_SIZE;
            int bt = r / BUCKET_SIZE;
            if (bl == bt) {
                posMap[bl].clear();
                for (int i = bl * BUCKET_SIZE ; i < (bl + 1) * BUCKET_SIZE ; i++) {
                    if (l <= i && i <= r) {
                        a[i] += x;
                    }
                    if (i < n) {
                        addMap(i, a[i]);
                    }
                }
                return;
            }

            for (int bi = bl ; bi <= bt ; bi++) {
                if (bi == bl || bi == bt) {
                    posMap[bi].clear();
                    for (int i = bi * BUCKET_SIZE ; i < (bi + 1) * BUCKET_SIZE ; i++) {
                        if (l <= i && i <= r) {
                            a[i] += x;
                        }
                        if (i < n) {
                            addMap(i, a[i]);
                        }
                    }
                } else {
                    added[bi] += x;
                }
            }
        }

        public long maxRange(long y) {
            long left = 1000000;
            long right = -1;
            for (int bi = 0 ; bi < bn ; bi++) {
                long find = y - added[bi];
                long minId = (find<<20)-1;
                Long obj = posMap[bi].higher(minId);
                if (obj != null) {
                    long v = obj>>20;
                    if (v == find) {
                        left = right = obj-(v<<20);
                        break;
                    }
                }
            }
            for (int bi = bn-1 ; bi >= 0 ; bi--) {
                long find = y - added[bi];
                long maxId = (find<<20)+n;
                Long obj = posMap[bi].lower(maxId);
                if (obj != null) {
                    long v = obj>>20;
                    if (v == find) {
                        right = obj-(v<<20);
                        break;
                    }
                }
            }
            if (right == -1) {
                return -1;
            }
            return right - left;
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
