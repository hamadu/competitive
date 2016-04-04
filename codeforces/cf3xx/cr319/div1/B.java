package codeforces.cf3xx.cr319.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/09/11.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] to = new int[n];
        for (int i = 0; i < n ; i++) {
            to[i] = in.nextInt()-1;
        }

        List<int[]> edges = new ArrayList<>();
        List<Integer> ones = new ArrayList<>();
        List<List<Integer>> twos = new ArrayList<>();
        List<List<Integer>> threes = new ArrayList<>();
        List<List<Integer>> pairs = new ArrayList<>();

        boolean[] done = new boolean[n];
        int oneCount = 0;
        for (int i = 0; i < n ; i++) {
            if (done[i]) {
                continue;
            }
            int now = i;
            int size = 0;
            List<Integer> list = new ArrayList<>();
            while (!done[now]) {
                list.add(now);
                size++;
                done[now] = true;
                now = to[now];
            }
            if (size >= 3 && size % 2 == 1) {
                threes.add(list);
            } else if (size % 2 == 0) {
                if (size >= 4) {
                    for (int k = 0; k < list.size(); k += 2) {
                        List<Integer> tl = new ArrayList<>();
                        tl.add(list.get(k));
                        tl.add(list.get(k+1));
                        pairs.add(tl);
                    }
                } else {
                    twos.add(list);
                }
            } else {
                ones.add(i);
            }
        }
        boolean isOK = false;
        if (ones.size() >= 1) {
            isOK = true;
            int head = ones.get(0);
            for (int i = 0; i < n ; i++) {
                if (i != head) {
                    edges.add(new int[]{head+1, i+1});
                }
            }
            //
        } else if (threes.size() == 0 && twos.size() >= 1) {
            isOK = true;
            int tn = twos.size();
            List<Integer> headTwo = twos.get(0);
            int left = headTwo.get(0);
            int right= headTwo.get(1);
            edges.add(new int[]{left+1, right+1});
            for (int i = 1; i < tn  ; i++) {
                edges.add(new int[]{ left+1,  twos.get(i).get(0)+1 });
                edges.add(new int[]{ right+1, twos.get(i).get(1)+1 });
            }
            int pn = pairs.size();
            for (int i = 0 ; i < pn ; i++) {
                int a = pairs.get(i).get(0);
                int b = pairs.get(i).get(1);
                edges.add(new int[]{ left+1,  a+1 });
                edges.add(new int[]{ right+1, b+1 });
            }
        }
        if (isOK) {
            out.println("YES");
            for (int[] e : edges) {
                out.println(e[0] + " " + e[1]);
            }
        } else {
            out.println("NO");
        }

        out.flush();
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
