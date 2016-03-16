package codeforces.cr344.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/10.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        int[][] queries = new int[m][3];
        for (int i = 0; i < m ; i++) {
            queries[i][0] = i;
            queries[i][1] = in.nextInt();
            queries[i][2] = in.nextInt();
        }
        Arrays.sort(queries, (o1, o2) -> o2[2] - o1[2]);

        List<int[]> operations = new ArrayList<>();
        {
            int head = -1;
            for (int i = 0; i < m; ) {
                int mi = -1;
                int fi = i;
                int ti = i;
                while (ti < m && queries[fi][2] == queries[ti][2]) {
                    if (head < queries[ti][0]) {
                        head = queries[ti][0];
                        mi = ti;
                    }
                    ti++;
                }
                if (mi != -1) {
                    operations.add(queries[mi]);
                }
                i = ti;
            }
        }

        boolean first = true;
        int[] answer = new int[n];

        int head = n;
        int lastOrder = 1;
        int trackHead = 0;
        int trackTail = n;
        for (int[] op : operations) {
            int toHead = op[2];
            if (first) {
                head = op[2];
                trackTail = op[2]-1;
                Arrays.sort(a, 0, op[2]);
                for (int i = op[2] ; i < n ; i++) {
                    answer[i] = a[i];
                }
                first = false;
            }
            while (--head >= toHead) {
                answer[head] = a[trackTail];
                if (trackHead < trackTail) {
                    trackTail--;
                } else {
                    trackTail++;
                }
            }
            head = toHead;
            if (lastOrder != op[1]) {
                int tmp = trackHead;
                trackHead = trackTail;
                trackTail = tmp;
            }
            lastOrder = op[1];
        }
        while (--head >= 0) {
            answer[head] = a[trackTail];
            if (trackHead < trackTail) {
                trackTail--;
            } else {
                trackTail++;
            }
        }

        for (int i = 0 ; i < n ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(answer[i]);
        }
        out.println();
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
