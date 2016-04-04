package codeforces.cf3xx.cr306.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/06.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int k = in.nextInt();
        if (k % 2 == 0) {
            out.println("NO");
            out.flush();
            return;
        }

        List<int[]> half = new ArrayList<>();
        int head = 1;
        for (int i = 3 ; i <= k ; i += 2) {
            if (i == 3) {
                int a = head;
                int b = head+1;
                int x = head+2;
                int y = head+3;
                half.add(new int[]{0, a});
                half.add(new int[]{0, b});
                half.add(new int[]{a, x});
                half.add(new int[]{b, y});
                half.add(new int[]{a, y});
                half.add(new int[]{b, x});
                half.add(new int[]{x, y});
                head += 4;
            } else {
                int x = head;
                int y = head+1;
                half.add(new int[]{0, x});
                half.add(new int[]{0, y});
                for (int h = 1 ; h < head ; h++) {
                    half.add(new int[]{h, x});
                    half.add(new int[]{h, y});
                }
                head += 2;
            }
        }

        List<int[]> ans = new ArrayList<>(half);
        for (int[] h : half) {
            ans.add(new int[]{ h[0]+head, h[1]+head });
        }
        ans.add(new int[]{ 0, head });

        out.println("YES");
        out.println(String.format("%d %d", head*2, ans.size()));
        for (int[] a : ans) {
            out.println(String.format("%d %d", a[0]+1, a[1]+1));
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
