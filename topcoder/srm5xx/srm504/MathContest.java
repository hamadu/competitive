package topcoder.srm5xx.srm504;

/**
 * Created by hama_du on 15/08/04.
 */
public class MathContest {
    public int countBlack(String ballSequence, int repetitions) {
        StringBuilder seq = new StringBuilder();
        for (int i = 0; i < repetitions ; i++) {
            seq.append(ballSequence);
        }
        char[] a = seq.toString().toCharArray();
        int n = a.length;
        int head = 0;
        int tail = n-1;
        boolean rev = false;
        boolean inv = false;

        int count = 0;
        while (head <= tail) {
            char c = rev ? a[tail--] : a[head++];
            boolean isBlack = c == 'B';
            if (inv) {
                isBlack = !isBlack;
            }
            if (isBlack) {
                count++;
                inv = !inv;
            } else {
                rev = !rev;
            }
        }
        return count;
    }
}
