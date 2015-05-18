package paiza.poh5;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/18.
 */
public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] sum = new long[7];
        for (int i = 0 ; i < n ; i++) {
            sum[i%7] += in.nextInt();
        }

        for (long s : sum) {
            out.println(s);
        }
        out.flush();
    }
}
