package codeforces.cr283.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/17.
 */
public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        Part[] parts = new Part[n];
        for (int i = 0 ; i < n ; i++) {
            parts[i] = new Part(in.nextInt(), in.nextInt(), i);
        }

        int m = in.nextInt();
        Actor[] actors = new Actor[m];
        for (int i = 0; i < m ; i++) {
            actors[i] = new Actor(in.nextInt(), in.nextInt(), in.nextInt(), i);
        }

        List<Event> events = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            events.add(Event.buildEventPartQuery(parts[i]));
        }
        for (int i = 0 ; i < m ; i++) {
            events.add(Event.buildEventActorEnter(actors[i]));
            events.add(Event.buildEventActorLeave(actors[i]));
        }
        Collections.sort(events);

        TreeSet<Actor> availableActors = new TreeSet<>();
        int[] ans = new int[n];
        boolean found = true;

        for (Event e : events) {
            long type = e.at & 3;
            if (type == 0) {
                availableActors.add(e.actor);
            } else if (type == 1) {
                Part part = e.part;
                Actor bestActor =  availableActors.higher(Actor.pseudoActor(part.to));
                if (bestActor == null) {
                    found = false;
                    break;
                }
                ans[part.idx] = bestActor.idx+1;
                bestActor.cnt--;
                if (bestActor.cnt == 0) {
                    availableActors.remove(bestActor);
                }
            } else {
                availableActors.remove(e.actor);
            }
        }

        if (found) {
            out.println("YES");
            StringBuilder b = new StringBuilder();
            for (int a : ans) {
                b.append(' ').append(a);
            }
            out.println(b.substring(1));
        } else {
            out.println("NO");
        }
        out.flush();
    }

    static class Event implements Comparable<Event> {
        long at;
        Part part;
        Actor actor;

        public static Event buildEventActorEnter(Actor actor) {
            Event e = new Event();
            e.actor = actor;
            e.at = ((1L *actor.fr) << 2) + 0;
            return e;
        }

        public static Event buildEventPartQuery(Part part) {
            Event e = new Event();
            e.part = part;
            e.at = ((1L * part.fr) << 2) + 1;
            return e;
        }

        public static Event buildEventActorLeave(Actor actor) {
            Event e = new Event();
            e.actor = actor;
            e.at = ((1L * actor.to) << 2) + 2;
            return e;
        }

        @Override
        public int compareTo(Event o) {
            return Long.compare(at, o.at);
        }
    }

    static class Part {
        int fr;
        int to;
        int idx;

        public Part(int f, int t, int i) {
            fr = f;
            to = t;
            idx = i;
        }
    }

    static class Actor implements Comparable<Actor> {
        int fr;
        int to;
        int cnt;
        int idx;

        public static Actor pseudoActor(int to) {
            return new Actor(0, to, 1, -1);
        }

        public Actor(int f, int t, int c, int i) {
            fr = f;
            to = t;
            cnt = c;
            idx = i;
        }

        @Override
        public int compareTo(Actor o) {
            if (to == o.to) {
                return idx - o.idx;
            }
            return to - o.to;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
