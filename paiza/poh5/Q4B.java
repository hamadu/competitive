package paiza.poh5;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/18.
 */
public class Q4B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int m = in.nextInt();
        int n = in.nextInt();
        int q = in.nextInt();

        int[][] table = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                table[i][j] = in.nextInt();
            }
        }

        boolean[][] include = new boolean[n][m];
        for (int k = 0 ; k < q ; k++) {
            int x1 = in.nextInt()-1;
            int y1 = in.nextInt()-1;
            int x2 = in.nextInt()-1;
            int y2 = in.nextInt()-1;
            for (int i = y1 ; i <= y2 ; i++) {
                for (int j = x1 ; j <= x2 ; j++) {
                    include[i][j] = true;
                }
            }
        }

        int sum = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                if (include[i][j]) {
                    sum += table[i][j];
                }
            }
        }

        out.println(sum);
        out.flush();
    }
}
