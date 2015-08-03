package codeforces.cr55.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/03.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] ord = new int[3*n];
        for (int i = 0; i < 3*n ; i++) {
            ord[i] = in.nextInt()-1;
        }
        int[][] teams = new int[3*n][];
        for (int i = 0; i < n ; i++) {
            int[] mem = {in.nextInt()-1, in.nextInt()-1, in.nextInt()-1};
            teams[mem[0]] = new int[]{mem[1], mem[2]};
            teams[mem[1]] = new int[]{mem[2], mem[0]};
            teams[mem[2]] = new int[]{mem[0], mem[1]};
        }
        int who = in.nextInt()-1;

        List<Integer> before = new ArrayList<>();
        List<Integer> after = new ArrayList<>();

        boolean flg = false;
        boolean[] used = new boolean[3*n];
        for (int i = 0; i < 3*n ; i++) {
            int lead = ord[i];
            if (used[lead]) {
                continue;
            }
            int mem1 = teams[lead][0];
            int mem2 = teams[lead][1];
            used[lead] = used[mem1] = used[mem2] = true;
            if (flg) {
                after.add(lead);
                after.add(mem1);
                after.add(mem2);
            } else {
                before.add(lead);
                before.add(mem1);
                before.add(mem2);
            }
            if (lead == who) {
                flg = true;
            }
        }
        Collections.sort(before);
        if (flg) {
            boolean mem1 = false;
            boolean mem2 = false;
            int idx = -1;
            for (int i = 0; i < before.size() ; i++) {
                if (before.get(i) == teams[who][0]) {
                    mem1 = true;
                }
                if (before.get(i) == teams[who][1]) {
                    mem2 = true;
                }
                if (mem1 && mem2 && idx == -1) {
                    idx = i;
                }
            }
            for (int i = before.size()-1 ; i > idx ; i--) {
                after.add(before.remove(i));;
            }
        }
        Collections.sort(after);

        StringBuilder line = new StringBuilder();
        for (int mi : before) {
            if (mi != who) {
                line.append(' ').append(mi+1);
            }
        }
        for (int mi : after) {
            if (mi != who) {
                line.append(' ').append(mi+1);
            }
        }
        out.println(line.substring(1));
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
