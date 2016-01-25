package hackerrank.codestorm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/10/30.
 */
public class Triangles {
    private static final int INF = 1000000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] stick = new int[n];

        TreeSet<Integer> t2 = new TreeSet<>();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(-1, 1);
        t2.add(-1);
        map.put(INF, n+2);
        t2.add(INF);

        for (int i = 0; i < n ; i++) {
            stick[i] = in.nextInt();
            map.put(stick[i]*stick[i], i+2);
            t2.add(stick[i]*stick[i]);
        }


        long acute = 0;
        long right = 0;
        long obtuse = 0;
        for (int a = 0; a < n ; a++) {
            int c = a+1;
            int left = a+1;
            for (int b = a+2 ; b < n ; b++) {
                int a2 = stick[a] * stick[a];
                int b2 = stick[b] * stick[b];
                int c2 = b2-a2;
                while (c < b && stick[c]*stick[c] < c2) {
                    c++;
                }
                while (left < b && stick[a] + stick[left] <= stick[b]) {
                    left++;
                }

                if (c2 == stick[c]*stick[c]) {
                    right++;
                    obtuse += Math.max(0, (c - 1) - left + 1);
                    acute  += Math.max(0, b - (c + 1));
                } else {
                    obtuse += Math.max(0, (c - 1) - left + 1);
                    acute += Math.max(0, b - c);
                }
            }
        }

        out.println(String.format("%d %d %d", acute, right, obtuse));
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
