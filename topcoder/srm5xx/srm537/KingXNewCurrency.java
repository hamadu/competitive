package topcoder.srm5xx.srm537;

/**
 * Created by hama_du on 15/08/24.
 */
public class KingXNewCurrency {
    public int howMany(int A, int B, int X) {
        if (gcd(A, B) % X == 0) {
            return -1;
        }
        int k = 0;
        for (int i = 1; i <= A+B ; i++) {
            if (i == X) {
                continue;
            }
            if (isOK(A, X, i) && isOK(B, X, i)) {
                k++;
            }
        }
        return k;
    }

    private boolean isOK(int a, int x, int i) {
        for (int u = 0; u <= a; u += x) {
            if ((u - a) % i == 0) {
                return true;
            }
        }
        return false;
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }
}
