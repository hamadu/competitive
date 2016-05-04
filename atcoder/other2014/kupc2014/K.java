package atcoder.other2014.kupc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by hama_du on 5/2/16.
 */
public class K {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] len = new int[n];
        int[] head = new int[n+1];
        int[] map = new int[300000];
        int[] ord = new int[300000];
        Arrays.fill(map, -1);

        char[][] S = new char[n][];
        StringBuilder SS = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            S[i] = in.nextToken().toCharArray();
            len[i] = S[i].length;
            int fr = SS.length();
            int to = fr + S[i].length;
            for (int k = fr ; k < to ; k++) {
                map[k] = i;
                ord[k] = k - fr;
            }
            head[i+1] = head[i] + S[i].length + 1;
            SS.append(S[i]);
            if (i != n-1) {
                SS.append('$');
            }
        }

        SuffixArray sa = new SuffixArray(SS.toString().toCharArray());
        sa.buildSA();
        sa.buildLCP();

        for (int i = 0 ; i < sa.lcp.length ; i++) {
            int idx = sa.sa[i];
            if (map[idx] != -1) {
                int l = S[map[idx]].length;
                sa.lcp[i] = Math.min(sa.lcp[i], l - ord[idx]);
            }
        }

        int L = sa.sa.length - n;
        int[] h = new int[L];
        int[] kind = new int[L];
        for (int i = n ; i < sa.sa.length ; i++) {
            kind[i-n] = map[sa.sa[i]];
        }
        for (int f = n ; f < sa.sa.length ; f++) {
            h[f-n] = f < sa.lcp.length ? sa.lcp[f] : 0;
        }

        int[] left = new int[L];
        int[] right = new int[L];
        Stack<Integer> stk = new Stack<>();
        for (int i = 0; i < L ; i++) {
            while (stk.size() >= 1 && h[stk.peek()] >= h[i]) {
                stk.pop();
            }
            if (stk.size() == 0) {
                left[i] = 0;
            } else {
                left[i] = stk.peek() + 1;
            }
            stk.push(i);
        }
        stk.clear();
        for (int i = L-1 ; i >= 0 ; i--) {
            while (stk.size() >= 1 && h[stk.peek()] >= h[i]) {
                stk.pop();
            }
            if (stk.size() == 0) {
                right[i] = L-1;
            } else {
                right[i] = stk.peek();
            }
            stk.push(i);
        }
        int[][] query = new int[L][2];
        for (int i = 0; i < L ; i++) {
            query[i][0] = left[i];
            query[i][1] = right[i];
        }

        int[] width = countDistinctValues(kind, query);
        // debug(width, query, h);

        long max = 0;
        for (int i = 0; i < L ; i++) {
            max = Math.max(max, (long)h[i] * width[i]);
        }
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, S[i].length);
        }
        out.println(max);
        out.flush();
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

        public SuffixArray(char[] x) {
            base = x;
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

    static class Event implements Comparable<Event> {
        int fr;
        int to;
        int val;  // plus: array value, 0 or minus: -(query index)


        Event(int f, int t, int ty) {
            fr = f;
            to = t;
            val = ty;
        }

        @Override
        public int compareTo(Event o) {
            if (fr != o.fr) {
                return o.fr - fr;
            }
            return o.val - val;
        }
    }

    static int[] countDistinctValues(int[] arr, int[][] query) {
        int q = query.length;
        int n = arr.length;

        BIT bit = new BIT(n+5);

        Event[] events = new Event[q+n];
        for (int i = 0; i < q ; i++) {
            events[i] = new Event(query[i][0], query[i][1], -i);
        }
        for (int i = 0; i < n ; i++) {
            events[q+i] = new Event(i, i, arr[i]+1);
        }
        Arrays.sort(events);

        int[] ret = new int[q];
        int max = 0;
        for (int i = 0; i < arr.length ; i++) {
            max = Math.max(max, arr[i]);
        }
        int[] lastFound = new int[max+10];
        for (Event e : events) {
            if (e.val <= 0) {
                int qidx = -e.val;
                ret[qidx] = (int)bit.range(e.fr+1, e.to+1);
            } else {
                if (lastFound[e.val] != 0) {
                    bit.add(lastFound[e.val], -1);
                }
                bit.add(e.fr+1, 1);
                lastFound[e.val] = e.fr+1;
            }
        }
        return ret;
    }

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
