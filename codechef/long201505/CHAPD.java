package codechef.long201505;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/09.
 */
public class CHAPD {
    static final int[] lowPrimes = primes(100010);

    public static int[] primes(int max) {
        boolean[] isp = new boolean[max];
        Arrays.fill(isp, true);

        int primeCount = 0;
        for (int i = 2 ; i < max ; i++) {
            if (isp[i]) {
                primeCount++;
                for (int ii = i*2 ; ii < max ; ii += i) {
                    isp[ii] = false;
                }
            }
        }

        int pi = 0;
        int[] ret = new int[primeCount];
        for (int i = 2 ; i < max ; i++) {
            if (isp[i]) {
                ret[pi++] = i;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (--T >= 0) {
            long a = in.nextLong();
            long b = in.nextLong();
            out.println(solve(a, b) ? "Yes" : "No");
        }
        out.flush();
    }

    private static boolean solve(long a, long b) {
        for (int lp : lowPrimes) {
            if (b < lp) {
                break;
            }
            if (b % lp == 0) {
                while (b % lp == 0) {
                    b /= lp;
                }
                if (a % lp != 0) {
                    return false;
                }
            }
        }
        long lastP = b;
        long b2 = (long)Math.sqrt(b);
        if (b2 * b2 == b) {
            lastP = b2;
        }
        return (a % lastP == 0);
    }
}
