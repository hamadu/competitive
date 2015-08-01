package codeforces.cr293.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/01.
 */
public class E {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            String tk = in.nextToken();
            if (tk.equals("?")) {
                a[i] = INF;
            } else {
                a[i] = Integer.valueOf(tk);
            }
        }

        boolean isOK = true;
        long[] arr = new long[n];
        for (int i = 0; i < k ; i++) {
            int head = i;
            int ai = 0;
            while (head < n) {
                arr[ai++] = a[head];
                head += k;
            }

            long left = -INF;
            for (int j = 0; j < ai ; ) {
                if (arr[j] != INF) {
                    if (left >= arr[j]) {
                        isOK = false;
                        break;
                    }
                    left = arr[j];
                    j++;
                    continue;
                }

                int fr = j;
                while (j < ai && arr[j] == INF) {
                    j++;
                }
                long right = (j == ai) ? INF : arr[j];
                int cnt = j - fr;
                if (right - left - 1 < cnt) {
                    isOK = false;
                    break;
                }
                // fill from left
                long fillLeft = 0;
                for (int l = 1; l <= cnt ; l++) {
                    fillLeft += Math.abs(left+l);
                }

                // fill from right
                long fillRight = 0;
                for (int l = 1; l <= cnt ; l++) {
                    fillRight += Math.abs(right-l);
                }

                long fillZero = Long.MAX_VALUE;
                int bestZeroPos = -1;
                for (int l = 0; l < cnt ; l++) {
                    if (left < -l && cnt - l - 1 < right) {
                        // ok.
                        long L = l;
                        long R = Math.max(0, cnt - L - 1);
                        long need =  L*(L+1)/2 + R*(R+1)/2;
                        if (fillZero > need) {
                            fillZero = need;
                            bestZeroPos = l;
                        }
                    }
                }

                long bestCost = Math.min(fillLeft, Math.min(fillRight, fillZero));
                if (bestCost == fillLeft) {
                    for (int l = 0; l < cnt ; l++) {
                        arr[fr+l] = left+l+1;
                    }
                } else if (bestCost == fillRight) {
                    for (int l = 0; l < cnt ; l++) {
                        arr[fr+l] = right-(cnt-l);
                    }
                } else {
                    for (int l = 0; l < cnt ; l++) {
                        arr[fr+l] = l - bestZeroPos;
                    }
                }
            }
            for (int j = 0; j < ai; j++) {
                a[i+j*k] = arr[j];
            }
        }


        if (isOK) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n; i++) {
                line.append(' ').append(a[i]);
            }
            out.println(line.substring(1));
        } else {
            out.println("Incorrect sequence");
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
