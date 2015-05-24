package bestcoder.br042;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by dhamada on 15/05/23.
 */
public class A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            if (!in.hasNext()) {
                break;
            }
            int n = in.nextInt();
            int cup = 2 * n;
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    int d = in.nextInt();
                    if (i < j && d >= 1) {
                        cup += 2;
                    }
                }
            }
            out.println(cup);
        }
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
