package codeforces.cf3xx.cr310.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/07/09.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int[][] queries = new int[q][3];
        for (int i = 0; i < q ; i++) {
            queries[i][0] = in.nextInt()-1;
            queries[i][1] = in.nextInt()-1;
            queries[i][2] = in.nextChar();
        }

        Set<Integer> doneX = new HashSet<>();

        LargeSegmentTree rowSeg = new LargeSegmentTree(n);
        LargeSegmentTree colSeg = new LargeSegmentTree(n);
        for (int i = 0 ; i < q ; i++) {
            if (queries[i][2] == 'L') {
                rowSeg.dig(queries[i][1]);
            } else {
                colSeg.dig(queries[i][0]);
            }
        }

        for (int i = 0 ; i < q ; i++) {
            if (doneX.contains(queries[i][0])) {
                out.println(0);
                continue;
            }
            doneX.add(queries[i][0]);

            if (queries[i][2] == 'L') {
                int rightMost = rowSeg.max(queries[i][1]);
                colSeg.apply(Math.max(rightMost, 0), queries[i][0] + 1, queries[i][1]);
                out.println(queries[i][0] - rightMost);
            } else {
                int downMost = colSeg.max(queries[i][0]);
                rowSeg.apply(Math.max(downMost, 0), queries[i][1] + 1, queries[i][0]);
                out.println(queries[i][1] - downMost);
            }
        }
        out.flush();
    }

    static class LargeSegmentTree {
        static final int MAX = 3000000;

        int n;
        int nodeID;
        int[] fr;
        int[] to;
        int[] value;
        int[] leftID;
        int[] rightID;

        // [0,n)
        LargeSegmentTree(int _n) {
            n = _n;
            fr = new int[MAX];
            to = new int[MAX];
            value = new int[MAX];
            leftID = new int[MAX];
            rightID = new int[MAX];
            Arrays.fill(value, -1);
            Arrays.fill(leftID, -1);
            Arrays.fill(rightID, -1);
            addNode(0, n);
        }

        void addNode(int a, int b) {
            fr[nodeID] = a;
            to[nodeID] = b;
            nodeID++;
        }

        int addLeftNode(int parentID) {
            if (leftID[parentID] != -1) {
                return leftID[parentID];
            }
            fr[nodeID] = fr[parentID];
            to[nodeID] = (fr[parentID] + to[parentID]) / 2;
            leftID[parentID] = nodeID;
            return nodeID++;
        }

        int addRightNode(int parentID) {
            if (rightID[parentID] != -1) {
                return rightID[parentID];
            }
            fr[nodeID] = (fr[parentID] + to[parentID]) / 2;
            to[nodeID] = to[parentID];
            rightID[parentID] = nodeID;
            return nodeID++;
        }

        void dig(int pos) {
            int currentID = 0;
            while (to[currentID] - fr[currentID] > 1) {
                int med = (to[currentID] + fr[currentID]) / 2;
                if (fr[currentID] <= pos && pos < med) {
                    currentID = addLeftNode(currentID);
                } else {
                    currentID = addRightNode(currentID);
                }
            }
        }

        int max(int pos) {
            int currentID = 0;
            int val = -1;
            while (true) {
                val = Math.max(val, value[currentID]);
                if (to[currentID] - fr[currentID] == 1) {
                    break;
                }
                int med = (to[currentID] + fr[currentID]) / 2;
                if (fr[currentID] <= pos && pos < med) {
                    currentID = addLeftNode(currentID);
                } else {
                    currentID = addRightNode(currentID);
                }
            }
            return val;
        }

        void apply(int a, int b, int val) {
            apply(0, a, b, val);
        }

        // [a,b)
        void apply(int id, int a, int b, int val) {
            if (b <= fr[id] || to[id] <= a) {
                return;
            }
            if (a <= fr[id] && to[id] <= b) {
                value[id] = Math.max(value[id], val);
                return;
            }
            if (to[id] - fr[id] == 1) {
                return;
            }
            if (leftID[id] != -1) {
                apply(leftID[id], a, b, val);
            }
            if (rightID[id] != -1) {
                apply(rightID[id], a, b, val);
            }
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