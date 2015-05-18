package paiza.poh5;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/18.
 */
public class Q4A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int m = in.nextInt();
        int n = in.nextInt();

        int[][] before = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                before[i][j] = in.nextInt();
            }
        }

        int[][] after = new int[n][m];
        for (int j = 0 ; j < m ; j++) {
            int idx = n-1;
            for (int i = n-1 ; i >= 0 ; i--) {
                if (before[i][j] == 1) {
                    after[idx][j] = 1;
                    idx--;
                }
            }
        }

        for (int[] row : after) {
            StringBuilder b = new StringBuilder();
            for (int c : row) {
                b.append(' ').append(c);
            }
            out.println(b.substring(1));
        }
        out.flush();
    }
}
