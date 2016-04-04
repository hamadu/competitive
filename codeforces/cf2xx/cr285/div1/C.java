package codeforces.cf2xx.cr285.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/19.
 */
public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        int[] deg = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt()-1;
            deg[a[i]]++;
        }
        int odd = 0;
        int oddNum = 0;
        for (int i = 0 ; i < n ; i++) {
            odd += deg[i] % 2;
            if (deg[i] % 2 == 1) {
                oddNum = i;
            }
        }
        if (odd > n % 2) {
            out.println(0);
            out.flush();
            return;
        }

        boolean cng = true;
        for (int i = 0 ; i < n ; i++) {
            if (a[i] != a[n-1-i]) {
                cng = false;
                break;
            }
        }
        if (cng) {
            out.println(1L * n * (n+1) / 2);
            out.flush();
            return;
        }

        int head = 0;
        while (a[head] == a[n-1-head]) {
            head++;
        }
        int tail = (n-1)/2;
        while (a[tail] == a[n-1-tail]) {
            if (tail == n-1-tail && a[tail] != oddNum) {
                break;
            }
            tail--;
        }

        int[] cnt = new int[n];
        for (int i = 0 ; i < n/2 ; i++) {
            if (i != n-1-i) {
                cnt[a[i]]++;
            }
            cnt[a[n-1-i]]++;
        }
        int[] need = new int[n];
        int needSum = 0;
        for (int i = 0; i < n ; i++) {
            need[i] = (cnt[i] + 1) / 2;
            needSum += need[i];
        }

        Arrays.fill(cnt, 0);
        int right = -1;
        for (int r = head ; r < n ; r++) {
            if (r <= tail || n-1-tail <= r || true) {
                cnt[a[r]]++;
                if (cnt[a[r]] - 1 < need[a[r]]) {
                    needSum--;
                }
            }
            if (needSum == 0) {
                right = r;
                break;
            }
        }

        Arrays.fill(cnt, 0);
        needSum = 0;
        for (int i = 0; i < n ; i++) {
            needSum += need[i];
        }

        int left = -1;
        for (int r = n-1-head ; r >= 0 ; r--) {
            if (r <= tail || n-1-tail <= r || true) {
                cnt[a[r]]++;
                if (cnt[a[r]] - 1 < need[a[r]]) {
                    needSum--;
                }
            }
            if (needSum == 0) {
                left = r;
                break;
            }
        }

        long ans = (head+1L)*(n-right)+(head+1L)*(left+1L);
        ans -= (head+1L)*(head+1L);

        out.println(ans);
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
