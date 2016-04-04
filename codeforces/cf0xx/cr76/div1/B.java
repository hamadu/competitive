package codeforces.cf0xx.cr76.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/09/08.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int w = in.nextInt();
        int m = in.nextInt();
        int total = n * w * m;
        int perCup = total / m;
        int perBottle = total / n;
        List<int[]>[] bottleConf = doit(perCup, perBottle, n, m);
        if (bottleConf == null) {
            out.println("NO");
        } else {
            List<int[]>[] cupConf = new List[m];
            for (int i = 0; i < m ; i++) {
                cupConf[i] = new ArrayList<>();
            }
            for (int i = 0; i < n ; i++) {
                for (int[] conf : bottleConf[i]) {
                    cupConf[conf[0]].add(new int[]{i+1, conf[1]});
                }
            }

            out.println("YES");
            for (int i = 0; i < m ; i++) {
                StringBuilder line = new StringBuilder();
                for (int[] conf : cupConf[i]) {
                    line.append(' ').append(conf[0]).append(' ').append(String.format("%.8f", conf[1] * 1.0d / m));
                }
                out.println(line.substring(1));
            }
        }
        out.flush();
    }

    private static List<int[]>[] doit(int perCup, int perBottle, int n, int m) {
        if (perCup * 2 < perBottle) {
            return null;
        }
        List<int[]>[] conf = new List[n];
        for (int i = 0; i < n ; i++) {
            conf[i] = new ArrayList<>();
        }
        int[] total = new int[m];

        int head = 0;
        for (int i = 0; i < n ; i++) {
            if (total[head] + perBottle < perCup) {
                conf[i].add(new int[]{head, perBottle});
                total[head] += perBottle;
            } else {
                conf[i].add(new int[]{head, perCup - total[head]});
                int left = perBottle - (perCup - total[head]);
                total[head] = perCup;
                if (left > perCup) {
                    return null;
                }
                head++;
                if (head < m && left >= 1) {
                    conf[i].add(new int[]{head, left});
                    total[head] += left;
                    if (total[head] == perCup) {
                        head++;
                    }
                }
            }
        }
        return conf;
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
