package atcoder.arc038;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class C {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] wan = new int[n];
        int[] mame = new int[n];
        for (int i = 1 ; i < n ; i++) {
            wan[i] = in.nextInt();
            mame[i] = in.nextInt();
        }

        int[] grundy = new int[n];
        int[] pos = new int[n+10];
        Arrays.fill(pos, -100);
        SegmentTree seg = new SegmentTree(pos);
        seg.update(0, 0);

        for (int i = 1 ; i < n ; i++) {
            grundy[i] = seg.findLessOrEqualIndexRight(0, i - wan[i] - 1);
            seg.update(grundy[i], i);
        }

        int xor = 0;
        for (int i = 0 ; i < n ; i++) {
            if (mame[i] % 2 == 1) {
                xor ^= grundy[i];
            }
        }
        out.println(xor == 0 ? "Second" : "First");
        out.flush();
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



    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



