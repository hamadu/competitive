package paiza.poh5;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/18.
 */
public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextLine().toCharArray();
        StringBuilder ans = new StringBuilder();
        for (int i = 0 ; i < s.length ; i += 2) {
            ans.append(s[i]);
        }
        out.println(ans.toString());
        out.flush();
    }
}
