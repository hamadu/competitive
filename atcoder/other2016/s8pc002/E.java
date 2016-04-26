package atcoder.other2016.s8pc002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 4/25/16.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] cl = in.nextToken().toCharArray();
        SuffixArray sa = new SuffixArray(cl);
        sa.buildSA();
        sa.buildLCP();

        long sum = 0;
        for (int i = 1; i < sa.sa.length ; i++) {
            long len = cl.length - sa.sa[i] - sa.lcp[i-1];
            long fr = sa.lcp[i-1]+1;
            sum += (fr+(fr+len-1))*len/2;
        }
        out.println(sum);
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
                Arrays.sort(sa, (i, j) -> compareNode(i, j, k));
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
