package atcoder.other2015.ijpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/10/18.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] words = new int[26][26];

        List<Integer>[][] ids = new List[26][26];
        for (int i = 0; i < 26 ; i++) {
            for (int j = 0; j < 26 ; j++) {
                ids[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < n ; i++) {
            char[] c = in.nextToken().toCharArray();
            words[c[0]-'a'][c[c.length-1]-'a']++;
            ids[c[0]-'a'][c[c.length-1]-'a'].add(i);
        }

        Node root = solve(n, words);
        if (root == null) {
            out.println("NO");
        } else {
            out.println("YES");

            int[][] den = new int[26][26];
            root.setNum(ids, den);

            int[] ans = new int[n];
            root.computeAns(ans);

            for (int i = 0; i < ans.length ; i++) {
                out.println(ans[i]);
            }
        }
        out.flush();
    }

    private static Node solve(int n, int[][] words) {
        if (n % 2 == 0) {
            return null;
        }
        int A = words.length;
        int[] wn = new int[A];
        int head = -1;
        int hc = 0;
        for (int i = 0; i < A ; i++) {
            for (int j = 0; j < A ; j++) {
                wn[i] += words[i][j];
            }
            if (wn[i] % 2 == 1) {
                head = i;
                hc++;
            }
        }
        if (hc != 1) {
            return null;
        }

        int need = (n - 1) / 2;
        for (int i = 0; i < A ; i++) {
            if (words[head][i] >= 1) {

                int[] outdeg = wn.clone();
                outdeg[head]--;

                int[][] wd = new int[A][A];
                for (int j = 0; j < A ; j++) {
                    wd[j] = words[j].clone();
                }
                wd[head][i]--;

                Queue<Node> tails = new ArrayBlockingQueue<>(100000);
                Node root = new Node(null, (char)('a' + head), (char)('a' + i));
                tails.add(root);

                int hae = 0;
                while (tails.size() >= 1 && hae < need) {
                    Node current = tails.poll();
                    int tailID = current.tail - 'a';
                    int best = -1;
                    int bestA = -1;
                    int bestB = -1;
                    for (int a = 0; a < A ; a++) {
                        for (int b = a ; b < A ; b++) {
                            if (a == b) {
                                if (wd[tailID][a] <= 1) {
                                    continue;
                                }
                            } else {
                                if (wd[tailID][a] == 0 || wd[tailID][b] == 0) {
                                    continue;
                                }
                            }
                            int next = outdeg[a] + outdeg[b];
                            if (best < next) {
                                best = next;
                                bestA = a;
                                bestB = b;
                            }
                        }
                    }
                    if (best == -1) {
                        continue;
                    }
                    hae++;
                    wd[tailID][bestA]--;
                    wd[tailID][bestB]--;
                    outdeg[tailID] -= 2;
                    current.left = new Node(current, current.tail, (char)('a' + bestA));
                    current.right = new Node(current, current.tail, (char)('a' + bestB));
                    tails.add(current.left);
                    tails.add(current.right);
                }
                if (hae == need) {
                    return root;
                }
            }
        }
        return null;
    }

    static class Node {
        char head;
        char tail;
        int num;

        Node left;
        Node right;
        Node parent;

        Node(Node parent, char head, char tail) {
            this.num = 0;
            this.parent = parent;
            this.head = head;
            this.tail = tail;
        }


        void setNum(List<Integer>[][] ids, int[][] deg) {
            int a = head-'a';
            int b = tail-'a';
            num = ids[a][b].get(deg[a][b]);
            deg[a][b]++;
            if (left != null) {
                left.setNum(ids, deg);
            }
            if (right != null) {
                right.setNum(ids, deg);
            }
        }

        void computeAns(int[] ans) {
            ans[num] = (parent == null) ? 0 : parent.num+1;
            if (left != null) {
                left.computeAns(ans);
            }
            if (right != null) {
                right.computeAns(ans);
            }
        }

        void print() {
            debug(head,tail,num);
            if (left != null) {
                left.print();
            }
            if (right != null) {
                right.print();
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
