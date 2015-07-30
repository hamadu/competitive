package aoj.vol21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/30.
 */
public class P2176 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int d = in.nextInt();
            if (n + d == 0) {
                break;
            }
            int[][] missiles = new int[n][];
            for (int i = 0; i < n ; i++) {
                int m = in.nextInt();
                missiles[i] = new int[m];
                for (int j = 0; j < m ; j++) {
                    missiles[i][j] = in.nextInt();
                }
            }

            out.println(solve(d, missiles) ? "Yes" : "No");
        }

        out.flush();
    }

    private static boolean solve(int d, final int[][] missiles) {
        int n = missiles.length;
        int left = 0;
        for (int i = 0; i < n ; i++) {
            left += missiles[i].length;
        }


        TreeSet<Country> capTree = new TreeSet<Country>(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if (o1.cap == o2.cap) {
                    return o1.idx - o2.idx;
                }
                return o1.cap - o2.cap;
            }
        });
        List<Country> countries = new ArrayList<Country>();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < missiles[i].length ; j++) {
                sum += missiles[i][j];
            }
            Country ct = new Country(i, missiles[i].length-1, sum);
            capTree.add(ct);
            countries.add(ct);
        }


        while (--left >= 0) {
            boolean found = false;
            for (Country c : countries) {
                if (c.qty == -1) {
                    continue;
                }
                capTree.remove(c);
                c.cap -= missiles[c.idx][c.qty];
                c.qty--;
                capTree.add(c);
                if (capTree.last().cap - capTree.first().cap <= d) {
                    found = true;
                    break;
                }
                capTree.remove(c);
                c.cap += missiles[c.idx][c.qty+1];
                c.qty++;
                capTree.add(c);
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    static class Country {
        int idx;
        int qty;
        int cap;
        Country(int i, int q, int c) {
            idx = i;
            qty = q;
            cap = c;
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
