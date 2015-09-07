package atcoder.tenka2015.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/05.
 */
public class C {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);


        w = in.nextInt();
        int h = in.nextInt();
        int[] ord = new int[w];
        for (int i = 0; i < w ; i++) {
            ord[i] = i;
        }
        for (int i = 0; i < h ; i++) {
            int d = in.nextInt();
            int tmp = ord[d];
            ord[d] = ord[d+1];
            ord[d+1] = tmp;
        }
        int[] want = new int[w];
        for (int i = 0; i < w; i++) {
            want[i] = in.nextInt();
        }

        int initialID = id(ord);
        int wantID = id(want);

        Map<Integer,Integer> memo = new HashMap<>();
        Queue<Integer> q = new ArrayBlockingQueue<>(10000);
        memo.put(initialID, 0);
        q.add(initialID);
        q.add(0);
        while (q.size() >= 1) {
            int[] o = revid(q.poll());
            int time = q.poll();
            for (int i = 0; i < w-1 ; i++) {
                swap(o, i, i+1);
                int tid = id(o);
                if (!memo.containsKey(tid)) {
                    memo.put(tid, time+1);
                    q.add(tid);
                    q.add(time+1);
                }
                swap(o, i, i+1);
            }
        }

        out.println(memo.get(wantID));
        out.flush();
    }

    private static int[] revid(int poll) {
        int[] x = new int[w];
        for (int i = 0; i < w ; i++) {
            x[i] = (poll>>(i*3))&7;
        }
        return x;
    }

    static int w;

    public static int id(int[] ord) {
        int id = 0;
        for (int i = 0; i < w ; i++) {
            id |= ord[i]<<(3*i);
        }
        return id;
    }

    public static void swap(int[] ord, int i, int j) {
        int tmp = ord[i];
        ord[i] = ord[j];
        ord[j] = tmp;
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
