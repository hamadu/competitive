package codeforces.cf2xx.cr297.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        long S = in.nextLong();

        long[] l = new long[n/2];
        long[] r = new long[n-n/2];
        for (int i = 0 ; i < l.length ; i++) {
            l[i] = in.nextLong();
        }
        for (int i = 0 ; i < r.length ; i++) {
            r[i] = in.nextLong();
        }

        Map<Long,Integer>[] left = build(25, l);
        Map<Long,Integer>[] right = build(25, r);

        long ptn = 0;
        for (int lk = 0 ; lk <= k ; lk++) {
            for (int rk = 0 ; lk + rk <= k ; rk++) {
                for (long ls : left[lk].keySet()) {
                    long lp = left[lk].get(ls);
                    long rs = S - ls;
                    if (right[rk].containsKey(rs)) {
                        long rp = right[rk].get(rs);
                        ptn += lp * rp;
                    }
                }
            }
        }
        out.println(ptn);
        out.flush();
    }

    private static Map<Long, Integer>[] build(int K, long[] l) {
        int n = l.length;
        long[] fact = new long[n];
        for (int i = 0; i < n; i++) {
            if (l[i] < 20) {
                fact[i] = 1;
                for (int x = 1 ; x <= l[i] ; x++) {
                    fact[i] *= x;
                }
            } else {
                fact[i] = -1;
            }
        }


        Map<Long,Integer>[] mp = new Map[K+1];
        for (int i = 0; i <= K ; i++) {
            mp[i] = new HashMap<>();
        }
        for (int p = 0 ; p < (1<<n) ; p++) {
            for (int sp = p ; ; sp = (sp - 1) & p) {
                long sum = 0;
                for (int k = 0; k < n; k++) {
                    if ((p & (1 << k)) >= 1) {
                        if ((sp & (1 << k)) >= 1) {
                            if (fact[k] == -1) {
                                sum = -1;
                                break;
                            } else {
                                sum += fact[k];
                            }
                        } else {
                            sum += l[k];
                        }
                    }
                }
                if (sum >= 0) {
                    int bc = Integer.bitCount(sp);
                    if (!mp[bc].containsKey(sum)) {
                        mp[bc].put(sum, 0);
                    }
                    mp[bc].put(sum, mp[bc].get(sum) + 1);
                }
                if (sp == 0) {
                    break;
                }
            }
        }
        return mp;
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



