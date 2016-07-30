package topcoder.srm6xx.srm695.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class BearPasswordLexic {
    public String findPassword(int[] x) {
        List<Integer> blocks = new ArrayList<>();
        int n = x.length;
        int total = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            int expected = (i == n-1) ? 0 : (x[i+1] + total);
            int actual = x[i];
            if (actual < expected) {
                return "";
            }
            int increased = actual - expected;
            total += increased;
            for (int k = 0 ; k < increased ; k++) {
                blocks.add(i+1);
            }
        }
        int sum = 0;
        for (int i = 0; i < blocks.size() ; i++) {
            sum += blocks.get(i);
        }
        if (sum != n) {
            return "";
        }

        Collections.sort(blocks);
        Collections.reverse(blocks);

        StringBuilder line = new StringBuilder();
        int bn = blocks.size();
        for (int i = 0 ; i < bn ; i++) {
            int top = i;
            int bottom = bn-i-1;
            if (top > bottom) {
                break;
            }
            line.append(copy('a', blocks.get(top)));
            if (top != bottom) {
                line.append(copy('b', blocks.get(bottom)));
            }
        }
        return line.toString();
    }

    private String copy(char a, int num) {
        char[] l = new char[num];
        Arrays.fill(l, a);
        return String.valueOf(l);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
