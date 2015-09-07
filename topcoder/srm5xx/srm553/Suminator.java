package topcoder.srm5xx.srm553;

import java.util.Stack;

/**
 * Created by hama_du on 15/08/31.
 */
public class Suminator {
    public int findMissing(int[] program, int wantedResult) {
        if (doit(program, 0) == wantedResult) {
            return 0;
        }
        long or = doit(program, 1);
        long tr = doit(program, 2);
        if (or == tr) {
            if (or == wantedResult) {
                return 1;
            } else {
                return -1;
            }
        }
        long tv = 1 + wantedResult - or;
        if (tv <= 0) {
            return -1;
        }
        return (int)tv;
    }

    private long doit(int[] program, int rev) {
        Stack<Long> stk = new Stack<>();
        for (int i = 0; i < 100 ; i++) {
            stk.push(0L);
        }
        int n = program.length;
        for (int i = 0; i < n ; i++) {
            long val = (program[i] == -1) ? rev : program[i];
            if (val == 0) {
                long op1 = stk.pop();
                long op2 = stk.pop();
                stk.push(op1 + op2);
            } else {
                stk.push(val);
            }
        }
        return stk.pop();
    }
}
