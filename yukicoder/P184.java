package yukicoder;

import java.util.Scanner;

/**
 * Created by dhamada on 15/05/12.
 */
public class P184 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        long[] a = new long[n];
        for (int i = 0 ; i < n ; i++) {
            a[i] = in.nextLong();
        }

        int ok = 0;
        for (int i = 0 ; i < n ; i++) {
            if (a[i] == 0) {
                continue;
            }
            ok++;
            int found = 0;
            while ((a[i] & (1L<<found)) == 0) {
                found++;
            }
            for (int j = i+1 ; j < n ; j++) {
                if ((a[j] & (1L<<found)) != 0) {
                    a[j] ^= a[i];
                }
            }
        }
        System.out.println(1L<<ok);
    }
}
