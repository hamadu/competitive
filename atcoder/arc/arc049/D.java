package atcoder.arc.arc049;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/03/19.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int num = (1<<n)+5;

        tree = new SegmentTreeRangeSum(new int[num]);

        // tree.add(4, 5, 1);
        // debug(tree.get(4));

        leftNode = new int[num];
        rightNode = new int[num];
        for (int i = 0; i < num ; i++) {
            leftNode[i] = i*2+1;
            rightNode[i] = i*2+2;
        }

        while (--q >= 0) {
            int type = in.nextInt();
            int a = in.nextInt()-1;
            int b = in.nextInt();
            if (type == 1) {
                int now = getValueOf(n, n, a);
                out.println(now+2-(1<<n));
            } else {
                int min = 0;
                int max = 1;
                for (int i = 0 ; i < n ; i++) {
                    if (a <= min && max <= b) {
                        tree.add(min, max, 1);
                    } else if (b <= min || max <= a) {
                        continue;
                    } else {
                        int left = getValueOf(n, i, a - min);
                        int right = getValueOf(n, i, b - min);
                        int apos = a - min;
                        int bpos = b - min;



                    }
                }


                tree.add(a, b, 1);
            }
        }
        out.flush();
    }

    static int getValueOf(int n, int level, int pos) {
        int now = 0;
        for (int k = 0 ; k < level ; k++) {
            int half = (1<<(n-k-1));
            if (pos < half) {
                now = goLeft(now);
            } else {
                now = goRight(now);
                pos -= half;
            }
        }
        return now;
    }


    static int[] leftNode;
    static int[] rightNode;
    static SegmentTreeRangeSum tree;

    static void resolve(int num) {
        int k = tree.get(num);
        if (k % 2 == 1) {
            swap(num);
        }
        tree.add(num, num+1, -k);
    }

    static int goLeft(int num) {
        resolve(num);
        return leftNode[num];
    }

    static int goRight(int num) {
        resolve(num);
        return rightNode[num];
    }

    static void swap(int idx) {
        int tmp = leftNode[idx];
        leftNode[idx] = rightNode[idx];
        rightNode[idx] = tmp;
    }

    public static class SegmentTreeRangeSum{
        int N;
        int M;
        int[] seg;

        public SegmentTreeRangeSum(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
        }

        public int get(int idx) {
            int value = seg[M+idx];
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                value += seg[i];
                if (i == 0) {
                    break;
                }
            }
            return value;
        }

        public void add(int l, int r, int x) {
            add(l, r, 0, 0, M+1, x);
        }

        public void add(int l, int r, int idx, int fr, int to, int x) {
            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r) {
                seg[idx] += x;
                return;
            }

            int med = (fr+to) / 2;
            add(l, r, idx*2+1, fr, med, x);
            add(l, r, idx*2+2, med, to, x);
        }

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
