package facebook.fhc2016.round1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/01/10.
 */
public class Laundro {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int l = in.nextInt();
            int n = in.nextInt();
            int m = in.nextInt();
            long D = in.nextInt();
            long[] wash = new long[n];
            for (int i = 0; i < n ; i++) {
                wash[i] = in.nextLong();
            }
            out.println(String.format("Case #%d: %d", c, solve(l, n, m, wash, D)));
        }
        out.flush();
    }

    private static long solve(int l, int n, long m, long[] wash, long dry) {
        long min = -1;
        long max = (long)1e18;
        while (max - min > 1) {
            long med = (max + min) / 2;
            long sumL = 0;
            for (int i = 0 ; i < n ; i++) {
                sumL += med / wash[i];
                if (sumL >= l) {
                    break;
                }
            }
            if (sumL >= l) {
                max = med;
            } else {
                min = med;
            }
        }

        // washing will finish at time max.
        List<Long> washFinishes = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            long num = max / wash[i];
            for (long t = 1 ; t <= num ; t++) {
                washFinishes.add(wash[i] * t);
            }
        }
        Collections.sort(washFinishes);

        Queue<Event> lq = new ArrayBlockingQueue<>(l+100);
        Queue<Event> q = new PriorityQueue<>();
        for (int i = 0 ; i < l ; i++) {
            q.add(new Event(washFinishes.get(i), 1));
        }
        long time = 0;
        while (q.size() >= 1) {
            Event e = q.poll();
            if (e.kind == 0) {
                time = Math.max(time, e.time);
                m++;
                while (lq.size() >= 1 && m >= 1) {
                    lq.poll();
                    m--;
                    q.add(new Event(e.time + dry, 0));
                }
            } else {
                if (m >= 1) {
                    m--;
                    q.add(new Event(e.time + dry, 0));
                } else {
                    lq.add(e);
                }
            }
        }
        return time;
    }

    static class Event implements Comparable<Event> {
        long time;
        long kind;

        public Event(long t, long k) {
            time = t;
            kind = k;
        }

        @Override
        public int compareTo(Event o) {
            if (time != o.time) {
                return Long.compare(time, o.time);
            }
            return Long.compare(kind, o.kind);
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
