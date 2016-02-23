package facebook.fhc2016.round3;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/31.
 */
public class Umbrella {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int s = in.nextInt();
            int[][] rain = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    rain[i][j] = in.nextInt();
                }
            }
            out.println(String.format("Case #%d: %d", c, solve(s, rain)));
        }
        out.flush();
    }

    private static int solve(long s, int[][] rain) {
        int left = solveSub(s, rain);
        for (int i = 0 ; i < rain.length ; i++) {
            rain[i][0] = 1000000000 - rain[i][0];
        }
        int right = solveSub(s, rain);
        return Math.max(left, right);
    }

    private static Map<Long,Integer> compress(Set<Long> set) {
        List<Long> li = new ArrayList<>();
        for (Long s : set) {
            li.add(s);
        }
        Collections.sort(li);
        Map<Long,Integer> m = new HashMap<>();
        for (int i = 0 ; i < li.size() ; i++) {
            m.put(li.get(i), i);
        }
        return m;
    }

    private static int solveSub(long s, int[][] rain) {
        int n = rain.length;

        Arrays.sort(rain, (r1, r2) -> (r1[0] != r2[0]) ? r1[0] - r2[0] : r1[1] - r2[1]);
        Set<Long> beforeSet = new HashSet<>();
        Set<Long> afterSet = new HashSet<>();
        for (int i = 0 ; i < n ; i++) {
            beforeSet.add(- rain[i][0] * 1L + s * rain[i][1]);
            afterSet.add(rain[i][0] * 1L + s * rain[i][1]);
        }
        Map<Long,Integer> beforeIndex = compress(beforeSet);
        Map<Long,Integer> afterIndex = compress(afterSet);

        int bn = beforeIndex.size();
        int an = afterIndex.size();
        SegmentTree goSeg = new SegmentTree(new int[bn+10]);
        SegmentTree revSeg = new SegmentTree(new int[an+10]);


        int[] tmp = new int[n+10];
        Arrays.fill(tmp, -n*10);
        SegmentTree tmpSeg = new SegmentTree(tmp);

        int[][] pos = new int[n][2];
        for (int i = 0; i < n ; i++) {
            long b = - rain[i][0] * 1L + s * rain[i][1];
            long a = rain[i][0] * 1L + s * rain[i][1];
            pos[i][0] = beforeIndex.get(b);
            pos[i][1] = afterIndex.get(a);
        }

        int max = 0;
        for (int i = 0 ; i < n ; ) {
            int fi = i;
            int ti = fi;
            while (ti < n && rain[fi][0] == rain[ti][0]) {
                ti++;
            }
            int minu = 0;
            for (int f = ti-1 ; f >= fi ; f--) {
                int amax = revSeg.max(pos[f][1], an+5);
                tmpSeg.update(f, amax-minu);
                minu++;
            }
            for (int f = fi ; f < ti ; f++) {
                int score = ti - f;
                int bmax = goSeg.max(0, pos[f][0]+1);
                int amax = tmpSeg.max(f, ti);
                max = Math.max(max, score + bmax + amax);
            }

            for (int f = fi ; f < ti ; f++) {
                goSeg.add(pos[f][0], 1);
                revSeg.add(pos[f][1], 1);
            }
            i = ti;
        }
        return max;
    }

    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MIN_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void add(int idx, int value) {
            seg[M+idx] += value;
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                seg[i] = compute(i);
                if (i == 0) {
                    break;
                }
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
            return Math.max(seg[i*2+1], seg[i*2+2]);
        }

        public int max(int l, int r) {
            return max(l, r, 0, 0, M+1);
        }

        public int max(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MIN_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }
            int med = (fr+to) / 2;
            int ret = Integer.MIN_VALUE;
            ret = Math.max(ret, max(l, r, idx*2+1, fr, med));
            ret = Math.max(ret, max(l, r, idx*2+2, med, to));
            return ret;
        }
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


}
