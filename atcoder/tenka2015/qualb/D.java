package atcoder.tenka2015.qualb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/01.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] map = new char[255];
        Arrays.fill(map, '@');
        map['O'] = map['D'] = '0';
        map['I'] = '1';
        map['Z'] = '2';
        map['E'] = '3';
        map['h'] = '4';
        map['s'] = '5';
        map['q'] = '6';
        map['L'] = '7';
        map['B'] = '8';
        map['G'] = '9';


        boolean moreThanZero = false;

        int d = in.nextInt();
        int n = in.nextInt();
        char[][] words = new char[n][];
        for (int i = 0; i < n ; i++) {
            words[i] = new StringBuilder(in.nextToken()).reverse().toString().toCharArray();
            for (int j = 0 ; j < words[i].length ; j++) {
                words[i][j] = map[words[i][j]];
                if (words[i][j] >= '1') {
                    moreThanZero = true;
                }
            }
        }
        if (!moreThanZero) {
            out.println(0);
        } else {
            String ans = solveOne(d, words);
            if (ans.charAt(0) == '0') {
                ans = "0." + ans.substring(1);
                while (ans.charAt(ans.length()-1) == '0') {
                    ans = ans.substring(0, ans.length()-1);
                }
            }
            out.println(ans);
        }
        out.flush();
    }

    private static String solveOne(int d, char[][] words) {
        Arrays.sort(words, (o1, o2) -> {
            String s1 = String.valueOf(o1);
            String s2 = String.valueOf(o2);
            return -((s1+s2).compareTo(s2+s1));
        });

        int n = words.length;
        String[] hv = new String[n];
        for (int i = 0 ; i < n ; i++) {
            hv[i] = String.valueOf(words[i]);
        }

        String[][] dp = new String[2][d+1];
        for (int i = 0 ; i <= 1 ; i++) {
            Arrays.fill(dp[i], "");
        }
        dp[0][0] = "";

        for (int i = 0 ; i < n ; i++) {
            int fi = i % 2;
            int ti = 1 - fi;
            Arrays.fill(dp[ti], "");
            for (int j = 0 ; j <= d ; j++) {
                if (dp[fi][j].length() != j) {
                    continue;
                }

                // use
                for (String to: new String[]{dp[fi][j], dp[fi][j]+hv[i]}) {
                    if (to.length() <= d) {
                        if (dp[ti][to.length()].compareTo(to) < 0) {
                            dp[ti][to.length()] = to;
                        }
                    }
                }
            }
        }

        boolean moreThanOne = false;
        for (int i = 0 ; i < n ; i++) {
            if (words[i][0] != '0') {
                moreThanOne = true;
            }
        }

        if (moreThanOne) {
            for (int i = d ; i >= 1 ; i--) {
                if (dp[n%2][i].length() == i) {
                    if (dp[n%2][i].charAt(0) == '0') {
                        continue;
                    }
                    return dp[n%2][i];
                }
            }
            return "-1";
        } else {
            String best = "";
            for (int i = d ; i >= 1 ; i--) {
                if (best.compareTo(dp[n%2][i]) < 0) {
                    best = dp[n%2][i];
                }
            }
            return best;
        }
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
