package atcoder.tenka2015.qualb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/01.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long L = in.nextLong();
        out.println(solve(L) % MOD);
        out.flush();
    }

    private static long solve(long L) {
        if (L <= 300) {
            return doe(L);
        }
        long div2 = inv(2) % MOD;
        long ans = 0;
        // a<a+1<b
        {
            long lim = L / 3;
            long add = (((((lim-1) % MOD) * (lim % MOD)) % MOD) * div2) % MOD;
            long dec = 0;
            long dl = lim;
            while (true) {
                long a3 = dl*3+3;
                if (a3 > L) {
                    dec += dl-1;
                } else {
                    long max = (4 * dl-L+1);
                    long dc = (max / 4) + 1;
                    long first = max % 4;
                    long ada = ((((first + max) % MOD) * (dc % MOD) % MOD) * div2) % MOD;
                    dec += ada;
                    dec %= MOD;
                    break;
                }
                dl--;
            }
            ans += add;
            ans += (MOD-dec);
            ans %= MOD;
        }

        {
            long add = 0;
            long dec = 0;
            long dl = L / 2 + 1;
            add += ((((dl % MOD) * ((dl - 1) % MOD)) % MOD) * div2) % MOD;
            while (true) {
                long a3 = dl * 2 + 5;
                if (a3 > L) {
                    dec += dl-1;
                    dec %= MOD;
                } else {
                    long min = (L - a3) + 3;
                    long max = dl - min + 1;
                    long first = max % 3;
                    long dc = (max - first) / 3 + 1;
                    dec += ((((first + max) % MOD) * (dc % MOD) % MOD) * div2) % MOD;
                    dec %= MOD;
                    break;
                }
                dl--;
            }
            ans += add;
            ans += (MOD-dec);
            ans %= MOD;
        }

        // a<a+1<a+2
        long lm = L / 3 + 3;
        while (true) {
            if (lm * 3 + 3 <= L) {
                ans += (MOD - (lm - 1) % MOD);
                ans %= MOD;
                break;
            }
            lm--;
        }
        return ans;
    }

    static long doe(long l) {
        long ans = 0;
        for (int a = 1 ; a <= l ; a++) {
            for (int b = a+1; b <= l ; b++) {
                for (int c = b+1 ; c <= l ; c++) {
                    if (Math.abs(b-a) == 1 || Math.abs(c-b) == 1) {
                    } else {
                        continue;
                    }
                    if (c < a+b && a+b+c <= l) {
                        ans++;
                    } else {
                        break;
                    }
                }
            }
        }
        //debug(l,downer,upper,ans);
        return ans;
    }


    static final long MOD = 1000000007;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
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
