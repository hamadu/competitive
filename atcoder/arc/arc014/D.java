package atcoder.arc.arc014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/05/07.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int all = in.nextInt();
        int n = in.nextInt();
        int m = in.nextInt();
        long[] l = new long[n];
        for (int i = 0; i < n ; i++) {
            l[i] = in.nextInt();
        }
        long[][] q = new long[m][3];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                q[i][j] = in.nextInt();
            }
            q[i][2] = i;
        }
        long[] ans = new long[m];
        Arrays.sort(q, new Comparator<long[]>(){
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare((o1[0] + o1[1]), (o2[0] + o2[1]));
            }
        });
        long[] gaps = new long[n-1];
        for (int i = 0; i < n-1 ; i++) {
            gaps[i] = l[i+1] - l[i] - 1;
        }
        Arrays.sort(gaps);

        int gidx = 0;
        long gapSum = 0;
        for (int i = 0; i < m ; i++) {
            long sum = q[i][0] + q[i][1];
            while (gidx < n-1 && gaps[gidx] <= sum) {
                gapSum += gaps[gidx];
                gidx++;
            }
            long total = sum * (n - 1) + n;
            long diff = sum * gidx - gapSum;
            total -= diff;
            total += Math.min(l[0]-1, q[i][0]);
            total += Math.min(all-l[n-1], q[i][1]);

            int qidx = (int)q[i][2];
            ans[qidx] = total;
        }

        for (int i = 0; i < m ; i++) {
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
