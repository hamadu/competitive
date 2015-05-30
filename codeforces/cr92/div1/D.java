package codeforces.cr92.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/26.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s = in.nextToken();
        SuffixArray sa = new SuffixArray(s);
        sa.buildSA();
        sa.buildLCP();
        out.println(solve(sa.lcp));
        out.flush();
    }


    static long solve(int[] lcp) {
        int n = lcp.length;

        int[] left = new int[n];
        int[] right = new int[n];
        {
            Stack<Integer> st = new Stack<>();
            for (int i = 0; i < n; i++) {
                while (st.size() >= 1 && lcp[st.peek()] >= lcp[i]) {
                    st.pop();
                }
                left[i] = (st.size() == 0) ? -1 : st.peek();
                st.add(i);
            }
        }

        {
            Stack<Integer> st = new Stack<>();
            for (int i = n-1; i >= 0; i--) {
                while (st.size() >= 1 && lcp[st.peek()] >= lcp[i]) {
                    st.pop();
                }
                right[i] = (st.size() == 0) ? n : st.peek();
                st.add(i);
            }
        }

        BITRange rangeSum = new BITRange(n+10);
        for (int i = 1 ; i <= n ; i++) {
            rangeSum.addRange(i, i, lcp[i - 1]);
        }

        int[][] ranges = new int[n][];
        for (int i = 0; i < n ; i++) {
            ranges[i] = new int[]{ left[i], right[i], lcp[i], i };
        }

        Arrays.sort(ranges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        long ret = 0;
        for (int[] r : ranges) {
            long height = rangeSum.get(r[3]+1);
            if (height  == 0) {
                continue;
            }
            long len = r[1] - r[0] - 1;

            ret += ((len * (len + 1)) / 2) * height;
            rangeSum.addRange(r[0]+2, r[1], -height);
        }
        return ret + 1L * n * (n + 1) / 2;
    }

    // 区間加算
    static class BITRange {
        BIT bit0;
        BIT bit1;
        BITRange(int n) {
            bit0 = new BIT(n);
            bit1 = new BIT(n);
        }

        void addRange(int l, int r, long x) {
            bit0.add(l, -x * (l-1));
            bit1.add(l, x);
            bit0.add(r+1, x * r);
            bit1.add(r+1, -x);
        }

        long range(int l, int r) {
            long right = bit0.sum(r) + bit1.sum(r) * r;
            long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
            return right - left;
        }

        long get(int i) {
            return range(i, i);
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

    static class SuffixArray {
        int n;
        char[] base;

        Integer[] sa;
        int[] rank;
        int[] tmp;
        int[] lcp;

        int compareNode(int i, int j, int k) {
            if (rank[i] != rank[j]) {
                return rank[i] - rank[j];
            } else {
                int ri = i + k <= n ? rank[i+k] : -1;
                int rj = j + k <= n ? rank[j+k] : -1;
                return ri - rj;
            }
        }

        SuffixArray(String x) {
            base = x.toCharArray();
            n = base.length;
        }

        void buildSA() {
            sa = new Integer[n+1];
            rank = new int[n+1];
            tmp = new int[n+1];
            for (int i = 0 ; i <= n ; i++) {
                sa[i] = i;
                rank[i] = (i < n) ? base[i] : -1;
            }
            for (int _k = 1 ; _k <= n ; _k *= 2) {
                final int k = _k;
                Arrays.sort(sa, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer i, Integer j) {
                        return compareNode(i, j, k);
                    }
                });
                tmp[sa[0]] = 0;
                for (int i = 1 ; i <= n ; i++) {
                    tmp[sa[i]] = tmp[sa[i-1]] + ((compareNode(sa[i-1], sa[i], k) < 0) ? 1 : 0);
                }
                for (int i = 0 ; i <= n ; i++) {
                    rank[i] = tmp[i];
                }
            }
        }

        void buildLCP() {
            for (int i = 0 ; i <= n ; i++) {
                rank[sa[i]] = i;
            }
            lcp = new int[n];
            int h = 0;
            for (int i = 0 ; i < n ; i++) {
                int j = sa[rank[i]-1];
                if (h > 0) {
                    h--;
                }
                for (; j + h < n && i + h < n ; h++) {
                    if (base[j+h] != base[i+h]) {
                        break;
                    }
                }
                lcp[rank[i]-1] = h;
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
