package codeforces.cf0xx.cr66.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/09/07.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        List<Racer> racers = new ArrayList<>();
        Map<String,Racer> nameMap = new HashMap<>();
        for (int i = 0; i < n ; i++) {
            String x = in.nextToken();
            int p = in.nextInt();
            Racer r = new Racer(x, p);
            racers.add(r);
            nameMap.put(x, r);
        }
        int m = in.nextInt();
        int[] ord = new int[n];
        for (int i = 0; i < m ; i++) {
            ord[i] = in.nextInt();
        }
        Arrays.sort(ord);

        Racer my = nameMap.get(in.nextToken());
        racers.remove(my);

        int low = doitLow(my, racers, ord);
        int high = doitHigh(my, racers, ord);
        out.println(String.format("%d %d", high, low));
        out.flush();
    }

    private static int doitHigh(Racer my, List<Racer> racers, int[] ord) {
        int n = ord.length;
        int myScore = my.point + ord[n-1];
        Collections.sort(racers);
        Collections.reverse(racers);
        int rank = 1;
        int oi = 0;
        int ot = n-2;
        for (Racer r : racers) {
            if (r.point > myScore || (r.point == myScore && r.name.compareTo(my.name) < 0)) {
                // nothing to do.
                rank++;
            } else if (oi <= n-2) {
                if (r.point+ord[oi] < myScore || r.point+ord[oi] == myScore && my.name.compareTo(r.name) < 0) {
                    // cannot over my score
                    oi++;
                } else {
                    rank++;
                }
            }
        }
        return rank;
    }

    private static int doitLow(Racer my, List<Racer> racers, int[] ord) {
        int myScore = my.point + ord[0];
        Collections.sort(racers);
        int rank = 1;
        int oi = ord.length-1;
        for (Racer r : racers) {
            if (r.point > myScore || (r.point == myScore && r.name.compareTo(my.name) < 0)) {
                // nothing to do
                rank++;
            } else if (oi >= 1) {
                if (r.point+ord[oi] < myScore || r.point+ord[oi] == myScore && my.name.compareTo(r.name) < 0) {
                    // cannot over my score
                } else {
                    oi--;
                    rank++;
                }
            }
        }
        return rank;
    }

    static class Racer implements Comparable<Racer> {
        String name;
        int point;

        Racer(String x, int p) {
            name = x;
            point = p;
        }

        @Override
        public int compareTo(Racer o) {
            if (point != o.point) {
                return point - o.point;
            }
            return o.name.compareTo(name);
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
