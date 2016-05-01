package atcoder.other2014.codefestival2014.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 4/29/16.
 */
public class H {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        char[] s = in.nextToken().toCharArray();

        int[] floor = new int[n];
        int max = 0;
        int min = 0;
        int[] heights = new int[n+1];
        heights[0] = k;
        for (int i = 0; i < n ; i++) {
            // [!] make sure that heights[min] >= 1 and heights[max] >= 1 in here
            if (s[i] == '1') {
                floor[i] = max + 1;
                heights[max] -= 1;
                heights[max+1] += 1;
                max++;
            } else {
                floor[i] = min + 1;
                heights[min] -= 1;
                heights[min+1] += 1;
                max = Math.max(max, min+1);
                if (heights[min] == 0) {
                    min++;
                }
            }
        }

        double[] ans = new double[n];
        double[] prob = new double[n+1];
        for (int i = n-1 ; i >= 0 ; i--) {
            int h = floor[i];
            ans[i] = h + prob[h];
            heights[h]--;
            heights[h-1]++;
            prob[h-1] = ((prob[h] + 1) + (heights[h-1] - 1) * prob[h-1]) / heights[h-1];
            // debug(prob[h]+1,prob[h-1],"to",1,(heights[h-1] - 1),"adding",h-1);
        }
        for (int i = 0; i < n ; i++) {
            out.println(ans[i]);
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
