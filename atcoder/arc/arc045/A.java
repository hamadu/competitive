package atcoder.arc.arc045;

import java.io.*;
import java.util.Arrays;

/**
 * Created by hama_du on 15/10/10.
 */
public class A {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String line = in.readLine();
        line = line.replaceAll("Left", "<");
        line = line.replaceAll("Right", ">");
        line = line.replaceAll("AtCoder", "A");

        out.println(line);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
