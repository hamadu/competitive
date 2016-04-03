package codeforces.other2016.indiahacks2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/19.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        Set<Long> okSet = new HashSet<>();
        boolean[] wrong = new boolean[n];
        int ct = 0;

        Set<Integer> fixPos = new HashSet<>();
        for (int i = 0; i < n-1; i++) {
            if (a[i] < a[i+1] && i % 2 == 0) {
            } else if (a[i] > a[i+1] && i % 2 == 1) {
            } else {
                fixPos.add(i);
                wrong[i] = wrong[i+1] = true;
                ct++;
            }
        }
        if (ct <= 4) {
            for (int i = 0; i < n; i++) {
                if (wrong[i]) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            continue;
                        }
                        Set<Integer> confirmPos = new HashSet<>(fixPos);
                        confirmPos.add(i);
                        confirmPos.add(j);

                        swap(a, i, j);
                        if (confirm(a, confirmPos)) {
                            int ii = Math.min(i, j);
                            int jj = Math.max(i, j);
                            okSet.add(ii*100000000L+jj);
                        }
                        swap(a, i, j);
                    }
                }
            }
        }

        out.println(okSet.size());
        out.flush();
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean confirm(int[] a, Set<Integer> fixPos) {
        for (int p : fixPos) {
            for (int d = p-2 ; d <= p+2 ; d++) {
                if (d < 0 || d >= a.length-1) {
                    continue;
                }
                if (d % 2 == 0) {
                    if (a[d] >= a[d+1]) {
                        return false;
                    }
                } else {
                    if (a[d] <= a[d+1]) {
                        return false;
                    }
                }
            }
        }
        return true;
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
