package codeforces.cr310.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/09.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        long[][] islands = new long[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                islands[i][j] = in.nextLong();
            }
        }

        // [idx,min,max]

        Gap[] gaps = new Gap[n-1];
        List<Event> events = new ArrayList<>();
        for (int i = 0 ; i < n -1 ; i++) {
            Gap gap = new Gap();
            gap.idx = i;
            gap.from = islands[i+1][0] - islands[i][1];
            gap.to = islands[i+1][1] - islands[i][0] + 1;
            gaps[i] = gap;

            events.add(Event.buildGapStartEvent(gap.idx, gap.from));
            events.add(Event.buildGapEndEvent(gap.idx, gap.to));
        }
        for (int i = 0 ; i < m ; i++) {
            events.add(Event.buildBridgeEvent(i, in.nextLong()));
        }
        Collections.sort(events);


        boolean isOK = true;
        int[] gapToBridges = new int[n-1];
        TreeSet<Gap> availableGaps = new TreeSet<>();
        for (Event e : events) {
            switch (e.type) {
                case GAP_END:
                    if (availableGaps.contains(gaps[e.idx])) {
                        availableGaps.remove(gaps[e.idx]);
                        isOK = false;
                    }
                    break;

                case GAP_START:
                    availableGaps.add(gaps[e.idx]);
                    break;

                case BRIDGE:
                    if (availableGaps.size() >= 1) {
                        Gap earliestGap = availableGaps.first();
                        gapToBridges[earliestGap.idx] = e.idx;
                        availableGaps.remove(earliestGap);
                    }
                    break;
            }
        }

        if (isOK) {
            out.println("Yes");
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n - 1; i++) {
                line.append(' ').append(gapToBridges[i]+1);
            }
            out.println(line.substring(1));
        } else {
            out.println("No");
        }
        out.flush();
    }

    static class Gap implements Comparable<Gap> {
        int idx;
        long from;
        long to;

        @Override
        public int compareTo(Gap o) {
            if (to != o.to) {
                return Long.compare(to, o.to);
            }
            return idx - o.idx;
        }
    }


    enum EventType {
        GAP_END,
        GAP_START,
        BRIDGE,
    }

    static class Event implements Comparable<Event> {
        EventType type;
        int idx;
        long value;

        public static Event buildGapStartEvent(int gapIdx, long inclusiveFrom) {
            Event e = new Event();
            e.type = EventType.GAP_START;
            e.idx = gapIdx;
            e.value = inclusiveFrom;
            return e;
        }

        public static Event buildGapEndEvent(int gapIdx, long exclusiveTo) {
            Event e = new Event();
            e.type = EventType.GAP_END;
            e.idx = gapIdx;
            e.value = exclusiveTo;
            return e;
        }

        public static Event buildBridgeEvent(int bridgeIdx, long length) {
            Event e = new Event();
            e.type = EventType.BRIDGE;
            e.idx = bridgeIdx;
            e.value = length;
            return e;
        }


        @Override
        public int compareTo(Event o) {
            if (value != o.value) {
                return Long.compare(value, o.value);
            }
            return type.ordinal() - o.type.ordinal();
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
