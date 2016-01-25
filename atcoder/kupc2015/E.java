package atcoder.kupc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/10/24.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        while (--n >= 0) {
            int h = in.nextInt();
            int w = in.nextInt();
            out.println(solve(h, w));
        }
        out.flush();
    }

    private static double solve(double H, double W, double x) {
        double y = Math.sqrt(4*H*H*W*W+4*H*H*x*x-4*H*W*W*x-4*H*x*x*x+W*W*W*W-2*W*W*x*x+x*x*x*x) / (2 * W);
        double z = (2*H*x+W*W-x*x) / 2*W;
        if (y > W || z > W) {
            return 0;
        }
        double a = x*x+y*y;
        double b = (H-x)*(H-x)+z*z;
        double c = H*H+(W-z)*(W-z);
        return Math.min(a, Math.min(b, c));
    }

    private static double solve(double H, double W) {
        if (W < H) {
            return solve(W, H);
        }
        return Math.max(try0(H, W), try1(H, W));
    }

    private static double try0(double H, double W) {
        double min = 0;
        double max = H;
        double best = Math.min(W*W, H*H+W*W/4);
        best = Math.max(best, Math.min(H*H, H*H/4+W*W));

//        for (int cur = 0 ; cur < 50 ; cur++) {
//            double med1 = (min * 2 + max) / 3;
//            double med2 = (min + max * 2) / 3;
//            double b1 = solve(H, W, med1);
//            double b2 = solve(H, W, med2);
//            if (b1 < b2) {
//                min = med1;
//            } else {
//                max = med2;
//            }
//            best = Math.max(best, Math.max(b1, b2));
//        }
        return Math.sqrt(best);
    }

    private static double try1(double H, double W) {
        double x = Math.sqrt(3)*H-W;
        double y = Math.sqrt(3)*W-H;
        if (x < 0 || y < 0 || x > W || y > H) {
            return 0;
        }
        return Math.sqrt(x*x+y*y);
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
