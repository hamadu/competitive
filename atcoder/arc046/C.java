package atcoder.arc046;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 2015/12/13.
 */
public class C {

    static class Man implements Comparable<Man> {
        int idx;
        int nen;
        int require;

        Man(int i, int j, int k) {
            idx = i;
            nen = j;
            require = k;
        }

        @Override
        public int compareTo(Man o) {
            if (require != o.require) {
                return require - o.require;
            }
            if (idx != o.idx) {
                return idx;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[][] man = new int[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= 1; j++) {
                man[i][j] = in.nextInt();
            }
        }
        int[][] woman = new int[m][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j <= 1; j++) {
                woman[i][j] = in.nextInt();
            }
        }

        // 男性:年収でソート
        Arrays.sort(man, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        // 女性:要求でソート
        Arrays.sort(woman, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        TreeSet<Man> manTree = new TreeSet<>();


        int cnt = 0;
        int midx = 0;
        for (int i = 0 ; i < m ; i++) {
            int need = woman[i][1];
            int nen = woman[i][0];
            while (midx < n && man[midx][0] >= need) {
                manTree.add(new Man(midx, man[midx][0], man[midx][1]));
                midx++;
            }
            Man best = manTree.lower(new Man(-1, -1, nen+1));
            if (best != null) {
                manTree.remove(best);
                cnt++;
            }
        }

        out.println(cnt);
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
