package codeforces.cr72.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/30.
 */
public class B {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long K = in.nextLong();

        int[] m = new int[n];
        int[][] a = new int[n][2];
        long total = 0;
        for (int i = 0; i < n ; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = i;
            total += a[i][0];

            m[i] = a[i][0];
        }
        if (total < K) {
            out.println("-1");
            out.flush();
            return;
        }

        Arrays.sort(a, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        long cur = 0;
        for (int i = 0 ; i < n ;) {
            long left = n - i;
            int j = i;
            while (j < n && a[i][0] == a[j][0]) {
                j++;
            }
            long doit = left * (a[i][0] - cur);
            if (doit >= K) {
                int doe = (int)(K % left);
                int dec = (int)(K / left + cur);


                List<Integer> notFinished = new ArrayList<>();
                for (int k = 0 ; k < n ; k++) {
                    if (m[k] > dec) {
                        m[k] -= dec;
                        notFinished.add(k);
                    }
                }

                List<Integer> answer = new ArrayList<>();
                for (int k = doe ; k < notFinished.size() ; k++) {
                    answer.add(notFinished.get(k));
                }
                for (int k = 0 ; k < doe ; k++) {
                    if (m[notFinished.get(k)] >= 2) {
                        answer.add(notFinished.get(k));
                    }
                }

                StringBuilder line = new StringBuilder();
                for (int ai : answer) {
                    line.append(' ').append(ai+1);
                }
                if (line.length() >= 1) {
                    out.println(line.substring(1));
                }
                break;
            } else {
                K -= doit;
            }
            cur = a[i][0];
            i = j;
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
