package atcoder.utpc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {


    static class Segment {
        int digit;
        long max;
        long rngsum;

        Segment[] children;

        Segment(int d) {
            max = 0;
            digit = d;
            children = new Segment[10]; // 0 to 9
        }

        void make(int num) {
            if (num == 0) {
                return;
            }
            int d = num % 10;
            int e = num / 10;
            if (children[d] == null) {
                children[d] = new Segment(d);
            }
            children[d].make(e);
        }

        long max() {
            long ma = 0;
            boolean haschild = false;
            for (Segment seg : children) {
                if (seg != null) {
                    haschild = true;
                    seg.rngsum += rngsum;
                    ma = Math.max(ma, seg.max);
                }
            }
            if (haschild) {
                max = Math.max(max, ma) + rngsum;
                rngsum = 0;
            } else {
                max = rngsum;
            }
            return max;
        }

        void add(int num, long val) {
            if (num == 0) {
                rngsum += val;
                max();
                // debug("rngsum",val,max);
                return;
            }

            int d = num % 10;
            int e = num / 10;
            // debug("add", num, d, e);
            children[d].add(e, val);
            // debug("rngsum-A", rngsum, max);
            max();
            // debug("rngsum-B", rngsum, max);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        int[] dig = new int[n];
        long[] val = new long[n];
        for (int i = 0; i < n ; i++) {
            dig[i] = in.nextInt();
            val[i] = in.nextLong();
        }

        Segment root = new Segment(-1);
        for (int i = 0 ; i < n ; i++) {
            root.make(dig[i]);
        }
        for (int i = 0 ; i < n ; i++) {
            root.add(dig[i], val[i]);
            out.println(root.max);
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



