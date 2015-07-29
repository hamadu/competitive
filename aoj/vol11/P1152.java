package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/29.
 */
public class P1152 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            char[] c = in.nextLine().toCharArray();
            if (c[0] == '0') {
                break;
            }
            out.println(solve(c));
        }


        out.flush();
    }

    private static String solve(char[] q) {
        c = q;
        int n = c.length;
        ops = new int[n];
        Stack<Integer> open = new Stack<Integer>();
        for (int i = 0; i < n ; i++) {
            if (c[i] == '(') {
                open.push(i);
            } else if (c[i] == ')') {
                int op = open.pop();
                int cl = i;
                ops[op] = cl;
                ops[cl] = op;
            } else {
                ops[i] = i;
            }
        }

        Node root = new Node(0, n-1);
        root.compute();
        root.doit(0);

        // debug(root.ptn);

        return root.toString();
    }

    private static class Node {
        Node left;
        Node right;

        int common = 0;
        int all = 0;
        Set<String> ptn;

        // [l,r]
        Node(int l, int r) {
            ptn = new HashSet<String>();
            if (l == r) {
                left = right = null;
            } else {
                l++;
                r--;
                left = new Node(l, ops[l]);
                right = new Node(ops[r], r);
            }
        }

        void compute() {
            if (left == null) {
                all = 1;
                common = 0;
                ptn.add("x");
                return;
            }
            left.compute();
            right.compute();
            ptn.addAll(left.ptn);
            ptn.addAll(right.ptn);

            common = 0;
            all = ptn.size();
            for (String p : ptn) {
                if (left.ptn.contains(p) && right.ptn.contains(p)) {
                    common++;
                }
            }

            String lr = "(" + left.toString() + " " + right.toString() + ")";
            String rl = "(" + right.toString() + " " + left.toString() + ")";
            if (left.toString().compareTo(right.toString()) < 0) {
                ptn.add(lr);
            } else {
                ptn.add(rl);
                Node tmp = right;
                right = left;
                left = tmp;
            }
        }

        public void doit(int flg) {
            if (left == null) {
                return;
            }
            Node[] ord = ord(left, right);
            if (flg <= 0) {
                left = ord[0];
                right = ord[1];
                left.doit(-1);
                right.doit(1);
            } else {
                left = ord[1];
                right = ord[0];
                left.doit(-1);
                right.doit(1);
            }
        }

        public boolean isLeaf() {
            return left == null;
        }

        // leftがつよいなら<0
        public static int compare(Node left, Node right) {
            return left.common * right.all - left.all * right.common;
        }

        public static Node[] ord(Node left, Node right) {
            if (left == null) {
                return null;
            }
            Node[] lr = {left, right};
            Node[] rl = {right, left};
            Node[] same = {right, left, null};

            int lrv = compare(left, right);
            if (lrv != 0) {
                return lrv < 0 ? lr : rl;
            }
            if (left.isLeaf() && right.isLeaf()) {
                return same;
            }

            Node[] l = ord(left.left, left.right);
            Node[] r = ord(right.left, right.right);
            Node[] lrStrong = ord(l[0], r[0]);
            if (lrStrong.length <= 2) {
                return lrStrong[0] == l[0] ? lr : rl;
            }

            Node[] lrWeak = ord(l[1], r[1]);
            if (lrWeak.length <= 2) {
                return lrWeak[0] == l[1] ? lr : rl;
            }
            return same;
        }


        @Override
        public String toString() {
            if (left == null) {
                return "x";
            }
            return "(" + left.toString() + " " + right.toString() + ")";
        }
    }

    private static char[] c;
    private static int[] ops;



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
