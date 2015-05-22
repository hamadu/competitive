package codeforces.cr285.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/05/19.
 */
public class B {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] p = new int[n];
        int[] q = new int[n];
        for (int i = 0; i < n ; i++) {
            p[i] = in.nextInt()+1;
        }
        for (int i = 0; i < n ; i++) {
            q[i] = in.nextInt()+1;
        }
        if (n == 1) {
            out.println(0);
            out.flush();
            return;
        }



        int[] sumValue = new int[n];
        BIT bitP = new BIT(n+10);
        BIT bitQ = new BIT(n+10);
        int carry = 0;
        for (int i = n-1 ; i >= 0; i--) {
            int pv = (int)bitP.range(1, p[i]);
            int qv = (int)bitQ.range(1, q[i]);
            int value = pv + qv + carry;
            int k = n-i;
            carry = value / k;
            value %= k;

            sumValue[i] = value;
            bitP.add(p[i], 1);
            bitQ.add(q[i], 1);
        }


        int[] num = new int[n];
        for (int i = 0; i < n ; i++) {
            num[i] = i;
        }
        KthTree tree = new KthTree(num);
        int[] answer = new int[n];
        for (int i = 0 ; i < n ; i++) {
            int v = tree.kthElement(sumValue[i]);
            tree.add(v, -1);
            answer[i] = v;
        }

        StringBuilder b = new StringBuilder();
        for (int a : answer) {
            b.append(' ').append(a);
        }
        out.println(b.substring(1));
        out.flush();
    }

    static class KthTree {
        Map<Integer, Integer> imap = new HashMap<>();
        int[] values;
        int vidx;
        BIT bit;

        KthTree(int[] sortedBase) {
            values = new int[sortedBase.length + 1];
            vidx = 1;
            for (int i = 0; i < sortedBase.length; i++) {
                if (!imap.containsKey(sortedBase[i])) {
                    imap.put(sortedBase[i], vidx);
                    values[vidx++] = sortedBase[i];
                }
            }
            bit = new BIT(vidx);
            for (int i = 0; i < sortedBase.length; i++) {
                bit.add(imap.get(sortedBase[i]), 1);
            }
        }

        void add(int v, int x) {
            bit.add(imap.get(v), x);
        }

        int kthElement(int k) {
            int idx = kthIndex(k);
            if (idx == -1) {
                return -1;
            }
            return values[idx];
        }

        int kthIndex(int k) {
            k++;
            int l = -1;
            int r = (int) bit.N + 1;
            while (r - l > 1) {
                int med = (r + l) / 2;
                if (bit.sum(med) < k) {
                    l = med;
                } else {
                    r = med;
                }
            }
            if (r == bit.N + 1) {
                return -1;
            }
            return r;
        }
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
