package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/13.
 */
public class P1197 {
    static final String IMPOSSIBLE = "~";

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        Dice.buildDiceMap();

        while (true) {
            int[] f = new int[6];
            int sum = 0;
            for (int i = 0; i < 6 ; i++) {
                f[i] = in.nextInt();
                sum += f[i];
            }
            Arrays.sort(f);
            if (sum == 0) {
                break;
            }
            int p = in.nextInt();
            int q = in.nextInt();
            String answer = solve(f);
            if (answer.equals(IMPOSSIBLE)) {
                out.println("impossible");
            } else {
                out.println(answer.substring(p-1, q));
            }
        }

        out.flush();
    }

    private static String solve(int[] dices) {
        String ans = IMPOSSIBLE;
        do {
            String ret = solveSub(dices.clone());
            if (ret.compareTo(ans) < 0) {
                ans = ret;
            }
        } while (next_permutation(dices));
        return ans;
    }

    private static String solveSub(int[] eye) {
        int sum = 0;
        for (int i = 0 ; i < 6 ; i++) {
            sum += eye[i];
        }

        int[] tur = new int[]{1, 2, 3};
        char[] dir = new char[] {'E', 'N', 'S', 'W'};
        char[] ret = new char[sum];
        for (int k = 0 ; k < sum ; k++) {
            boolean found = false;
            for (int d = 0 ; d < 4 ; d++) {
                char dc = dir[d];
                int[] toTur = Dice.diceMap[tur[0]][tur[1]][tur[2]][d];
                int floor = toTur[0] - 1;
                if (eye[floor] == 0) {
                    continue;
                }
                eye[floor] -= 1;
                if (canMake(toTur, eye)) {
                    found = true;
                    ret[k] = dc;
                    tur = toTur;
                    break;
                }
                eye[floor] += 1;
            }
            if (!found) {
                return IMPOSSIBLE;
            }
        }
        return String.valueOf(ret);
    }

    private static boolean canMake(int[] tur, int[] eye) {
        int up = tur[0] - 1;
        int dw = 7 - tur[0] - 1;
        int ri = tur[2] - 1;
        int le = 7 - tur[2] - 1;
        int fr = tur[1] - 1;
        int ba = 7 - tur[1] - 1;
        int sum = 0;
        for (int i = 0; i < 6 ; i++) {
            sum += eye[i];
        }
        if (eye[up] + eye[dw] > sum - eye[up] - eye[dw]) {
            return false;
        }
        if (eye[ri] + eye[le] - 1 > sum - eye[ri] - eye[le]) {
            return false;
        }
        if (eye[fr] + eye[ba] - 1 > sum - eye[fr] - eye[ba]) {
            return false;
        }
        return true;
    }

    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
    }

    static final Direction[] _directions = { Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN };

    static enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1);

        int dx;
        int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    static class Dice {
        final static int MAXCODE = 777;

        static int[][][][][] diceMap = new int[7][7][7][4][3];

        static void buildDiceMap() {
            for (int i = 1 ; i <= 6 ; i++) {
                for (int j = 1 ; j <= 6 ; j++) {
                    for (int k = 1 ; k <= 6 ; k++) {
                        if (i == j || j == k || i == k) {
                            continue;
                        }
                        Dice dice = new Dice(i, j, k);
                        Dice[] dices = new Dice[]{dice.right(), dice.up(), dice.down(), dice.left()};
                        for (int d = 0 ; d < 4 ; d++) {
                            Dice di = dices[d];
                            diceMap[i][j][k][d] = new int[]{di.top, di.up, di.right};
                        }
                    }
                }
            }
        }

        int top;
        int up;
        int right;

        public Dice(int t, int u, int r) {
            top = t;
            up = u;
            right = r;
        }

        public String toString() {
            return String.format("%d/%d/%d", top, up, right);
        }

        public Dice move(Direction dir) {
            switch (dir) {
                case LEFT:
                    return left();
                case RIGHT:
                    return right();
                case UP:
                    return up();
                case DOWN:
                    return down();
            }
            throw new RuntimeException("invalid direction : " + dir);
        }

        private Dice left() {
            return new Dice(right, up, 7-top);
        }

        private Dice right() {
            return new Dice(7-right, up, top);
        }

        private Dice up() {
            return new Dice(7-up, top, right);
        }

        private Dice down() {
            return new Dice(up, 7-top, right);
        }

        public int encode() {
            return (top<<6)+(up<<3)+right;
        }

        public static Dice decode(int code) {
            int top = (code>>6)&7;
            int up = (code>>3)&7;
            int right = code&7;
            return new Dice(top, up, right);
        }

        public static Dice initialDice() {
            return new Dice(1, 2, 3);
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
