package atcoder.other2016.mujin2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/02/27.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[] s = in.nextToken().toCharArray();
        int q = in.nextInt();

        int[] left = new int[n+1];
        int[] right = new int[n+1];
        int[] question = new int[n+1];
        for (int i = 0 ; i < n ; i++) {
            left[i+1] = left[i];
            right[i+1] = right[i];
            question[i+1] = question[i];
            if (s[i] == '(') {
                left[i+1]++;
            } else if (s[i] == ')') {
                right[i+1]++;
            } else {
                question[i+1]++;
            }
        }

        int[] leftImos = makeit(s, 1);
        int[] rightImos = makeit(s, -1);

        debug(leftImos);
        debug(rightImos);


        SegmentTree leftSeg = new SegmentTree(leftImos);
        SegmentTree rightSeg = new SegmentTree(rightImos);

        while (--q >= 0) {
            int l = in.nextInt()-1;
            int r = in.nextInt();
            if (r - l % 2 == 1) {
                out.println("No");
                continue;
            }
            int nl = left[r] - left[l];
            int nr = right[r] - right[l];
            int nq = question[r] - question[l];
            int diff = Math.abs(nl - nr);
            if (nq < diff || (nq - diff) % 2 == 1) {
                out.println("No");
                continue;
            }
            int needToFlip = (nl + nq - nr) / 2;
            int wantPlus = nq - needToFlip;
            if (wantPlus < 0) {
                out.println("No");
                continue;
            }


            int min = l;
            int max = r;
            while (max - min > 1) {
                int med = (max + min) / 2;
                if (question[med] - question[l] > wantPlus) {
                    max = med;
                } else {
                    min = med;
                }
            }

            // [l, min) [min, r)
            long leftPart = leftSeg.min(l, min) - ((l >= 1) ? leftImos[l-1] : 0);
            long rightPart = rightSeg.min(min, r) - ((min >= 1) ? rightImos[min-1] : 0);
            long leftSum = (left[min] - left[l]) + (question[min] - question[l]) - (right[min] - right[l]);

//            debug(q, wantPlus, needToFlip);
//            debug(q, l, min, r, leftSum, leftPart, rightPart);

            if (leftPart >= 0 && rightPart + leftSum >= 0) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
        out.flush();
    }

    private static int[] makeit(char[] s, int q) {
        int[] ret = new int[s.length+1];
        for (int i = 0; i < s.length ; i++) {
            if (s[i] == '?') {
                ret[i] = q;
            } else {
                ret[i] = s[i] == '(' ? 1 : -1;
            }
        }
        int[] imos = new int[ret.length+1];
        imos[0] = ret[0];
        for (int i = 1 ; i < ret.length ; i++) {
            imos[i] += imos[i-1] + ret[i];
        }
        return imos;
    }

    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MAX_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void update(int idx, int value) {
            seg[M+idx] = value;
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                seg[i] = compute(i);
                if (i == 0) {
                    break;
                }
            }
        }

        public int compute(int i) {
            return Math.min(seg[i*2+1], seg[i*2+2]);
        }

        public int min(int l, int r) {
            return min(l, r, 0, 0, M+1);
        }

        public int min(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MAX_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MAX_VALUE;
            ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
            ret = Math.min(ret, min(l, r, idx*2+2, med, to));
            return ret;
        }

        /**
         * L番目以降で、最初にV以下になるインデックスを見つける。O(logn)
         * 見つからない時は -1
         *
         * @param l
         * @param v
         * @return
         */
        public int findLessOrEqualIndexRight(int l, int v) {
            int cur = M + l;
            while (true) {
                if (seg[cur] <= v) {
                    if (cur < M) {
                        cur = cur*2+1;
                    } else {
                        return cur - M;
                    }
                } else {
                    cur += 1;
                    if ((cur & (cur+1)) == 0) {
                        return -1;
                    }
                    if ((cur&1) == 1) {
                        cur >>>= 1;
                    }
                }
            }
        }

        /**
         * L番目以前、最初にV以下になるインデックスを見つける。O(logn)
         * 見つからない時は -1
         *
         * @param l
         * @param v
         * @return
         */
        public int findLessOrEqualIndexLeft(int l, int v) {
            int cur = M + l;
            while (true) {
                if (seg[cur] <= v) {
                    if (cur < M) {
                        cur = cur*2+2;
                    } else {
                        return cur - M;
                    }
                } else {
                    if ((cur & (cur+1)) == 0) {
                        return -1;
                    }
                    cur--;
                    if ((cur&1)==0) {
                        cur >>>= 1;
                    }
                }
            }
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
