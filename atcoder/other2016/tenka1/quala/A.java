package atcoder.other2016.tenka1.quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/30.
 */
public class A {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(System.out);

        int sum = 0;
        for (int i = 1 ; i <= 100 ; i++) {
            if (i % 3 == 0 || i % 5 == 0) {

            } else {
                sum += i;
            }
        }
        out.println(sum);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
