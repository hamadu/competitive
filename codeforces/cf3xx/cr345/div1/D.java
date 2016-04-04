package codeforces.cf3xx.cr345.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/09.
 */
public class D {
    private static final int MASK = (1<<20)-1;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[] h = new int[n];
        for (int i = 0; i < n ; i++) {
            h[i] = in.nextInt();
        }

        int[] qpos = new int[m];
        int[] qnum = new int[m];
        int[] qidx = new int[m];
        for (int i = 0; i < m ; i++) {
            qpos[i] = in.nextInt()-1;
            qnum[i] = in.nextInt();
        }
        int nm = n + m;
        int[] cnum = new int[nm];
        for (int i = 0 ; i < n ; i++) {
            cnum[i] = h[i];
        }
        for (int i = 0 ; i < m ; i++) {
            cnum[n+i] = qnum[i];
        }
        Arrays.sort(cnum);
        Map<Integer,Integer> cmap = new HashMap<>();
        int ctr = 0;
        for (int i = 0 ; i < nm ; ) {
            int j = i;
            while (j < nm && cnum[i] == cnum[j]) {
                j++;
            }
            cmap.put(cnum[i], ctr);
            ctr++;
            i = j;
        }

//
//
//        Set<Integer> nums = new HashSet<>();
//        for (int i = 0 ; i < n ; i++) {
//            nums.add(h[i]);
//        }
//        for (int i = 0 ; i < m ; i++) {
//            nums.add(qnum[i]);
//        }
//        Map<Integer,Integer> cmap = compress(nums);
        for (int i = 0 ; i < n ; i++) {
            h[i] = cmap.get(h[i])+1;
        }
        for (int i = 0; i < m ; i++) {
            qnum[i] = cmap.get(qnum[i])+1;
        }

        long[] queries = new long[m];
        for (int i = 0; i < m ; i++) {
            queries[i] = (((long)qpos[i])<<40L)|(((long)qnum[i])<<20L)|i;
        }
        Arrays.sort(queries);
        for (int i = 0 ; i < m ; i++)  {
            qpos[i] = (int)(queries[i]>>40L);
            qnum[i] = (int)(queries[i]>>20L)&(MASK);
            qidx[i] = (int)(queries[i]&MASK);
        }

        int[] left = new int[n+1];
        int[] right = new int[n+1];
        int[] part = new int[m];

        int qi = 0;
        int ns = cmap.size()+5;
        SegmentTree leftSeg = new SegmentTree(ns);
        leftSeg.update(0, 0);
        for (int i = 1 ; i <= n ; i++) {
            int num = h[i-1];
            left[i] = leftSeg.max(0, num) + 1;
            while (qi < m && qpos[qi] < i-1) {
                qi++;
            }
            while (qi < m && qpos[qi] == i-1) {
                int newnum = qnum[qi];
                part[qidx[qi]] += leftSeg.max(0, newnum);
                qi++;
            }
            leftSeg.update(num, left[i]);
        }

        SegmentTree rightSeg = new SegmentTree(ns);
        rightSeg.update(ns-1, 0);
        qi = m-1;
        for (int i = n-1 ; i >= 0 ; i--) {
            int num = h[i];
            right[i] = rightSeg.max(num+1, ns) + 1;
            while (qi >= 0 && qpos[qi] > i) {
                qi--;
            }
            while (qi >= 0 && qpos[qi] == i) {
                int newnum = qnum[qi];
                part[qidx[qi]] += rightSeg.max(newnum+1, ns);
                qi--;
            }
            rightSeg.update(num, right[i]);
        }

        int max = 0;
        for (int i = 0 ; i < n ; i++) {
            max = Math.max(max, left[i+1]);
        }

        boolean[] notNeeded = new boolean[n];
        int ct = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            if (left[i+1] != max-ct) {
                notNeeded[i] = true;
            } else {
                ct++;
            }
        }
        ct = 0;
        for (int i = 0; i < n ; i++) {
            if (right[i] != max-ct) {
                notNeeded[i] = true;
            } else {
                ct++;
            }
        }

        for (int i = 0 ; i < m ; i++) {
            int pos = qpos[i];
            part[qidx[i]] = Math.max(part[qidx[i]]+1, notNeeded[pos] ? max : max-1);
        }

        for (int i = 0; i < m ; i++) {
            out.println(part[i]);
        }
        out.flush();
    }


    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int len) {
            N = Integer.highestOneBit(len-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MIN_VALUE);
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


    static Map<Integer, Integer> compress(Set<Integer> set) {
        List<Integer> nadd = new ArrayList<Integer>(set);
        Collections.shuffle(nadd);
        Collections.sort(nadd);

        Map<Integer, Integer> comp = new HashMap<Integer, Integer>();
        for (int na : nadd) {
            comp.put(na, comp.size());
        }
        return comp;
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
