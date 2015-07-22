package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2341 {
    static final long INF = 3000000000000000000L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long K = in.nextLong();
        char[][] words = new char[n][];
        for (int i = 0; i < n ; i++) {
            words[i] = in.nextToken().toCharArray();
        }

        long[] dp = new long[m+1];
        dp[0] = 1;
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i + words[j].length <= m) {
                    int ti = i + words[j].length;
                    dp[ti] = Math.min(dp[ti]+dp[i], INF);
                }
            }
        }
        if (dp[m] < K) {
            out.println("-");
            out.flush();
            return;
        }

        Node root = Node.buildRoot();
        for (int i = 0; i < n ; i++) {
            root.dig(words[i], 0);
        }
        StringBuilder line = new StringBuilder();
        Map<Node,Long> currentNodeMap = new HashMap<Node,Long>();
        currentNodeMap.put(Node.root, 1L);

        long left = K;
        for (int i = 0 ; i < m ; i++) {
            for (int c = 0; c < 26 ; c++) {
                Map<Node,Long> nextNodeMap = new HashMap<Node,Long>();
                for (Node now : currentNodeMap.keySet()) {
                    long mul = currentNodeMap.get(now);
                    if (now.isFinal) {
                        if (Node.root.next[c] != null) {
                            addNodeToMap(Node.root.next[c], nextNodeMap, mul);
                        }
                    }
                    if (now.next[c] != null) {
                        addNodeToMap(now.next[c], nextNodeMap, mul);
                    }
                }
                BigInteger cnt = BigInteger.ZERO;
                BigInteger bleft = BigInteger.valueOf(left);
                long maxmul = 0;
                sch: for (Node toNode : nextNodeMap.keySet()) {
                    long mul = nextNodeMap.get(toNode);
                    maxmul = Math.max(maxmul, mul);
                    for (int f = 0; f <= 200 && 1 + i + f <= m; f++) {
                        if (toNode.degree[f] >= 1) {
                            int l = m - i - f - 1;

                            BigInteger A = BigInteger.valueOf(toNode.degree[f]);
                            BigInteger B = BigInteger.valueOf(dp[l]);
                            BigInteger C = BigInteger.valueOf(mul);
                            cnt = cnt.add(A.multiply(B).multiply(C));
                            if (cnt.compareTo(bleft) > 0) {
                                cnt = BigInteger.valueOf(INF);
                                break sch;
                            }
                        }
                    }
                }
                if (bleft.compareTo(cnt) > 0) {
                    left -= cnt.longValue();
                } else {
                    currentNodeMap = nextNodeMap;
                    line.append((char)('a' + c));
                    break;
                }
            }
        }
        out.println(line.toString());
        out.flush();
    }

    static void addNodeToMap(Node node, Map<Node,Long> nodeMap, long add) {
        if (nodeMap.containsKey(node)) {
            nodeMap.put(node, Math.min(nodeMap.get(node)+add, INF));
        } else {
            nodeMap.put(node, Math.min(add, INF));
        }
    }

    static class Node {
        static Node root;

        Node[] next;
        boolean isFinal;
        int[] degree;

        Node() {
            next = new Node[26];
            degree = new int[201];
        }

        static Node buildRoot() {
            Node node = new Node();
            Node.root = node;
            return node;
        }

        static Node build(Node parent) {
            Node node = new Node();
            return node;
        }

        void dig(char[] c, int idx) {
            int left = c.length - idx;
            degree[left]++;
            if (left == 0) {
                isFinal = true;
                return;
            }
            int a = c[idx]-'a';
            if (next[a] == null) {
                next[a] = build(this);
            }
            next[a].dig(c, idx+1);
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
