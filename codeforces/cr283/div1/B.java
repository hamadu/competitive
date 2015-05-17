package codeforces.cr283.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/17.
 */
public class B {

    private static final int INF = 10000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0 ; i < n ; i++) {
            a[i] = in.nextInt();
        }

        int[] imosP = new int[n+1];
        int[] imosG = new int[n+1];
        for (int i = 0 ; i < n ; i++) {
            imosP[i+1] = imosP[i];
            imosG[i+1] = imosG[i];
            if (a[i] == 1) {
                imosP[i+1]++;
            } else {
                imosG[i+1]++;
            }
        }

        int[] firstAppearP = new int[2*n+1];
        int[] firstAppearG = new int[2*n+1];
        Arrays.fill(firstAppearP, n+1);
        Arrays.fill(firstAppearG, n+1);
        for (int i = 0 ; i <= n ; i++) {
            int ip = imosP[i];
            if (firstAppearP[ip] == n+1) {
                firstAppearP[ip] = i;
            }
            int ig = imosG[i];
            if (firstAppearG[ig] == n+1) {
                firstAppearG[ig] = i;
            }
        }

        List<int[] > ans = new ArrayList<>();
        for (int t = 1 ; t <= n ; t++) {
            int petya = 0;
            int gena = 0;
            int head = 0;
            int lastWon = 0;
            while (head + t <= n) {
                int nowP = imosP[head];
                int nowG = imosG[head];

                int posP = firstAppearP[nowP+t];
                int posG = firstAppearG[nowG+t];
                if (posP < posG) {
                    petya++;
                    lastWon = 1;
                    head = posP;
                } else if (posG < posP) {
                    gena++;
                    lastWon = 2;
                    head = posG;
                } else {
                    petya = gena = lastWon = 0;
                    break;
                }
            }
            if ((petya > gena && lastWon == 1) || (petya < gena && lastWon == 2)) {
                if (head == n) {
                    ans.add(new int[]{Math.max(petya, gena), t});
                }
            }
        }

        Collections.sort(ans, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        out.println(ans.size());
        for (int[] row : ans) {
            out.println(row[0] + " " + row[1]);
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
