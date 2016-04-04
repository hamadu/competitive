package codeforces.cf1xx.cr170.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/02.
 */
public class C {

    static class Cut implements Comparable<Cut> {
        static int __seq = 0;
        int seq;
        int pos;
        int fr;
        int to;
        public Cut(int p, int a, int b) {
            seq = --__seq;
            pos = p;
            fr = Math.min(a, b);
            to = Math.max(a, b);
        }

        @Override
        public int compareTo(Cut o) {
            if (fr == o.fr) {
                return seq - o.seq;
            }
            return fr - o.fr;
        }

        @Override
        public String toString() {
            return pos + ":" + fr + "/" + to;
        }
    }

    static class MeldableSegment {
        TreeSet<Segment> tree;

        public MeldableSegment() {
            tree = new TreeSet<>();
        }

        void meld(Segment s) {
            Segment left = tree.lower(s);
            if (left != null) {
                if (left.to + 1 <= s.fr) {
                    // ok.
                } else {
                    // merge
                    s.fr = left.fr;
                    s.to = Math.max(s.to, left.to);
                    tree.remove(left);
                }
            }
            while (true) {
                Segment right = tree.higher(s);
                if (right != null) {
                    if (s.to + 1 <= right.fr) {
                        break;
                    } else {
                        // merge.
                        tree.remove(right);
                        if (s.to <= right.to) {
                            s.to = right.to;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            tree.add(s);
        }

        static class Segment implements Comparable<Segment> {
            static int __seq = 0;
            int seq;
            int fr;
            int to;
            public Segment(int a, int b) {
                fr = Math.min(a, b);
                to = Math.max(a, b);
            }
            @Override
            public int compareTo(Segment o) {
                if (fr == o.fr) {
                    return seq - o.seq;
                }
                return fr - o.fr;
            }

            @Override
            public String toString() {
                return fr + "/" + to;
            }
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int x = in.nextInt();
        int y = in.nextInt();
        int k = in.nextInt();

        Map<Integer,TreeSet<Cut>> vcuts = new HashMap<>();
        Map<Integer,TreeSet<Cut>> hcuts = new HashMap<>();

        for (int i = 0; i < k ; i++) {
            int[] cut = new int[4];
            for (int j = 0; j < 4 ; j++) {
                cut[j] = in.nextInt();
            }
            if (cut[0] == cut[2]) {
                Cut cu = new Cut(cut[0], cut[1], cut[3]);
                mergeCuts(vcuts, cu);
            } else {
                Cut cu = new Cut(cut[1], cut[0], cut[2]);
                mergeCuts(hcuts, cu);
            }
        }

        int xor = 0;
        xor ^= (x - 1) % 2 == 0 ? 0 : y;
        xor ^= (y - 1) % 2 == 0 ? 0 : x;

        int[][] vpile = new int[100010][2];
        int[][] hpile = new int[100010][2];
        int vi = 0;
        int hi = 0;
        if (vcuts.size() < x - 1) {
            for (int xi = 1 ; xi <= x - 1 ; xi++) {
                if (!vcuts.containsKey(xi)) {
                    vpile[vi][0] = xi;
                    vpile[vi][1] = y;
                    vi++;
                    break;
                }
            }
        }
        if (hcuts.size() < y - 1) {
            for (int yi = 1 ; yi <= y - 1 ; yi++) {
                if (!hcuts.containsKey(yi)) {
                    hpile[hi][0] = yi;
                    hpile[hi][1] = x;
                    hi++;
                    break;
                }
            }
        }

        for (int key : vcuts.keySet()) {
            xor ^= y;
            int length = y;
            for (Cut c : vcuts.get(key)) {
                length -= c.to - c.fr;
            }
            vpile[vi][0] = key;
            vpile[vi][1] = length;
            vi++;
            xor ^= length;
        }
        for (int key : hcuts.keySet()) {
            xor ^= x;
            int length = x;
            for (Cut c : hcuts.get(key)) {
                length -= c.to - c.fr;
            }
            hpile[hi][0] = key;
            hpile[hi][1] = length;
            hi++;
            xor ^= length;
        }


        if (xor == 0) {
            out.println("SECOND");
        } else {
            out.println("FIRST");
            int[] cut = null;
            for (int v = 0 ; v < vi ; v++) {
                int txor = xor;
                txor ^= vpile[v][1];
                if (txor <= vpile[v][1]) {
                    int[] range = findRangeOfLength(vpile[v][0], vpile[v][1] - txor, vcuts);
                    cut = new int[] { vpile[v][0], range[0], vpile[v][0], range[1] };
                    break;
                }
            }
            if (cut == null) {
                for (int h = 0 ; h < hi ; h++) {
                    int txor = xor;
                    txor ^= hpile[h][1];
                    if (txor <= hpile[h][1]) {
                        int[] range = findRangeOfLength(hpile[h][0], hpile[h][1] - txor, hcuts);
                        cut = new int[] { range[0], hpile[h][0], range[1], hpile[h][0] };
                        break;
                    }
                }
            }
            out.println(cut[0] + " " + cut[1] + " " + cut[2] + " " + cut[3]);
        }
        out.flush();
    }

    private static int[] findRangeOfLength(int pos, int length, Map<Integer, TreeSet<Cut>> vcuts) {
        if (!vcuts.containsKey(pos)) {
            return new int[]{0, length};
        }


        TreeSet<Cut> ts = vcuts.get(pos);
        Cut[] ct = new Cut[ts.size()];
        int ci = 0;
        for (Cut c : ts) {
            ct[ci++] = c;
        }
        Arrays.sort(ct);

        int to = 0;
        for (int c = 0 ; c < ci ; c++) {
            if (to < ct[c].fr) {
                int earn = ct[c].fr - to;
                if (length <= earn) {
                    return new int[] {0, to + length};
                } else {
                    length -= earn;
                    to = ct[c].to;
                }
            } else {
                to = ct[c].to;
            }
        }
        return new int[] { 0, to + length };
    }


    static void mergeCuts(Map<Integer,TreeSet<Cut>> cutMap, Cut c) {
        if (!cutMap.containsKey(c.pos)) {
            TreeSet<Cut> ts = new TreeSet<>();
            ts.add(c);
            cutMap.put(c.pos, ts);
            return;
        }

        TreeSet<Cut> ts = cutMap.get(c.pos);
        Cut left = ts.lower(c);
        if (left != null) {
            if (left.to + 1 <= c.fr) {
                // ok.
            } else {
                // merge
                c.fr = left.fr;
                c.to = Math.max(c.to, left.to);
                ts.remove(left);
            }
        }
        while (true) {
            Cut right = ts.higher(c);
            if (right != null) {
                if (c.to + 1 <= right.fr) {
                    break;
                } else {
                    // merge.
                    ts.remove(right);
                    if (c.to <= right.to) {
                        c.to = right.to;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        ts.add(c);
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
