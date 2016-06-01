package topcoder.srm6xx.srm691.div1;

/**
 * Created by hama_du on 2016/05/30.
 */
public class Sunnygraphs {
    public long count(int[] a) {
        int n = a.length;
        long p0 = search(0, a);
        long p1 = search(1, a);
        long p01 = p0 & p1;

        int c0 = Long.bitCount(p0);
        int c1 = Long.bitCount(p1);
        int c01 = Long.bitCount(p01);
        int c2 = Long.bitCount((1L<<n)-1-(p0|p1));

        if (c01 == 0) {
            return ((1L<<c0)-1)*((1L<<c1)-1)*(1L<<c2);
        }
        long all = 1L<<n;
        long wrong01 = ((1L<<(c0-c01))-1)*(1L<<c2);
        long wrong10 = ((1L<<(c1-c01))-1)*(1L<<c2);

        return all - wrong01 - wrong10;
    }

    private long search(int head, int[] a) {
        long ret = 1L<<head;
        for (int i = 0; i < a.length ; i++) {
            head = a[head];
            ret |= 1L<<head;
        }
        return ret;
    }
}
