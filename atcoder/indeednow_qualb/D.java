// http://indeednow-qualb.contest.atcoder.jp/submissions/362763
package atcoder.indeednow_qualb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int C = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt()-1;
        }
        List<Integer>[] pos = new List[C];
        for (int i = 0 ; i < C ; i++) {
            pos[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            pos[a[i]].add(i);
        }
        for (int i = 0 ; i < C ; i++) {
            pos[i].add(n);
        }

        for (int c = 0 ; c < C ; c++) {
            long ptn = (n+1L)*n/2;
            long now = -1;
            for (int p : pos[c]) {
                long diff = p - now - 1;
                ptn -= (diff+1)*diff/2;
                now = p;
            }
            out.println(ptn);
        }
        out.flush();
    }


    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int next() {
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

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}
