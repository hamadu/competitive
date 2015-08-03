package codeforces.cr254.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/03.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int d = in.nextInt();
        int x = in.nextInt();
        a = new int[n];
        b = new int[n];
        initAB(n, d, x);

        int[] imosB = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imosB[i+1] = imosB[i] + b[i];
        }
        int[] ones = new int[n];
        int oi = 0;
        for (int i = 0; i < n ; i++) {
            if (b[i] == 1) {
                ones[oi++] = i;
            }
        }
        
        int[][] ord = new int[n][2];
        for (int i = 0; i < n ; i++) {
            ord[i][0] = a[i];
            ord[i][1] = i;
        }
        Arrays.sort(ord, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        int[] ans = new int[n];


        TreeSet<Integer> left = new TreeSet<>();
        for (int i = 0; i < n ; i++) {
            left.add(i);
        }

        int[] mark = new int[n];
        int mi = 0;

        for (int i = 0; i < n; i++) {
            int idx = ord[i][1];
            int cnt = n - idx;
            if (imosB[cnt] <= 320) {
                int oneI = 0;
                while (oneI < oi && ones[oneI] < cnt) {
                    int ai = idx+ones[oneI];
                    if (ans[ai] == 0) {
                        ans[ai] = ord[i][0];
                        left.remove(ai);
                    }
                    oneI++;
                }
            } else {
                mi = 0;
                for (int ai : left.tailSet(idx, true)) {
                    if (b[ai-idx] == 1) {
                        mark[mi++] = ai;
                        ans[ai] = ord[i][0];
                    }
                }
                for (int j = 0; j < mi ; j++) {
                    left.remove(mark[j]);
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            out.println(ans[i]);
        }
        out.flush();
    }

    static long getNextX(long x) {
        x = (x * 37 + 10007) % 1000000007;
        return x;
    }

    static int[] a;
    static int[] b;

    static void swap(int[] a, int i1, int i2) {
        int tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
    }

    static void initAB(int n, int d, long x) {
        for (int i = 0; i < n ; i++){
            a[i] = i + 1;
        }
        for(int i = 0; i < n; i++) {
            x = getNextX(x);
            swap(a, i, (int)(x % (i + 1)));
        }
        for(int i = 0; i < n; i++){
            if (i < d) {
                b[i] = 1;
            } else {
                b[i] = 0;
            }
        }
        for(int i = 0; i < n; i++){
            x = getNextX(x);
            swap(b, i, (int)(x % (i + 1)));
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
