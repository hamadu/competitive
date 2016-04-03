package atcoder.arc.arc030;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;

/**
 * Created by hama_du on 2016/03/15.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        Node root = null;
        for (int i = 0; i < n ; i++) {
            root = Node.merge(root, new Node(in.nextInt()));
        }

        while (--q >= 0) {
            int type = in.nextInt();
            int a = in.nextInt()-1;
            int b = in.nextInt();
            if (type == 1) {
                int val = in.nextInt();
                root = Node.add(root, a, b, val);
            } else if (type == 2) {
                int c = in.nextInt()-1;
                int d = in.nextInt();

                // [memo]
                // u[0] := [0, b), u[1] := [b, n)
                // v[0] := [0, a), v[1] := [a, b)
                Node[] u = Node.split(root, b);
                Node[] v = Node.split(u[0], a);
                Node cd = Node.split(Node.split(root, d)[0], c)[1];
                root = Node.merge(Node.merge(v[0], cd), u[1]);
            } else {
                Node[] u = Node.split(root, b);
                Node[] v = Node.split(u[0], a);
                out.println(Node.sum(v[1]));
                // out.println(Node.sum(root, a, b));
            }
            debug(q, root);
        }

        out.flush();
    }

    static class Node {
        static  Random _rnd = new Random(1);

        Node left, right;
        long value;
        long lazyValue;
        long sum;
        int count;

        public Node(long v) {
            value = v;
            lazyValue = 0;
            Node.update(this);
        }

        public Node clone() {
            Node n = new Node(value);
            n.left = left;
            n.right = right;
            n.lazyValue = lazyValue;
            return update(n);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node [value=");
            builder.append(value);
            builder.append(", count=");
            builder.append(count);
            builder.append(", plus=");
            builder.append(lazyValue);
            builder.append(", sum=");
            builder.append(sum);
            builder.append("]");
            return builder.toString();
        }


        static Node update(Node c) {
            if (c == null) {
                return null;
            }
            c.count = 1 + count(c.left) + count(c.right);
            c.sum = c.value + count(c) * c.lazyValue + sum(c.left) + sum(c.right);
            return c;
        }

        static Node propergate(Node c) {
            if (c == null) {
                return null;
            }
            if (c.lazyValue == 0) {
                return c.clone();
            }
            Node nc = c.clone();
            if (nc.left != null) {
                nc.left = c.left.clone();
                nc.left.lazyValue += c.lazyValue;
                update(nc.left);
            }
            if (nc.right != null) {
                nc.right = c.right.clone();
                nc.right.lazyValue += c.lazyValue;
                update(nc.right);
            }
            nc.value += nc.lazyValue;
            nc.lazyValue = 0;
            return update(nc);
        }

        static int count(Node c) {
            return c == null ? 0 : c.count;
        }

        static long sum(Node c) {
            return c == null ? 0 : c.sum;
        }


        static Node merge(Node a, Node b) {
            if (a == null) {
                return b;
            }
            if (b == null) {
                return a;
            }
            Node ac = propergate(a);
            Node bc = propergate(b);
            if (_rnd.nextInt(a.count + b.count) < a.count) {
                ac.right = merge(a.right, bc);
                return update(ac);
            } else {
                bc.left = merge(ac, bc.left);
                return update(bc);
            }
        }

        static Node[] split(Node c, int k) {
            if (c == null) {
                return new Node[2];
            }
            if (k <= count(c.left)) {
                Node cc = propergate(c);
                Node[] s = split(cc.left, k);
                cc.left = s[1];
                s[1] = update(cc);
                return s;
            } else {
                Node cc = propergate(c);
                Node[] s = split(cc.right, k - count(cc.left) - 1);
                cc.right = s[0];
                s[0] = update(cc);
                return s;
            }
        }

        public static Node add(Node a, int l, int r, int v) {
            if(a == null || r <= 0 || count(a) <= l) {
                return a;
            }
            if(l <= 0 && count(a) <= r) {
                propergate(a);
                Node ac = a.clone();
                ac.lazyValue += v;
                return update(ac);
            }else{
                propergate(a);
                Node ac = a.clone();
                if(0 < r && l < count(a.left)) {
                    ac.left = add(a.left, l, r, v);
                }
                if(count(a.left)+1 < r && l < count(a)) {
                    ac.right = add(a.right, l-count(a.left)-1, r-count(a.left)-1, v);
                }
                if(l <= count(a.left) && count(a.left) < r){
                    ac.value += v;
                }
                return update(ac);
            }
        }

        public static long sum(Node a, int l, int r) {
            if(a == null || r <= 0 || count(a) <= l) {
                return 0;
            }
            if(l <= 0 && count(a) <= r) {
                return a.sum;
            } else {
                long ret = 0;
                if(0 < r && l < count(a.left)) {
                    ret += sum(a.left, l, r);
                }
                if(count(a.left)+1 < r && l < count(a)) {
                    ret += sum(a.right, l-count(a.left)-1, r-count(a.left)-1);
                }
                if(l <= count(a.left) && count(a.left) < r){
                    ret += a.value;
                }
                ret += a.lazyValue * (Math.min(r, count(a)) - Math.max(0, l));
                return ret;
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
