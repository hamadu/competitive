package codeforces.cr296.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class A {

    static class Edge implements Comparable<Edge> {
        int length;

        public Edge(int l) {
            this.length = l;
        }

        @Override
        public boolean equals(Object e) {
            return ((Edge)e).length - length == 0;
        }

        @Override
        public int compareTo(Edge o) {
            return o.length - length;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int w = in.nextInt();
        int h = in.nextInt();
        int n = in.nextInt();

        TreeSet<Integer> posV = new TreeSet<>();
        TreeSet<Integer> posH = new TreeSet<>();
        PriorityQueue<Edge> heapV = new PriorityQueue<>();
        PriorityQueue<Edge> heapH = new PriorityQueue<>();
        posV.add(0);
        posV.add(w);
        posH.add(0);
        posH.add(h);
        heapV.add(new Edge(w));
        heapH.add(new Edge(h));
        long maxV = w;
        long maxH = h;
        for (int i = 0; i < n; i++) {
            char c = in.nextChar();
            int t = in.nextInt();
            if (c == 'V') {
                maxV = cut(posV, heapV, t);
            } else {
                maxH = cut(posH, heapH, t);
            }
            out.println(maxV * maxH);
        }
        out.flush();
    }


    public static int cut(TreeSet<Integer> pos, PriorityQueue<Edge> heap, int at) {
        int lo = pos.lower(at);
        int hi = pos.higher(at);
        pos.add(at);
        heap.remove(new Edge(hi - lo));
        heap.add(new Edge(at - lo));
        heap.add(new Edge(hi - at));
        return heap.peek().length;
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



