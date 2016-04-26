package atcoder.other2016.s8pc002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 4/25/16.
 */
public class H {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();

        int BUCKET_SIZE = 320;
        int bn = (n + BUCKET_SIZE - 1) / BUCKET_SIZE;
        int[] flip = new int[bn];
        int[] count = new int[bn];
        int[] blen = new int[bn];
        for (int bi = 0; bi < bn ; bi++) {
            blen[bi] = (bi == bn - 1) ? n % BUCKET_SIZE : BUCKET_SIZE;
        }

        int[] arr = new int[n];
        while (--q >= 0) {
            int type = in.nextInt();
            int l = in.nextInt();
            int r = in.nextInt();
            int bfrom = l / BUCKET_SIZE;
            int bto = (r - 1) / BUCKET_SIZE;
            if (type == 1) {
                for (int bi = bfrom ; bi <= bto; bi++) {
                    if (bi == bfrom || bi == bto) {
                        for (int i = bi * BUCKET_SIZE ; i < bi * BUCKET_SIZE + blen[bi] ; i++) {
                            if (l <= i && i < r) {
                                count[bi] -= flip[bi] ^ arr[i];
                                arr[i] ^= 1;
                                count[bi] += flip[bi] ^ arr[i];
                            }
                        }
                    } else {
                        flip[bi] ^= 1;
                        count[bi] = blen[bi] - count[bi];
                    }
                }
            } else {
                int ans = 0;
                for (int bi = bfrom ; bi <= bto; bi++) {
                    if (bi == bfrom || bi == bto) {
                        for (int i = bi * BUCKET_SIZE ; i < bi * BUCKET_SIZE + blen[bi] ; i++) {
                            if (l <= i && i < r) {
                                ans += flip[bi] ^ arr[i];
                            }
                        }
                    } else {
                        ans += count[bi];
                    }
                }
                out.println(ans);
            }
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
