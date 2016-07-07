package topcoder.srm6xx.srm646.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class TheConsecutiveIntegersDivOne {
    public int find(int[] numbers, int k) {
        int n = numbers.length;
        int min = Integer.MAX_VALUE;
        Arrays.sort(numbers);
        for (int i = 0; i < n ; i++) {
            for (int wantL = 0 ; wantL <= Math.min(i, k-1) ; wantL++) {
                int wantR = Math.min(n-i-1, (k-1) - wantL);
                if (wantL+wantR+1 != k) {
                    continue;
                }
                int op = 0;
                int d = 0;
                for (int j = i-1 ; j >= i-wantL ; j--) {
                    d++;
                    int w = numbers[i]-d;
                    op += Math.abs(numbers[j]-w);
                }
                d = 0;
                for (int j = i+1 ; j <= i+wantR ; j++) {
                    d++;
                    int w = numbers[i]+d;
                    op += Math.abs(numbers[j]-w);
                }
                min = Math.min(min, op);
            }
        }
        return min;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
