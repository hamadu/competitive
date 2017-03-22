package csacademy.round019;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] arr = in.nextInts(n);
        int[] deg = new int[100010];
        for (int i = 0; i < arr.length ; i++) {
            deg[arr[i]]++;
        }

        nums = new PriorityQueue<>((u, v) -> u.number - v.number);
        degSet = new TreeSet<>();
        for (int i = 0; i < deg.length; i++) {
            if (deg[i] >= 1) {
                Num nu = new Num(i, deg[i]);
                nums.add(nu);
                degSet.add(nu);
            }
        }

        line = new StringBuilder();
        left = n;
        last = -1;
        while (left >= 1) {
            Num maxDeg = degSet.first();
            if (last != maxDeg.number && left % 2 == 1) {
                int canPlace = (left + 1) / 2;
                if (canPlace == maxDeg.count) {
                    add(maxDeg);
                    continue;
                } else if (canPlace < maxDeg.count) {
                    line = null;
                    break;
                }
            }
            Num minNum = nums.peek();
            if (minNum.number == last) {
                if (nums.size() == 1) {
                    line = null;
                    break;
                }
                minNum = nums.poll();
                add(nums.peek());
                nums.add(minNum);
            } else {
                add(minNum);
            }
        }

        if (line == null) {
            out.println(-1);
        } else {
            out.println(line.substring(1));
        }
        out.flush();
    }

    static int left;
    static int last;
    static Queue<Num> nums;
    static TreeSet<Num> degSet;
    static StringBuilder line;

    static void add(Num addNum) {
        line.append(' ').append(addNum.number);
        nums.remove(addNum);
        degSet.remove(addNum);
        addNum.count--;
        if (addNum.count >= 1) {
            nums.add(addNum);
            degSet.add(addNum);
        }
        last = addNum.number;
        left--;
    }

    static class Num implements Comparable<Num> {
        int number;
        int count;

        Num(int n, int c) {
            number = n;
            count = c;
        }

        @Override
        public int compareTo(Num o) {
            return count == o.count ? number - o.number : o.count - count;
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
