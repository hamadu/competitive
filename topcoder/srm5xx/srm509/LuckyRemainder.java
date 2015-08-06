package topcoder.srm5xx.srm509;

/**
 * Created by hama_du on 15/08/05.
 */
public class LuckyRemainder {
    public int getLuckyRemainder(String X) {
        long l = X.length();
        long cnt = 1L<<(l-1);
        cnt %= 9;
        long total = 0;
        for (char c : X.toCharArray()) {
            int d = c-'0';
            total += d;
        }
        total %= 9;
        return (int)(total * cnt % 9);
    }
}
