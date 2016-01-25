package topcoder.srm5xx.srm575;

/**
 * Created by hama_du on 15/09/13.
 */
public class TheNumberGameDivOne {
    public final String WIN = "John";
    public final String LOSE = "Brus";

    public String find(long n) {
        if (n % 2 == 1) {
            return LOSE;
        }
        long x = 0;
        while (n % 2 == 0) {
            x++;
            n /= 2;
        }
        if (n == 1 && x % 2 == 1) {
            return LOSE;
        }
        return WIN;
    }
}
