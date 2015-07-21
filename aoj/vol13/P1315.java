package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1315 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }

            List<List<Entry>> entries = new ArrayList<List<Entry>>();
            String pday = "";
            List<Entry> dayEntries = new ArrayList<Entry>();
            for (int i = 0; i < n ; i++) {
                String day = in.nextToken();
                if (!pday.equals(day)) {
                    if (dayEntries.size() >= 1) {
                        entries.add(dayEntries);
                    }
                    pday = day;
                    dayEntries = new ArrayList<Entry>();
                }
                String time = in.nextToken();
                String io = in.nextToken();
                String id = in.nextToken();
                dayEntries.add(new Entry(day, time, io, id));
            }
            if (dayEntries.size() >= 1) {
                entries.add(dayEntries);
            }
            out.println(solve(entries));
        }

        out.flush();
    }

    private static int solve(List<List<Entry>> entries) {
        int[] blessedTime = new int[1000];
        int[] entryTime = new int[1000];

        for (List<Entry> dayEntries : entries) {
            Set<Integer> entered = new HashSet<Integer>();
            for (Entry e : dayEntries) {
                if (e.entered) {
                    entered.add(e.id);
                    entryTime[e.id] = e.time;
                } else {
                    if (e.isGoddess()) {
                        for (int id : entered) {
                            blessedTime[id] += e.time - Math.max(entryTime[0], entryTime[id]);
                        }
                    } else {
                        if (entered.contains(0)) {
                            blessedTime[e.id] += e.time - Math.max(entryTime[0], entryTime[e.id]);
                        }
                    }
                    entered.remove(e.id);
                }
            }
        }
        int max = 0;
        for (int i = 1 ; i < blessedTime.length; i++) {
            max = Math.max(max, blessedTime[i]);
        }
        return max;
    }

    static class Entry {
        String day;
        int time;
        boolean entered;
        int id;

        Entry(String d, String t, String io, String i) {
            day = d;
            time = Integer.valueOf(t.substring(0, 2)) * 60 + Integer.valueOf(t.substring(3, 5));
            entered = io.equals("I");
            id = Integer.valueOf(i);
        }

        boolean isGoddess() {
            return id == 0;
        }

        public String toString() {
            return day + "/" + time + "/" + entered + "/" + id;
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
